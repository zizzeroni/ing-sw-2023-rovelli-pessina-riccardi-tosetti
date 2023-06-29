package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.FinishingState;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.OnGoingState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.OptionsValues;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * This class indicates the server's implementation.
 * It is an extension of the UnicastRemoteObject and also a ModelListener's implementation.
 * It contains methods that implements all the server's functionalities described in
 * the server's interface class.
 *
 * @see Server
 * @see ModelListener
 * @see UnicastRemoteObject
 */
public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Map<Client, Optional<String>> clientsToHandle;
    private Map<Client, Integer> numberOfMissedPings;

    /**
     * Class constructor.
     * initialize the server's attributes to their default values, also instantiating a new game controller.
     * Finally, the server registers itself as a listener on the game and the board classes to identify changes.
     *
     * @see GameController
     * @see Game
     * @see Board
     * @throws RemoteException if a connection error occurs
     */
    public ServerImpl() throws RemoteException {
        super();
        this.clientsToHandle = new ConcurrentHashMap<>();
        this.model = new Game();
        this.controller = new GameController(this.model);
        //Server start listening to Game for changes
        this.model.registerListener(this);
        //Server start listening to Board for changes
        this.model.getBoard().registerListener(this);
        this.numberOfMissedPings = new ConcurrentHashMap<>();
        startPingSenderThread(this);
    }

    /**
     * Class constructor.
     * initialize the server's port to the given value.
     * Starts the thread's pinging.
     *
     * @param port the server's port number.
     * @throws RemoteException if a connection error occurs
     */
    public ServerImpl(int port) throws RemoteException {
        super(port);
        startPingSenderThread();
    }

    /**
     * Class constructor.
     * initialize the server's ip and port to the given values.
     * Starts the thread's pinging.
     *
     * @param port the server's port number.
     * @param csf  the client socket factory employed for the RMI.
     * @param ssf  the server socket factory employed for the RMI.
     * @see Server
     * @see RMIClientSocketFactory
     * @see RMIServerSocketFactory
     * @throws RemoteException if a connection error occurs
     */
    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        startPingSenderThread();
    }

    /**
     * Allows to change the turn and update the state of the game.
     * In case the {@code GameState} is {@code RESET_NEEDED} launch the
     * resetServer() method
     *
     * @throws RemoteException is called when a communication error occurs.
     * @see Game
     * @see Board
     * @see java.net.http.WebSocket.Listener
     * @see #resetServer()
     */
    @Override
    public synchronized void changeTurn() throws RemoteException {
        this.controller.changeTurn(OptionsValues.GAMES_STORAGE_DEFAULT_PATH, OptionsValues.GAMES_STORAGE_BACKUP_DEFAULT_PATH);
        if (this.model.getGameState() == GameState.RESET_NEEDED) {
            resetServer();
        }
    }

    /**
     * Allows to execute the input insertion through the controller.
     *
     * @param playerChoice the choice made by the player.
     * @throws RemoteException is called when a communication error occurs.
     * @see Player
     * @see GameController
     */
    @Override
    public synchronized void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        try {
            this.controller.insertUserInputIntoModel(playerChoice);
        } catch (WrongInputDataException e) {
            for (Client client : this.clientsToHandle.keySet()) {
                try {
                    client.receiveException(e);
                } catch (RemoteException e2) {
                    System.err.println("[COMMUNICATION:ERROR] Error while sending exception to client: " + client + ", error caused by \"receiveException(GenericException)\" invocation\n  " + e.getMessage() + ".Skipping client update");
                }
            }
        }
    }

    /**
     * Calls the sending of a private message through the controller's implementation.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender   the {@code Player} sending the message.
     * @param content  the text of the message being sent.
     * @throws RemoteException is called when a communication error occurs.
     */
    @Override
    public synchronized void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        this.controller.sendPrivateMessage(receiver, sender, content);
    }


    /**
     * Calls the sending of a broadcast message through the controller's implementation.
     *
     * @param sender  the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     * @throws RemoteException is called when a communication error occurs.
     */
    @Override
    public synchronized void sendBroadcastMessage(String sender, String content) throws RemoteException {
        this.controller.sendBroadcastMessage(sender, content);
    }

    /**
     * Adds a player to the game associated with his client.
     * In case the lobby is full, or the nickname il already taken, respond
     * to the client sending an exception
     *
     * @param client   is the player's client.
     * @param nickname is the reference for the name of the {@code Player} being added.
     * @throws RemoteException is called when a communication error occurs.
     * @see Player
     * @see Client
     * @see Game
     */
    @Override
    public synchronized void addPlayer(Client client, String nickname) throws RemoteException {
        Optional<String> nicknameInInput = Optional.ofNullable(nickname);
        if (this.clientsToHandle.containsValue(nicknameInInput)) {
            if (this.controller.getModel().getPlayerFromNickname(nickname).isConnected()) {
                client.receiveException(new DuplicateNicknameException("Chosen nickname is already being utilized, please try another one!"));
                return;
            }
            Client keyToRemove = null;
            for (Map.Entry<Client, Optional<String>> entry : this.clientsToHandle.entrySet()) {
                if (entry.getValue().isPresent() && entry.getValue().get().equals(nickname)) {
                    keyToRemove = entry.getKey();
                }
            }
            this.clientsToHandle.remove(keyToRemove);
            this.numberOfMissedPings.remove(keyToRemove);
        }

        this.clientsToHandle.put(client, nicknameInInput);
        this.numberOfMissedPings.put(client, OptionsValues.INITIAL_MISSED_PINGS);
        try {
            this.controller.addPlayer(nickname);
        } catch (LobbyIsFullException e) {
            client.receiveException(e);
            Optional<String> nullNickname = Optional.empty();
            this.clientsToHandle.replace(client, nullNickname); //Look down tryToResumeGame method
            this.numberOfMissedPings.remove(client);            //I will remove it anyway in the next tryToResumeGame call, so I remove it at this point
        }
    }

    /**
     * This method tries to resume the current's game when possible.
     */
    @Override
    public synchronized void tryToResumeGame() throws RemoteException {
        this.controller.tryToResumeGame();
        //Necessary because with the socket connection, in case the addPlayer method threw an exception I still need to execute the tryToResumeGame otherwise i get stuck in the semaphore wait.
        //Wouldn't be necessary for RMI because once the methods calling reach the notification method (gameStateChanged in this case) it will throw an exception and simply return. Socket
        //don't do this on his own.
        Optional<String> nullNickname = Optional.empty();
        this.clientsToHandle.values().removeAll(Collections.singleton(nullNickname));
    }

    /**
     * Calls the controller to set the number of player requested to start the game.
     *
     * @param chosenNumberOfPlayers identifies the number of players present
     *                              in the lobby during the game creation.
     * @throws RemoteException if a communication error occurs.
     * @see Game
     * @see GameController
     */
    @Override
    public synchronized void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        try {
            this.controller.checkExceedingPlayer(chosenNumberOfPlayers);
            this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
        } catch (ExcessOfPlayersException e) {
            List<String> orderedConnectedPlayersNickname = this.controller.getModel().getPlayers().stream().map(Player::getNickname).toList();
            for (int numberOfRemainingPlayer = this.controller.getNumberOfPlayersCurrentlyInGame(); numberOfRemainingPlayer > chosenNumberOfPlayers; numberOfRemainingPlayer--) {
                int playerNickToRemoveIndex = numberOfRemainingPlayer;
                Client clientToRemove = clientsToHandle.entrySet().stream()
                        .filter(clientOptionalEntry -> clientOptionalEntry.getValue().orElse("Unknown").equals(orderedConnectedPlayersNickname.get(playerNickToRemoveIndex - 1)))
                        .toList().get(0).getKey();

                clientToRemove.receiveException(e);
                this.controller.disconnectPlayer(orderedConnectedPlayersNickname.get(numberOfRemainingPlayer - 1));
                if (this.controller.getModel().getGameState() == GameState.IN_CREATION) {
                    this.clientsToHandle.remove(clientToRemove);
                    this.numberOfMissedPings.remove(clientToRemove);
                }
            }
            this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
        } catch (WrongInputDataException e) {
            for (Client client : this.clientsToHandle.keySet()) {
                try {
                    client.receiveException(e);
                } catch (RemoteException e2) {
                    System.err.println("[COMMUNICATION:ERROR] Error while sending exception to client: " + client + ", error caused by \"receiveException(GenericException)\" invocation\n  " + e2.getMessage() + ".Skipping client update");
                }
            }
        }
    }

    /**
     * Calls the controller to start up the current Game.
     *
     * @throws RemoteException if a communication error occurs.
     * @see GameController#startGame()
     * @see Game
     */
    @Override
    public synchronized void startGame() throws RemoteException {
        this.controller.startGame();
    }

    /**
     * Used to pass the registration of a player's client, basing on his nickname.
     *
     * @param client   is the client registering to the server.
     * @param nickname the player's nickname related to the client.
     * @throws RemoteException called if a communication error occurs.
     * @see Client
     * @see Server
     */
    @Override
    public synchronized void register(Client client, String nickname) throws RemoteException {
        Optional<String> nicknameInInput = Optional.ofNullable(nickname);
        this.clientsToHandle.put(client, nicknameInInput);
        this.numberOfMissedPings.put(client, OptionsValues.INITIAL_MISSED_PINGS);

    }


    /**
     * Receives ping from the client.
     *
     * @throws RemoteException if a communication error occurs.
     */
    @Override
    public synchronized void ping() throws RemoteException {
        //Receiving ping from the client... so do nothing
    }

    /**
     * Calls the controller implementation of disconnecting .
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @throws RemoteException if a communication error occurs.
     * @see Game
     * @see Player
     */
    @Override
    public synchronized void disconnectPlayer(String nickname) throws RemoteException {
        this.controller.disconnectPlayer(nickname);
    }

    /**
     * Restores the current game for the considered player.
     *
     * @param nickname the given player's nickname.
     * @see Player
     * @see Game
     * @see #disconnectPlayerNotPartOfTheLobby()
     * @see #notifyClientsAfterRestoring()
     */
    @Override
    public void restoreGameForPlayer(String nickname) throws RemoteException {
        this.controller.restoreGameForPlayer(this, nickname, OptionsValues.GAMES_STORAGE_DEFAULT_PATH);
        this.model = this.controller.getModel();
        disconnectPlayerNotPartOfTheLobby();
        notifyClientsAfterRestoring();
    }

    /**
     * Disconnects the players not in actually present in the game's lobby.
     *
     * @throws RemoteException called when a communication error occurs.
     * @see Player
     * @see Game
     */
    private void disconnectPlayerNotPartOfTheLobby() throws RemoteException {
        Game model = this.controller.getModel();
        List<String> orderedConnectedPlayersNickname = model.getPlayers().stream().map(Player::getNickname).toList();
        List<Client> clientsToRemove = this.clientsToHandle.entrySet().stream()
                .filter(clientOptionalEntry -> !orderedConnectedPlayersNickname.contains(clientOptionalEntry.getValue().orElse("Unknown")))
                .map(Map.Entry::getKey)
                .toList();

        for (Client client : clientsToRemove) {
            client.receiveException(new ExcessOfPlayersException("The creator of the lobby restored a previous game that you weren't part of"));
            this.clientsToHandle.remove(client);
            this.numberOfMissedPings.remove(client);
        }
    }

    /**
     * Notifies the client after being restored.
     *
     * @see Client
     */
    private void notifyClientsAfterRestoring() {
        switch (this.controller.getModel().getGameState()) {
            case ON_GOING -> {
                this.controller.changeState(new OnGoingState(this.controller));
                this.controller.getModel().setGameState(OnGoingState.toEnum());
            }
            case FINISHING -> {
                this.controller.changeState(new FinishingState(this.controller));
                this.controller.getModel().setGameState(FinishingState.toEnum());
            }
        }
    }

    /**
     * Verifies if there are any saved games associated to the given player.
     *
     * @param nickname the given player's nickname.
     * @throws RemoteException called when a communication error occurs.
     * @see Game
     * @see Player
     */
    @Override
    public void areThereStoredGamesForPlayer(String nickname) throws RemoteException {
        boolean result = this.controller.areThereStoredGamesForPlayer(nickname, OptionsValues.GAMES_STORAGE_DEFAULT_PATH);
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.setAreThereStoredGamesForPlayer(result);
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(addedTilesToBoard):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Signals to the view that the board has changed.
     * Tiles have been added.
     *
     * @param board the tiles are added on this board.
     * @see Board
     * @see it.polimi.ingsw.model.tile.Tile
     */
    //Listeners methods
    @Override
    public void addedTilesToBoard(Board board) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(addedTilesToBoard):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Signals to the view that the board has changed.
     * Tiles have been removed.
     *
     * @param board the tiles are removed from this board.
     * @see Board
     * @see it.polimi.ingsw.model.tile.Tile
     */
    @Override
    public void removedTilesFromBoard(Board board) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(removedTilesFromBoard):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Signals to the view that the bookshelf has changed.
     * Tiles have been added.
     *
     * @param bookshelf the tiles are added on this Bookshelf.
     * @see Bookshelf
     * @see it.polimi.ingsw.model.tile.Tile
     * @see javax.swing.text.View
     */
    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(tileAddedBookshelf):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Notifies to the game's view that the number of players to start the game has been set.
     *
     * @see Player
     */
    @Override
    public void numberOfPlayersModified() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(numberOfPlayersModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Notifies the game's view the index of the active player has been modified.
     *
     * @see Client
     * @see GameView
     * @see Player
     */
    @Override
    public void activePlayerIndexModified() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(activePlayerIndexModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Notifies the bag-related changes.
     *
     * @see Client
     */
    @Override
    public void bagModified() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(bagModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Notifies the given commonGoals have been modified.
     *
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     */
    @Override
    public void commonGoalsModified() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(commonGoalsModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Consents to communicate and register the adding of a player to the game's lobby.
     *
     * @see Game
     * @see Player
     */
    @Override
    public void addedPlayer() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(addedPlayers):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Indicates a change in the current game's state.
     * It is also used to register every player and his bookshelf as a listener on the server implementation
     * if the game just entered in the {@code ON_GOING} state.
     * Finally, if the {@code Game} is in {@code RESET_NEEDED} state just call resetServer() method.
     *
     * @see Game
     * @see Player
     * @see java.net.http.WebSocket.Listener
     */
    @Override
    public void gameStateChanged() {
        if (this.controller.getModel().getGameState() == GameState.ON_GOING) {
            for (Player player : this.controller.getModel().getPlayers()) {
                player.registerListener(this);
                player.getBookshelf().registerListener(this);
            }
        }
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(gameStateChanged):" + e.getMessage() + ".Skipping update");
            }
        }

        if (this.model.getGameState() == GameState.RESET_NEEDED) {
            resetServer();
        }
    }

    /**
     * Used for game's chat updating management.
     * In case of a communication error prints a control message to signal it.
     *
     * @see Game
     */
    @Override
    public void chatUpdated() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(chatUpdated):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Indicates if a player has reconnected to the current game's lobby .
     * In case of a communication error prints a control message to signal it.
     *
     * @see Game
     */
    @Override
    public void playerHasReconnected() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(playerHasReconnected):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * For each connected client, start a thread that take care of pinging that specific client.
     * In case the ping() method call fail three times in a row, disconnect the Player associated to
     * that client and remove it from the client to handle.
     *
     * @see Game
     * @see #clientsToHandle
     * @see Client#ping()
     * @see GameController#disconnectPlayer(String)
     */

    private void startPingSenderThread() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                clientsToHandle.entrySet()
                        .stream()
                        .filter(entry -> model.getPlayerFromNickname(entry.getValue().orElse("Unknown")) != null
                                && model.getPlayerFromNickname(entry.getValue().orElse("Unknown")).isConnected())
                        .collect(Collectors.toSet())
                        .forEach(clientOptionalEntry -> {
                            //I declare a new Thread for each client registered, in this way each thread handle the sending of the ping to his associated client
                            Thread pingSenderThread = new Thread() {
                                @Override
                                public void run() {
                                    Client client = clientOptionalEntry.getKey();
                                    String nickname = clientOptionalEntry.getValue().orElse("Unknown");

                                    try {
                                        client.ping();
                                        numberOfMissedPings.replace(client, OptionsValues.INITIAL_MISSED_PINGS);
                                    } catch (RemoteException e) {
                                        try {
                                            numberOfMissedPings.replace(client, numberOfMissedPings.get(client) + 1);
                                            System.out.println("Client:" + client + ", pings missed:" + numberOfMissedPings.get(client));
                                            if (numberOfMissedPings.get(client) >= 3) {
                                                System.err.println("[COMMUNICATION:ERROR] Error while sending heartbeat to the client \"" + nickname + "\":" + e.getMessage());
                                                if (model.getGameState() == GameState.IN_CREATION) {
                                                    clientsToHandle.remove(client);
                                                    numberOfMissedPings.remove(client);
                                                }
                                                if (model.getPlayerFromNickname(nickname) != null && model.getPlayerFromNickname(nickname).isConnected()) {
                                                    controller.disconnectPlayer(nickname);
                                                }
                                            }
                                            this.interrupt();
                                        } catch (NullPointerException e1) {
                                            this.interrupt();
                                        }
                                    }
                                }
                            };

                            pingSenderThread.start();
                        });
            }
        };

        Timer pingSender = new Timer("PingSenderTimer");
        pingSender.scheduleAtFixedRate(timerTask, 30, OptionsValues.MILLISECOND_PING_TO_CLIENT_PERIOD);
    }

    /**
     * This method is used to reset the server.
     *
     * @see Server
     */
    private void resetServer() {
        this.model = new Game();
        this.controller = new GameController(this.model);
        //Server start listening to Game for changes
        this.model.registerListener(this);
        //Server start listening to Board for changes
        this.model.getBoard().registerListener(this);
        this.clientsToHandle.clear();
    }
}