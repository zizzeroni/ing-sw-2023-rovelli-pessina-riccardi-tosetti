package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class ChooseNumberOfPlayerCommandToServer implements CommandToServer {
    private Server actuator;
    private int numberOfPlayers;

    public ChooseNumberOfPlayerCommandToServer(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ChooseNumberOfPlayerCommandToServer(Server actuator) {
        this.actuator = actuator;
    }

    public ChooseNumberOfPlayerCommandToServer(Server actuator, int numberOfPlayers) {
        this.actuator = actuator;
        this.numberOfPlayers = numberOfPlayers;
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
            this.actuator.chooseNumberOfPlayerInTheGame(this.numberOfPlayers);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"chooseNumberOfPlayerInTheGame(int)\" command because this.actuator is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:ChooseNumberOfPlayers, Parameters:{NumberOfPlayers: " + this.numberOfPlayers + "}]";
    }
}
