package it.polimi.ingsw.network;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.model.exceptions.GenericException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    //Method used by Server to forward an updated model to the client
    public void updateModelView(GameView modelUpdated) throws RemoteException;

    public void ping() throws RemoteException;

    public void receiveException(GenericException exception) throws RemoteException;
}
