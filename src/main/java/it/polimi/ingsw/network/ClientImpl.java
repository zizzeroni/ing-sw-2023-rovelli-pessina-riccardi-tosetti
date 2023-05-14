package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.UI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, ViewListener, Runnable {
    private final Server serverConnectedTo;
    private final UI view;

    public ClientImpl(Server server, UI view, String nickname) throws RemoteException {
        super();
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNicknameID(nickname);
        server.register(this,nickname);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, String nickname, int port) throws RemoteException {
        super(port);
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNicknameID(nickname);
        server.register(this,nickname);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, String nickname, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNicknameID(nickname);
        server.register(this,nickname);
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
            System.err.println("[COMMUNICATION:ERROR] Error while updating server(changeTurn):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        try {
            this.serverConnectedTo.insertUserInputIntoModel(playerChoice);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server(insertUserInputIntoModel):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        try {
            this.serverConnectedTo.sendPrivateMessage(receiver, sender, content);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server(sendPrivateMessage):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        try {
            this.serverConnectedTo.sendBroadcastMessage(sender, content);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server(sendBroadcastMessage):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void addPlayer(String nickname) {
        try {
            this.serverConnectedTo.addPlayer(nickname);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server(addPlayer):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        try {
            this.serverConnectedTo.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server(chooseNumberOfPlayerInTheGame):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void startGame() {
        try {
            this.serverConnectedTo.startGame();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server(startGame):" + e.getMessage() + ".Skipping update");
        }
    }

    @Override
    public void run() {
        this.view.run();
    }
}
