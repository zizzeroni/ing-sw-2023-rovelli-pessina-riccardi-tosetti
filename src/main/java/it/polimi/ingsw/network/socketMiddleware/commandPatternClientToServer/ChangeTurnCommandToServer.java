package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class ChangeTurnCommandToServer implements CommandToServer {
    private Server actuator;

    public ChangeTurnCommandToServer() {
        this.actuator = null;
    }

    public ChangeTurnCommandToServer(Server actuator) {
        this.actuator = actuator;
    }

    @Override
    public Server getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.changeTurn();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"changeTurn()\" command because this.actuator is NULL");
        }
    }
    @Override
    public CommandType toEnum() {
        return CommandType.CHANGE_TURN;
    }
    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:ChangeTurn, Parameters:NONE]";
    }
}
