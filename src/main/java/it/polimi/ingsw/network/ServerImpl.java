package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.exceptions.DuplicateNicknameException;
import it.polimi.ingsw.network.exceptions.WrongInputDataException;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Map<Client, String> clientsToHandle;

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
            //TODO: Chiedere se dobbiamo inviare l'eccezione a tutti i client o solo 1
            //TODO: Invio a tutti i client:
//            for (Client client : this.clientsToHandle.keySet()) {
//                try {
//                    client.receiveException(e);
//                } catch (RemoteException e2) {
//                    System.err.println("[COMMUNICATION:ERROR] Error while sending exception:" + e.getMessage() + " ; to client:"+client);
//                }
//            }
            //TODO: Invio al client che ha invocato il metodo (player attivo corrente)
            Game model = this.controller.getModel();
            Client client = this.clientsToHandle.entrySet().stream()
                    //.filter(pair -> pair.getValue().equals(this.controller.getModel().getPlayers().get(this.controller.getModel().getActivePlayerIndex()).getNickname()))
                    .reduce(new AbstractMap.SimpleEntry<>(null,null),
                            (resultEntry, currentEntry)->resultEntry = currentEntry.getValue().equals(model.getPlayers().get(model.getActivePlayerIndex()).getNickname())
                                    ? currentEntry : resultEntry).getKey();
            client.receiveException(e);
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
        if (this.clientsToHandle.containsValue(nickname)) {
            if (this.controller.getModel().getPlayerFromNickname(nickname).isConnected()) {
                client.receiveException(new DuplicateNicknameException("[INPUT:ERROR] Chosen nickname is already being utilized, please try another one!"));
                return;
            }
            Client key = null;
            for (Map.Entry<Client, String> entry : this.clientsToHandle.entrySet()) {
                if (entry.getValue() != null && entry.getValue().equals(nickname)) {
                    key = entry.getKey();
                }
            }
            this.clientsToHandle.remove(key);
        }

        this.clientsToHandle.put(client, nickname);
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


        this.clientsToHandle.put(client, nickname);


    }

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
