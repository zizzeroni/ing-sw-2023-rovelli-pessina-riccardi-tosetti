package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

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
        //Do nothing, you just received a ping message...
    }

    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PING_TO_CLIENT;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:NONE, Parameters:NONE]";
    }
}
