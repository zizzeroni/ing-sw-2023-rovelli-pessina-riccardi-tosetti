package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface CommandToServer extends Serializable {
    public Server getActuator();

    public void setActuator(Server actuator);

    public void execute() throws NullPointerException, RemoteException;
}
