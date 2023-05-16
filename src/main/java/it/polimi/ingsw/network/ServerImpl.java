package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Map<String, Client> clientsToHandle;

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
    public void changeTurn() throws RemoteException {
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

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        this.controller.insertUserInputIntoModel(playerChoice);
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        this.controller.sendPrivateMessage(receiver, sender, content);
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) throws RemoteException {
        this.controller.sendBroadcastMessage(sender, content);
    }

    @Override
    public void addPlayer(String nickname) throws RemoteException {
        this.controller.addPlayer(nickname);
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    @Override
    public void startGame() throws RemoteException {
        this.controller.startGame();
    }

    //TODO: Ask if we should pass nickname to register client
    @Override
    public void register(Client client, String nickname) {
        /*try {
            System.out.println(RemoteServer.getClientHost());           //Alternative method for identify clients by their IP (Doesn't work on local)
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }*/
        //this.addClientToHandle(client);
        if(this.clientsToHandle.containsKey(nickname)) {
            System.err.println("[INPUT:ERROR] Nickname already existing, try another one!");
        } else {
            this.clientsToHandle.put(nickname, client);
        }

    }

    @Override
    public synchronized void pingClients() throws RemoteException {
        String clientToRemove="";
        for (Map.Entry<String, Client> entry : clientsToHandle.entrySet()) {
            String nickname = entry.getKey();
            Client client = entry.getValue();
            try {
                client.ping();
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while sending heartbeat to the client \"" + nickname+"\":" + e.getMessage());
                if(this.controller.getModel().getGameState()==GameState.IN_CREATION) {
                    clientToRemove = nickname;
                }
                if(this.controller.getModel().getPlayerFromNickname(nickname).isConnected()) {
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
    public void disconnectPlayer(String nickname) throws RemoteException {
        this.controller.disconnectPlayer(nickname);
        //this.clientsToHandle.remove(nickname);
    }

    //Listeners methods
    @Override
    public void addedTilesToBoard(Board board) {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(addedTilesToBoard):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void removedTilesFromBoard(Board board) {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(removedTilesFromBoard):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(tileAddedBookshelf):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void imageModified(String image) {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(imageModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void numberOfPlayersModified() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(numberOfPlayersModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void activePlayerIndexModified() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(activePlayerIndexModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void bagModified() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(bagModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void commonGoalsModified() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(commonGoalsModified):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void addedPlayer() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(addedPlayers):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void gameStateChanged() {
        if (this.controller.getModel().getGameState() == GameState.ON_GOING) {
            for (Player player : this.model.getPlayers()) {
                player.registerListener(this);
                player.getBookshelf().registerListener(this);
            }
        }
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(startOfTheGame):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void chatUpdated() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(chatUpdated):" + e.getMessage() + ".Skipping update");
            }
        }
    }

    private void startPingSenderThread(Server server) {
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
