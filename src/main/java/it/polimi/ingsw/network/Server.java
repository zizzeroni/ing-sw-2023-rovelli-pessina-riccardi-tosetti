package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.ControllerListener;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.view.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Server extends Remote {
    //Methods used by the clients to notify the Server of some events
    public void changeTurn() throws RemoteException;

    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException;

    public void sendPrivateMessage(Player receiver, Player sender, String content) throws RemoteException;

    public void sendBroadcastMessage(Player sender, String content) throws RemoteException;


    public GameView register(Client client);
}
