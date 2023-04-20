package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ControllerListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.TextualUI;
import it.polimi.ingsw.view.UI;

import java.rmi.RemoteException;

public class ClientImpl implements Client, ControllerListener,Runnable {

    private Server serverConnectedTo;
    UI view;

    public ClientImpl(Server server) {
        serverConnectedTo=server;
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        try {
            this.serverConnectedTo.insertUserInputIntoModel(playerChoice);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPrivateMessage(Player receiver, Player sender, String content) {
        try {
            this.serverConnectedTo.sendPrivateMessage(receiver,sender,content);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendBroadcastMessage(Player sender, String content) {
        try {
            this.serverConnectedTo.sendBroadcastMessage(sender,content);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        view.run();
    }
}
