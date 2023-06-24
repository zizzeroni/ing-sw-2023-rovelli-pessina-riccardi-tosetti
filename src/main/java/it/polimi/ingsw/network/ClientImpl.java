package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.view.GUI.UI;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client, ViewListener, Runnable {
    private final Server serverConnectedTo;
    private final UI view;

    public ClientImpl(Server server, UI view) throws RemoteException {
        super();
        this.serverConnectedTo = server;
        this.view = view;
        server.register(this, null);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, String nickname) throws RemoteException {
        super();
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNickname(nickname);
        server.register(this, nickname);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, String nickname, int port) throws RemoteException {
        super(port);
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNickname(nickname);
        server.register(this, nickname);
        view.registerListener(this);
    }

    public ClientImpl(Server server, UI view, String nickname, int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
        this.serverConnectedTo = server;
        this.view = view;
        this.view.setNickname(nickname);
        server.register(this, nickname);
        view.registerListener(this);
    }

    //Update coming from the server, I forward it to the view
    @Override
    public synchronized void updateModelView(GameView modelUpdated) throws RemoteException {
        this.view.modelModified(modelUpdated);
    }

    @Override
    public synchronized void ping() throws RemoteException {
        //Receiving ping from server... do nothing.
    }

    @Override
    public synchronized void receiveException(GenericException exception) throws RemoteException {
        this.view.printException(exception);
    }

    //Methods used for forwarding notifications from view to the server
    @Override
    public void changeTurn() {
        try {
            this.serverConnectedTo.changeTurn();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"changeTurn()\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        try {
            this.serverConnectedTo.insertUserInputIntoModel(playerChoice);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"insertUserInputIntoModel(Choice)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        try {
            this.serverConnectedTo.sendPrivateMessage(receiver, sender, content);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"sendPrivateMessage(String,String,String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        try {
            this.serverConnectedTo.sendBroadcastMessage(sender, content);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"sendBroadcastMessage(String,String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void addPlayer(String nickname) {
        try {
            this.serverConnectedTo.addPlayer(this, nickname);
            this.serverConnectedTo.tryToResumeGame();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Error while updating server: " + this.serverConnectedTo + ", error caused by \"addPlayer(String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        try {
            this.serverConnectedTo.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"chooseNumberOfPlayerInTheGame(int)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void startGame() {
        try {
            this.serverConnectedTo.startGame();
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"startGame()\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void disconnectPlayer(String nickname) {
        try {
            this.serverConnectedTo.disconnectPlayer(nickname);
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] while updating server: " + this.serverConnectedTo + ", error caused by \"disconnectPlayer(String)\" invocation:\n  " + e.getMessage() + ".Skipping server update");
        }
    }

    @Override
    public void run() {
        this.view.run();
    }

}
