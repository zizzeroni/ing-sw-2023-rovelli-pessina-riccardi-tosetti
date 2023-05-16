package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class InsertUserInputCommandToServer implements CommandToServer {
    private Server actuator;
    private Choice playerChoice;

    public InsertUserInputCommandToServer() {
        this.actuator = null;
    }

    public InsertUserInputCommandToServer(Choice playerChoice) {
        this.playerChoice = playerChoice;
    }

    public InsertUserInputCommandToServer(Server actuator) {
        this.actuator = actuator;
    }

    public InsertUserInputCommandToServer(Server actuator, Choice playerChoice) {
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
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:InsertUserInput, Parameters:{PlayerChoice: " + this.playerChoice + "}]";
    }
}
