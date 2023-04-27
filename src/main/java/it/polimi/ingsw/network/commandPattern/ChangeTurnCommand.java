package it.polimi.ingsw.network.commandPattern;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.network.Server;

import java.io.Serializable;
import java.rmi.RemoteException;

public class ChangeTurnCommand implements Command {
    private Server controller;

    public ChangeTurnCommand() {
        controller = null;
    }

    public ChangeTurnCommand(Server controller) {
        this.controller = controller;
    }

    @Override
    public Server getController() {
        return controller;
    }

    @Override
    public void setController(Server controller) {
        this.controller = controller;
    }

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (controller != null) {
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
