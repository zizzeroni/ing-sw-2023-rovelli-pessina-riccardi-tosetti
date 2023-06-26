package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 *
 */
public class SendPingToClientCommand implements CommandToClient {
    private Client actuator;

    public SendPingToClientCommand(Client actuator) {
        this.actuator = actuator;
    }

    public SendPingToClientCommand() {
        this.actuator = null;
    }

    @Override
    public Client getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Client actuator) {
        this.actuator = actuator;
    }

    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.ping();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"ping()\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PING_TO_CLIENT;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Client, CommandType:" + this.toEnum() + ", Parameters: NONE]";
    }
}
