package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that enables user's input insertion.
 * Contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 *
 * @see CommandToServer
 * @see it.polimi.ingsw.GUI.User
 */
public class InsertUserInputCommand implements CommandToServer {
    private Server actuator;
    private Choice playerChoice;

    /**
     * Class constructor.
     */
    public InsertUserInputCommand() {
        this.actuator = null;
    }

    /**
     * Class constructor.
     * Initialize the {@code playerChoice} to the given value.
     *
     * @param playerChoice the {@code Player}'s choice.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Choice
     */
    public InsertUserInputCommand(Choice playerChoice) {
        this.playerChoice = playerChoice;
    }

    /**
     * Class constructor.
     * Initialize the command's actuator to the given value.
     *
     * @param actuator the command's actuator.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public InsertUserInputCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * Initialize the {@code playerChoice} and actuator to the given values.
     *
     * @param playerChoice the {@code Player}'s choice.
     * @param actuator the command's actuator.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Choice
     */
    public InsertUserInputCommand(Server actuator, Choice playerChoice) {
        this.actuator = actuator;
        this.playerChoice = playerChoice;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the user's input command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the user's input command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the user's input command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.insertUserInputIntoModel(this.playerChoice);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"insertUserInputIntoModel(Choice)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the user's input command.
     *
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.INSERT_USER_INPUT;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     *
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:InsertUserInput, Parameters:{PlayerChoice: " + this.playerChoice + "}]";
    }
}
