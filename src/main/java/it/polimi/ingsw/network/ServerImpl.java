package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.view.GameView;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.*;

public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Map<Integer, Client> clientsToHandle;

    public ServerImpl() throws RemoteException {
        super();
        this.clientsToHandle = new HashMap<>();
        this.model = new Game();
        this.controller = new GameController(this.model);
        //Server start listening to Game for changes
        this.model.registerListener(this);
        //Server start listening to Board for changes
        this.model.getBoard().registerListener(this);
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    public void addClientToHandle(Client client) {
        this.clientsToHandle.put(this.clientsToHandle.size(), client);
    }

    @Override
    public void changeTurn() throws RemoteException {
        this.controller.changeTurn();
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
        this.model.getPlayers().get(this.model.getPlayers().size() - 1).getBookshelf().registerListener(this);
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        this.controller.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    //TODO: Ask if we should pass nickname to register client
    @Override
    public void register(Client client/*, String nickname*/) {
        /*try {
            System.out.println(RemoteServer.getClientHost());           //Alternative method for identify clients by their IP (Doesn't work on local)
        } catch (ServerNotActiveException e) {
            throw new RuntimeException(e);
        }*/
        this.addClientToHandle(client);
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
    public void startOfTheGame() {
        for (Client client : this.clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(this.model));
            } catch (RemoteException e) {
                System.err.println("[COMMUNICATION:ERROR] Error while updating client(startOfTheGame):" + e.getMessage() + ".Skipping update");
            }
        }
    }
}
