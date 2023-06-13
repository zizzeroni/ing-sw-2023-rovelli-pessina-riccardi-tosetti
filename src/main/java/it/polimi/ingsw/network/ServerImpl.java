package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.network.exceptions.WrongInputDataException;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Map<Client, Optional<String>> clientsToHandle;

    public ServerImpl() throws RemoteException {
        super();
        this.clientsToHandle = new ConcurrentHashMap<>();
        this.model = new Game();
        this.controller = new GameController(this.model);
        //Server start listening to Game for changes
        this.model.registerListener(this);
        //Server start listening to Board for changes
        this.model.getBoard().registerListener(this);

        startPingSenderThread(this);
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
        startPingSenderThread(this);
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        startPingSenderThread(this);
    }

    @Override
    public synchronized void changeTurn() throws RemoteException {
        this.controller.changeTurn();
        if (this.model.getGameState() == GameState.RESET_NEEDED) {
            resetServer();
        }
    }

    @Override
    public synchronized void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        try {
            this.controller.insertUserInputIntoModel(playerChoice);
        } catch (WrongInputDataException e) {
            for (Client client : this.clientsToHandle.keySet()) {
                try {
                    client.receiveException(e);
                } catch (RemoteException e2) {
                    System.err.println("[COMMUNICATION:ERROR] Error while sending exception:" + e.getMessage() + " ; to client:" + client);
                }
            }

            /*Game model = this.controller.getModel();
            Client client = this.clientsToHandle.entrySet().stream()
                    //.filter(pair -> pair.getValue().equals(this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getNickname()))
                    .reduce(new AbstractMap.SimpleEntry<>(null,null),
                            (resultEntry, currentEntry)->resultEntry = currentEntry.getValue().equals(model.getPlayers().get(model.getActivePlayerIndex()).getNickname())
                                    ? currentEntry : resultEntry).getKey();
            client.receiveException(e);*/
        }
    }

    @Override
    public synchronized void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        this.controller.sendPrivateMessage(receiver, sender, content);
    }

    @Override
    public synchronized void sendBroadcastMessage(String sender, String content) throws RemoteException {
        this.controller.sendBroadcastMessage(sender, content);
    }

    @Override
    public synchronized void addPlayer(Client client, String nickname) throws RemoteException {
        Optional<String> nicknameInInput = Optional.ofNullable(nickname);
        if (this.clientsToHandle.containsValue(nicknameInInput)) {
            if (this.controller.getModel().getPlayerFromNickname(nickname).isConnected()) {
                client.receiveException(new DuplicateNicknameException("[INPUT:ERROR] Chosen nickname is already being utilized, please try another one!"));
                return;
            }
            Client key = null;
            for (Map.Entry<Client, Optional<String>> entry : this.clientsToHandle.entrySet()) {
                if (entry.getValue().isPresent() && entry.getValue().get().equals(nickname)) {
                    key = entry.getKey();
                }
            }
            this.clientsToHandle.remove(key);
        }

        this.clientsToHandle.put(client, nicknameInInput);
        this.controller.addPlayer(nickname);
    }

    @Override
    public void tryToResumeGame() throws RemoteException {
        this.controller.tryToResumeGame();
    }

    @Override
    public synchronized void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    @Override
    public synchronized void startGame() throws RemoteException {
        this.controller.startGame();
    }

    //TODO: Ask if we should pass nickname to register client
    @Override
    public synchronized void register(Client client, String nickname) throws RemoteException {
        /*try {
            System.out.println(RemoteServer.getClientHost());           //Alternative method for identify clients by their IP (Doesn't work on local)
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }*/
        //this.addClientToHandle(client);

        Optional<String> nicknameInInput = Optional.ofNullable(nickname);
        this.clientsToHandle.put(client, nicknameInInput);


    }

    /*TODO: Fare un thread che invoca altri 4 thread ciascuno dei quali fa un ping ad un client, altrimenti se il ping di un client non va a buon fine il server si blocca
            impedendo di inviare gli altri ping ad altri client*/
    public synchronized void pingClients() throws RemoteException {
        Game model = this.controller.getModel();
        Client clientToRemove = null;
        for (Map.Entry<Client, Optional<String>> entry : clientsToHandle.entrySet()) {
            String nickname = entry.getValue().orElse("Unknown");
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

    @Override
    public synchronized void ping() throws RemoteException {
        //Receiving ping from the client... so do nothing
    }

    @Override
    public synchronized void disconnectPlayer(String nickname) throws RemoteException {
        this.controller.disconnectPlayer(nickname);
        //this.clientsToHandle.remove(nickname);
    }

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

    @Override
    public void imageModified(String image) {
        for (Client client : this.clientsToHandle.keySet()) {
            try {
                client.updateModelView(new GameView(this.controller.getModel()));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(imageModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

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

    /*private void startPingSenderThread(ServerImpl server) {
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
    }*/

    private void startPingSenderThread(ServerImpl server) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                for (Map.Entry<Client, Optional<String>> entry : clientsToHandle.entrySet()) {
                    //I declare a new Thread for each client registered, in this way each thread handle the sending of the ping to his associated client
                    Thread pingSenderThread = new Thread() {
                        @Override
                        public void run() {
                            String nickname = entry.getValue().orElse("Unknown");
                            Client client = entry.getKey();

                            //I save in this variable the instance of this Thread, in order to use it in the next TimerTask for eventually interrupt the Thread "pingSenderThread"
                            Thread selfThread = this;
                            Timer stopIfWaitTooLongTimer = new Timer("stopIfWaitTooLong");
                            stopIfWaitTooLongTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    selfThread.interrupt();
                                }
                            }, 6000);

                            try {
                                entry.getKey().ping();
                                stopIfWaitTooLongTimer.cancel();
                            } catch (RemoteException e) {
                                System.err.println("[COMMUNICATION:ERROR] Error while sending heartbeat to the client \"" + nickname + "\":" + e.getMessage());
                                if (model.getGameState() == GameState.IN_CREATION) {
                                    clientsToHandle.remove(client);
                                }
                                if (model.getPlayerFromNickname(nickname) != null && model.getPlayerFromNickname(nickname).isConnected()) {
                                    controller.disconnectPlayer(nickname);
                                }
                            }
                        }
                    };
                    pingSenderThread.start();
                }
            }
        };

        Timer pingSender = new Timer("PingSenderTimer");
        pingSender.scheduleAtFixedRate(timerTask, 30, 3000);
    }

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
