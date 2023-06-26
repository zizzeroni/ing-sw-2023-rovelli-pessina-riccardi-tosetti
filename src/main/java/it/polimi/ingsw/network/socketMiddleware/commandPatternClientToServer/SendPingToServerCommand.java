package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 *
 */
public class SendPingToServerCommand implements CommandToServer {
    private Server actuator;

    /**
     * @param actuator
     */
    public SendPingToServerCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     *
     */
    public SendPingToServerCommand() {
        this.actuator = null;
    }

    /**
     * @return
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * @param actuator
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * @throws NullPointerException
     * @throws RemoteException
     */
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.ping();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"ping()\" command because this.actuator is NULL");
        }
    }

    /**
     * @return
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PING_TO_SERVER;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters: NONE]";
    }
}
