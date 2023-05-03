package it.polimi.ingsw.network.socketMiddleware.commandPattern;

import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class ChooseNumberOfPlayerCommand implements Command {
    private Server controller;
    private int numberOfPlayers;

    public ChooseNumberOfPlayerCommand(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public ChooseNumberOfPlayerCommand(Server controller) {
        this.controller = controller;
    }

    public ChooseNumberOfPlayerCommand(Server controller, int numberOfPlayers) {
        this.controller = controller;
        this.numberOfPlayers = numberOfPlayers;
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
            this.controller.chooseNumberOfPlayerInTheGame(this.numberOfPlayers);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"chooseNumberOfPlayerInTheGame(int)\" command because this.controller is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:ChooseNumberOfPlayers, Parameters:{NumberOfPlayers: " + this.numberOfPlayers + "}]";
    }
}
