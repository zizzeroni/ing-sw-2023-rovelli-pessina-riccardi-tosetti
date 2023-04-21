package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.TextualUI;
import it.polimi.ingsw.view.UI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, ViewListener, Runnable {

    private Server serverConnectedTo;
    UI view;


    public ClientImpl(Server server, UI view) throws RemoteException {
        super();
        serverConnectedTo = server;
        this.view=view;
        GameView freshModel = server.register(this);
        view.setModel(freshModel);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, int port) throws RemoteException {
        super(port);
        serverConnectedTo = server;
        this.view=view;
        GameView freshModel = server.register(this);
        view = new TextualUI(freshModel);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        serverConnectedTo = server;
        this.view=view;
        GameView freshModel = server.register(this);
        view = new TextualUI(freshModel);
        view.registerListener(this);
    }

    //Update coming from the server, I forward it to the view
    @Override
    public void updateModelView(GameView modelUpdated) throws RemoteException {
        this.view.modelModified(modelUpdated);
    }

    //Methods used for forwarding notifications from view to the server
    @Override
    public void changeTurn() {
        try {
            this.serverConnectedTo.changeTurn();
        } catch (RemoteException e) {
            System.err.println("Error while updating server:" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        try {
            this.serverConnectedTo.insertUserInputIntoModel(playerChoice);
        } catch (RemoteException e) {
            System.err.println("Error while updating server:" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void sendPrivateMessage(Player receiver, Player sender, String content) {
        try {
            this.serverConnectedTo.sendPrivateMessage(receiver, sender, content);
        } catch (RemoteException e) {
            System.err.println("Error while updating server:" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void sendBroadcastMessage(Player sender, String content) {
        try {
            this.serverConnectedTo.sendBroadcastMessage(sender, content);
        } catch (RemoteException e) {
            System.err.println("Error while updating server:" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void addPlayer(String nickname, int chosenNumberOfPlayers) {
        try {
            this.serverConnectedTo.addPlayer(nickname,chosenNumberOfPlayers);
        } catch (RemoteException e) {
            System.err.println("Error while updating server:" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void run() {
        view.run();
    }
}
