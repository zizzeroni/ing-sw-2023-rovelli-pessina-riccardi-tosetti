package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * An implementation of the {@code CommandToServer} interface.
 * It allows to communicate a command sent by the {@code Player} to the {@code Server}.
 *
 * @see CommandToServer
 * @see Server
 * @see it.polimi.ingsw.network.ServerImpl
 * @see it.polimi.ingsw.model.Player
 */
public class ChangeTurnCommand implements CommandToServer {
    private Server actuator;

    /**
     * Class constructor.
     * Initialize the actuator and the {@code Player}'s nickname to the given values.
     *
     * @param actuator the command's actuator.
     * @param nickname the {@code Player}'s nickname.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public ChangeTurnCommand() {
        this.actuator = null;
    }

    /**
     * Class constructor.
     * Initialize the actuator and the {@code Player}'s nickname to the given values.
     *
     * @param actuator the command's actuator.
     * @param nickname the {@code Player}'s nickname.
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
        return "[CommandReceiver:GameController, CommandType:ChangeTurn, Parameters:NONE]";
    }
}
