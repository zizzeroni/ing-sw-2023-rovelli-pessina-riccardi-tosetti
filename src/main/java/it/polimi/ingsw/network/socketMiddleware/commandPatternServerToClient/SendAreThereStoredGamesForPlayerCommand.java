package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class SendAreThereStoredGamesForPlayerCommand implements CommandToClient {
    private Client actuator;
    private final boolean result;

    public SendAreThereStoredGamesForPlayerCommand(Client actuator, boolean result) {
        this.actuator = actuator;
        this.result = result;
    }

    public SendAreThereStoredGamesForPlayerCommand(boolean result) {
        this.result = result;
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
            this.actuator.setAreThereStoredGamesForPlayer(this.result);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"updateModelView(boolean)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.SEND_UPDATED_MODEL;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:updateModelView, Parameters:{modelUpdated: " + this.result + "}]";
    }
}
