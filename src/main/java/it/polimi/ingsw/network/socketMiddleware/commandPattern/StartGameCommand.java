package it.polimi.ingsw.network.socketMiddleware.commandPattern;

import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class StartGameCommand implements Command {
    private Server controller;

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
            this.controller.startGame();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"startGame()\" command because this.controller is NULL");
        }
    }
}
