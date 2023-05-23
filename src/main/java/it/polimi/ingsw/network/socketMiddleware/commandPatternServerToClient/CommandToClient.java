package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.io.Serializable;
import java.rmi.RemoteException;

public interface CommandToClient extends Serializable {
    public Client getActuator();

    public void setActuator(Client actuator);

    public void execute() throws NullPointerException, RemoteException;

    public CommandType toEnum();
}
