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
import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServerImpl extends UnicastRemoteObject implements Server, ModelListener {
    private GameController controller;
    private Game model;
    private Set<Client> clientsToHandle;

    public ServerImpl() throws RemoteException{
        super();
        clientsToHandle=new HashSet<>();

        model = new Game();
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
    public void addPlayer(String nickname, int chosenNumberOfPlayers) throws RemoteException {
        this.controller.addPlayer(nickname,chosenNumberOfPlayers);
        this.model.getPlayers().get(this.model.getPlayers().size()-1).getBookshelf().registerListener(this);
    }


    @Override
    public GameView register(Client client) {
        clientsToHandle.add(client);
        this.model.registerListener(this);
        this.model.getBoard().registerListener(this);
       /* for(Player player : this.model.getPlayers()) {
            player.getBookshelf().registerListener(this);
        }*/
        this.controller = new GameController(model);
        return new GameView(model);
    }

    //Listeners methods
    @Override
    public void addedTilesToBoard(Board board) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void removedTilesFromBoard(Board board) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void tileAddedToBookshelf(Bookshelf bookshelf) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void imageModified(String image) {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void numberOfPlayersModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void activePlayerIndexModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void bagModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void commonGoalsModified() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void addedPlayer() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }

    @Override
    public void startOfTheGame() {
        for(Client client : clientsToHandle) {
            try {
                client.updateModelView(new GameView(model));
            } catch (RemoteException e) {
                System.err.println("Error while updating client:" + e.getMessage() + ".Skipping update");
            }
        }
    }
}
