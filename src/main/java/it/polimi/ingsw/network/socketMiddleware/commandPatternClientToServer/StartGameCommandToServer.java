package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class StartGameCommandToServer implements CommandToServer {
    private Server actuator;

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
            this.actuator.startGame();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"startGame()\" command because this.actuator is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:StartGame, Parameters:NONE]";
    }
}
