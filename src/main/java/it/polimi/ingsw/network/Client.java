package it.polimi.ingsw.network;

import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.GameView;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface provides a general presentation of the client's methods
 * that are implemented in the {@code ClientImpl}.
 * The methods allow the server to update the client's model view and to ping the client, also allows the client to receive
 * client's exceptions.
 *
 *
 * @see ClientImpl
 */
public interface Client extends Remote {
     
    /**
     * This method permits to convey the updated model view.
     *
     * @param modelUpdated contains the model updates.
     * @throws RemoteException is called when a communication error occurs and the modelView can't be sent.
     *
     * @see javax.swing.text.View
     */
    public void updateModelView(GameView modelUpdated) throws RemoteException;

    /**
     * Allows to ping the client.
     *
     * @throws RemoteException signals the occurrence of a communication error with the client.
     *
     * @see Client
     */
    public void ping() throws RemoteException;

    /**
     * Used to receive a generic exception in order to handle it.
     *
     * @param exception the GENERIC except being received.
     * @throws RemoteException called when a communication error with the client occurs.
     *
     * @see Client
     */
    public void receiveException(GenericException exception) throws RemoteException;

    public void setAreThereStoredGamesForPlayer(boolean result) throws RemoteException;
}
