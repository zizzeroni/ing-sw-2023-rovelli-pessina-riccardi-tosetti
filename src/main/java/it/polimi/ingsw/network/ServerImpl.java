package it.polimi.ingsw.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.listeners.BookshelfListener;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.listeners.ModelListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.model.view.ModelViewListener;
import it.polimi.ingsw.view.TextualUI;
import it.polimi.ingsw.view.UI;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.*;
import java.util.*;

public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Map<Integer,Client> clientsToHandle;

    public ServerImpl() throws RemoteException{
        super();
        clientsToHandle=new HashMap<>();
        model = new Game();
        this.controller = new GameController(model);
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

    @Override
    public void changeTurn() throws RemoteException {
        this.controller.changeTurn();
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        this.controller.insertUserInputIntoModel(playerChoice);
    }

    @Override
    public void sendPrivateMessage(Player receiver, Player sender, String content) throws RemoteException {
        this.controller.sendPrivateMessage(receiver,sender,content);
    }

    @Override
    public void sendBroadcastMessage(Player sender, String content) throws RemoteException {
        this.controller.sendBroadcastMessage(sender,content);
    }

    @Override
    public void addPlayer(String nickname) throws RemoteException {
        this.controller.addPlayer(nickname);
        this.model.getPlayers().get(this.model.getPlayers().size()-1).getBookshelf().registerListener(this);
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
        clientsToHandle.put(clientsToHandle.size(),client);
        //this.controller.addPlayer(nickname);
    }

    //Listeners methods
    @Override
    public void addedTilesToBoard(Board board) {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void removedTilesFromBoard(Board board) {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void imageModified(String image) {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void numberOfPlayersModified() {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void activePlayerIndexModified() {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void bagModified() {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void commonGoalsModified() {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void addedPlayer() {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void startOfTheGame() {
        for(Client client : clientsToHandle.values()) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }
}
