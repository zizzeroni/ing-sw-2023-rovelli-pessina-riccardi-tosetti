package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface CommandToClient extends Serializable {
    public Client getActuator();

    public void setActuator(Client actuator);

    public void execute() throws NullPointerException, RemoteException;
}
