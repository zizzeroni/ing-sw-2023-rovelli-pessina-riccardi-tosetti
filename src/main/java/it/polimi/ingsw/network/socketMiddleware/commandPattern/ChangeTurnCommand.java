package it.polimi.ingsw.network.socketMiddleware.commandPattern;

import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class ChangeTurnCommand implements Command {
    private Server controller;

    public ChangeTurnCommand() {
        this.controller = null;
    }

    public ChangeTurnCommand(Server controller) {
        this.controller = controller;
    }

    @Override
    public Server getController() {
        return this.controller;
    }

    @Override
    public void setController(Server controller) {
        this.controller = controller;
    }

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.controller != null) {
            this.controller.changeTurn();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"changeTurn()\" command because this.controller is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:ChangeTurn, Parameters:NONE]";
    }
}
