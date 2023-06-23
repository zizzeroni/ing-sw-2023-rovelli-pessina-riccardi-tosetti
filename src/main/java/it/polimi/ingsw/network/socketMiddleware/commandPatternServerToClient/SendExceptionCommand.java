package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class SendExceptionCommand implements CommandToClient {
    private Client actuator;
    private GenericException exception;

    public SendExceptionCommand(GenericException exception) {
        this.exception = exception;
    }

    public SendExceptionCommand(Client actuator) {
        this.actuator = actuator;
    }

    public SendExceptionCommand() {
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
            this.actuator.receiveException(this.exception);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"receiveException(GameView)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.EXCEPTION;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:NONE, Parameters:NONE]";
    }
}
