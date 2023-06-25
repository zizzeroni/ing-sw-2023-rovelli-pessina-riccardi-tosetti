package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class InsertUserInputCommand implements CommandToServer {
    private Server actuator;
    private Choice playerChoice;

    public InsertUserInputCommand() {
        this.actuator = null;
    }

    public InsertUserInputCommand(Choice playerChoice) {
        this.playerChoice = playerChoice;
    }

    public InsertUserInputCommand(Server actuator) {
        this.actuator = actuator;
    }

    public InsertUserInputCommand(Server actuator, Choice playerChoice) {
        this.actuator = actuator;
        this.playerChoice = playerChoice;
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
            this.actuator.insertUserInputIntoModel(this.playerChoice);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"insertUserInputIntoModel(Choice)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.INSERT_USER_INPUT;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{PlayerChoice: " + this.playerChoice + "}]";
    }
}
