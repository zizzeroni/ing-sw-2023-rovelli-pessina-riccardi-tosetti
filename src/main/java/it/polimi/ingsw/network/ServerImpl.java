package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.exceptions.DuplicateNicknameException;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    private Map<Client, String> clientsToHandle;

    /**
     * Class constructor.
     * initialize the server's attributes to their default values, also instantiating a new game controller.
     * Finally, the server registers itself as a listener on the game and the board classes to identify changes.
     *
     * @see GameController
     * @see Game
     * @see Board
     */
    public ServerImpl() throws RemoteException {
        super();
        this.clientsToHandle = new HashMap<>();
        this.model = new Game();
        this.controller = new GameController(this.model);
        //Server start listening to Game for changes
        this.model.registerListener(this);
        //Server start listening to Board for changes
        this.model.getBoard().registerListener(this);

        startPingSenderThread(this);
    }

    /**
     * Class constructor.
     * initialize the server's port to the given value.
     * Starts the thread's pinging.
     *
     * @param port the server's port number.
     */
    public ServerImpl(int port) throws RemoteException {
        super(port);
        startPingSenderThread(this);
    }

    /**
     * Class constructor.
     * initialize the server's ip and port to the given values.
     * Starts the thread's pinging.
     *
     * @param port the server's port number.
     * @param csf the client socket factory employed for the RMI.
     * @param ssf the server socket factory employed for the RMI.
     *
     * @see Server
     * @see RMIClientSocketFactory
     * @see RMIServerSocketFactory
     */
    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        startPingSenderThread(this);
    }

    /**
     * Allows to change the turn and update the state of the game.
     * Registers a listener for game and board changes.
     *
     * @throws RemoteException is called when a communication error occurs.
     *
     * @see Game
     * @see Board
     * @see java.net.http.WebSocket.Listener
     */
    @Override
    public synchronized void changeTurn() throws RemoteException {
        this.controller.changeTurn();
        if (this.model.getGameState() == GameState.RESET_NEEDED) {
            this.model = new Game();
            this.controller = new GameController(this.model);
            //Server start listening to Game for changes
            this.model.registerListener(this);
            //Server start listening to Board for changes
            this.model.getBoard().registerListener(this);
        }
    }

    /**
     * Allows to notify the input insertion through the controller.
     *
     * @param playerChoice the choice made by the player.
     * @throws RemoteException is called when a communication error occurs.
     *
     * @see Player
     * @see GameController
     */
    @Override
    public synchronized void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        this.controller.insertUserInputIntoModel(playerChoice);
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
     * Adds a player to the game through its client. Updates the connection state of the new player to 'connected'.
     *
     * @param client   is the player's client.
     * @param nickname is the reference for the name of the {@code Player} being added.
     * @throws RemoteException is called when a communication error occurs.
     *
     * @see Player
     * @see Client
     * @see Game
     */
    @Override
    public synchronized void addPlayer(Client client, String nickname) throws RemoteException {
        if (this.clientsToHandle.containsValue(nickname)) {
            if(this.model.getPlayerFromNickname(nickname).isConnected()) {
                client.receiveException(new DuplicateNicknameException("[INPUT:ERROR] Chosen nickname is already being utilized, please try another one!"));
                return;
            }
            Client key = null;
            for(Map.Entry<Client, String> entry : this.clientsToHandle.entrySet()) {
                if(entry.getValue()!=null && entry.getValue().equals(nickname)) {
                    key = entry.getKey();
                }
            }
            this.clientsToHandle.remove(key);
        }

        this.clientsToHandle.put(client, nickname);
        this.controller.addPlayer(nickname);
    }

    /**
     * Allows to communicate the players number selection from the GameController.
     *
     * @param chosenNumberOfPlayers identifies the number of players present
     *                              in the lobby during the game creation.
     *
     * @throws RemoteException if a communication error occurs.
     *
     * @see Game
     * @see GameController
     */
    @Override
    public synchronized void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    /**
     * Calls the controller to start up the current Game.
     *
     * @throws RemoteException if a communication error occurs.
     *
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
     * @throws RemoteException
     *
     * @see Client
     * @see Server
     */
    //TODO: Ask if we should pass nickname to register client
    @Override
    public synchronized void register(Client client, String nickname) throws RemoteException {
        /*try {
            System.out.println(RemoteServer.getClientHost());           //Alternative method for identify clients by their IP (Doesn't work on local)
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }*/
        //this.addClientToHandle(client);

        if (this.clientsToHandle.containsKey(nickname)) {
            client.receiveException(new DuplicateNicknameException("[INPUT:ERROR] Nickname chosen is already being utilized, please try another one!"));
        } else {
            this.clientsToHandle.put(client, nickname);
        }

    }

    /**
     * Routes the server's pings to the proper player's client.
     *
     * @throws RemoteException if a communication error occurs.
     *
     * @see Player
     */
    public synchronized void pingClients() throws RemoteException {
        Game model = this.controller.getModel();
        Client clientToRemove = null;
        for (Map.Entry<Client, String> entry : clientsToHandle.entrySet()) {
            String nickname = entry.getValue();
            Client client = entry.getKey();
            try {
                client.ping();
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while sending heartbeat to the client \"" + nickname + "\":" + e.getMessage());
                if (model.getGameState() == GameState.IN_CREATION) {
                    clientToRemove = client;
                }
                if (model.getPlayerFromNickname(nickname) != null && model.getPlayerFromNickname(nickname).isConnected()) {
                    this.controller.disconnectPlayer(nickname);
                }

            }

        }
        this.clientsToHandle.remove(clientToRemove);
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
     * Signals to the game's view the disconnection of a player.
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @throws RemoteException if a communication error occurs.
     *
     * @see Game
     * @see Player
     */
    @Override
    public synchronized void disconnectPlayer(String nickname) throws RemoteException {
        this.controller.disconnectPlayer(nickname);
        //this.clientsToHandle.remove(nickname);
    }

    /**
     * Signals to the view that the board has changed.
     * Tiles have been added.
     *
     * @param board the tiles are added on this board.
     *
     * @see Board
     * @see it.polimi.ingsw.model.tile.Tile
     */
    //Listeners methods
    @Override
    public void addedTilesToBoard(Board board) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
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
     *
     * @see Board
     * @see it.polimi.ingsw.model.tile.Tile
     */
    @Override
    public void removedTilesFromBoard(Board board) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
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
     *
     * @see Bookshelf
     * @see it.polimi.ingsw.model.tile.Tile
     * @see javax.swing.text.View
     */
    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(tileAddedBookshelf):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Notifies to the game's view that the given image has been modified.
     *
     * @param image the image that has changed following the method call.
     *
     * @see Client
     * @see GameView
     */
    @Override
    public void imageModified(String image) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(imageModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Notifies to the game's view that the number of players has changed (due to a disconnection/reconnection/registration event).
     *
     * @see Player
     */
    @Override
    public void numberOfPlayersModified() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
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
                client.updateModelView(new GameView(this.model));
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
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(bagModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     *
     */
    @Override
    public void commonGoalsModified() {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
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
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(addedPlayers):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    /**
     * Indicates a change in the current game's state.
     * It is also used to register every player and his bookshelf as a listener on the server implementation.
     *
     * @see Game
     * @see Player
     * @see java.net.http.WebSocket.Listener
     */
    @Override
    public void gameStateChanged() {
        if (this.controller.getModel().getGameState() == GameState.ON_GOING) {
            for (Player player : this.model.getPlayers()) {
                player.registerListener(this);
                player.getBookshelf().registerListener(this);
            }
        }
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(startOfTheGame):" + e.getMessage() + ".Skipping update");
            }
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
                client.updateModelView(new GameView(this.model));
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
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(playerHasReconnected):" + e.getMessage() + ".Skipping update");
            }
        }
    }


    /**
     * Allows the server to ping a game's thread.
     *
     * @param server the server starting the thread's pinging.
     *
     * @see Game
     */
    private void startPingSenderThread(ServerImpl server) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    server.pingClients();
                } catch (RemoteException e) {
                    System.err.println("prova");
                }
            }
        };

        Timer pingSender = new Timer("PingSender");
        pingSender.scheduleAtFixedRate(timerTask, 30, 3000);
    }
}
