package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.exceptions.GenericException;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that allows to notify about an occurred exception
 * during the normal development of the {@code Game} to the various {@code Players}' {@code Client}s.
 * It implements the {@code CommandToClient} interface.
 *
 * @see it.polimi.ingsw.model.Game
 * @see CommandToClient
 * @see Client
 * @see it.polimi.ingsw.network.ClientImpl
 * @see it.polimi.ingsw.model.Player
 */
public class SendExceptionCommand implements CommandToClient {
    private Client actuator;
    private GenericException exception;

    /**
     * Class constructor.
     *
     * Initialize the exception attribute to the given exception's value.
     *
     * @param exception the attribute to be initialized.
     */
    public SendExceptionCommand(GenericException exception) {
        this.exception = exception;
    }

    /**
     * Class constructor.
     *
     * @param actuator the command's actuator.
     */
    public SendExceptionCommand(Client actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     *
     * Initialize the actuator to its default value (null).
     */
    public SendExceptionCommand() {
        this.actuator = null;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the exception sending command.
     */
    @Override
    public Client getActuator() {
        return this.actuator;
    }

    /**
     * Sets the exception sending command's actuator.
     *
     * @param actuator the actuator of the exception sending command.
     */
    @Override
    public void setActuator(Client actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the exception sending command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     */
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.receiveException(this.exception);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"receiveException(GameView)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the exception sending command.
     *
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.EXCEPTION;
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
        return "[CommandReceiver:UI, CommandType:NONE, Parameters:NONE]";
    }
}
