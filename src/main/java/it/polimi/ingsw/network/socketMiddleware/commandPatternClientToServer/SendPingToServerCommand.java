package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class SendPingToServerCommand implements CommandToServer {
    private Server actuator;

    public SendPingToServerCommand(Server actuator) {
        this.actuator = actuator;
    }

    public SendPingToServerCommand() {
        this.actuator = null;
    }

    @Override
    public Server getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Server actuator) {
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
        return CommandType.SEND_PING_TO_SERVER;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters: NONE]";
    }
}
