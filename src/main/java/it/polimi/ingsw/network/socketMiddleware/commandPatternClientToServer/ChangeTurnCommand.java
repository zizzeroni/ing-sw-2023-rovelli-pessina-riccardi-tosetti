package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that permits to change the {@code Game}'s turn.
 * It contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 *
 * @see CommandToServer
 * @see it.polimi.ingsw.model.Game
 */
public class ChangeTurnCommand implements CommandToServer {
    private Server actuator;

    /**
     * Class constructor.
     */
    public ChangeTurnCommand() {
        this.actuator = null;
    }

    /**
     * Class constructor.
     * Initialize the actuator to the given value.
     *
     * @param actuator the command's actuator.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public ChangeTurnCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Gets the change turn command's actuator.
     *
     * @return the actuator of the change turn command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the change turn command's actuator.
     *
     * @param actuator the actuator of the change turn command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the change turn command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.changeTurn();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"changeTurn()\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the change turn command.
     *
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.CHANGE_TURN;
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
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters: NONE]";
    }
}
