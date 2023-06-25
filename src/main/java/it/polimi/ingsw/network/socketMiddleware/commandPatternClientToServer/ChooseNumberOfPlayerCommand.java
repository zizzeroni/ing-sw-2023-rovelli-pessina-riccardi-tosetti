package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class ChooseNumberOfPlayerCommand implements CommandToServer {
    private Server actuator;
    private int numberOfPlayers;

    public ChooseNumberOfPlayerCommand(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ChooseNumberOfPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    public ChooseNumberOfPlayerCommand(Server actuator, int numberOfPlayers) {
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
    public CommandType toEnum() {
        return CommandType.CHOOSE_NUMBER_OF_PLAYER;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{NumberOfPlayers: " + this.numberOfPlayers + "}]";
    }
}
