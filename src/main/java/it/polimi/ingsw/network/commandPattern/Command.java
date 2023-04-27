package it.polimi.ingsw.network.commandPattern;

import it.polimi.ingsw.network.Server;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface Command extends Serializable {
    public Server getController();

    public void setController(Server controller);

    public void execute() throws NullPointerException, RemoteException;
}
