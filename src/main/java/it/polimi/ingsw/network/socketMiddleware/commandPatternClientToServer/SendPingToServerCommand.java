package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;

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
        //Do nothing, you just received a ping message...
    }

    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:NONE, Parameters:NONE]";
    }
}
