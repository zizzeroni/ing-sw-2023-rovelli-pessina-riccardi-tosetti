package it.polimi.ingsw.network.commandPattern;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class InsertUserInputCommand implements Command {
    private Server controller;
    private Choice playerChoice;

    public InsertUserInputCommand() {
        controller = null;
    }

    public InsertUserInputCommand(Choice playerChoice) {
        this.playerChoice = playerChoice;
    }

    public InsertUserInputCommand(Server controller) {
        this.controller = controller;
    }

    public InsertUserInputCommand(Server controller, Choice playerChoice) {
        this.controller = controller;
        this.playerChoice = playerChoice;
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
        if (controller != null) {
            this.controller.insertUserInputIntoModel(playerChoice);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"insertUserInputIntoModel(Choice)\" command because this.controller is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:InsertUserInput, Parameters:{PlayerChoice: " + playerChoice + "}]";
    }
}
