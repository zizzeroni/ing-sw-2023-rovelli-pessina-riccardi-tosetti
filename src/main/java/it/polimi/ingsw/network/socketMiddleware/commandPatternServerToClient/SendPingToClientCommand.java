package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that allows to ping the {@code Player}'s {@code Client}.
 * It implements the {@code CommandToClient} interface.
 *
 * @see CommandToClient
 * @see it.polimi.ingsw.model.Player
 * @see Client
 */
public class SendPingToClientCommand implements CommandToClient {
    private Client actuator;

    /**
     * Class constructor.
     *
     * @param actuator the command's actuator.
     */
    public SendPingToClientCommand(Client actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * <p>
     * Initialize the command's actuator to its default value (null).
     */
    public SendPingToClientCommand() {
        this.actuator = null;
    }

    /**
     * Gets the client's ping sending command's actuator.
     *
     * @return the actuator of the client's ping sending command.
     * @see Client
     */
    @Override
    public Client getActuator() {
        return this.actuator;
    }

    /**
     * Sets the client's ping sending command's actuator.
     *
     * @param actuator the actuator of the client's ping sending command.
     * @see Client
     */
    @Override
    public void setActuator(Client actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the client's ping sending command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     * @see Client
     */
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.ping();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"ping()\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the client's ping sending command.
     * @see CommandType
     * @see it.polimi.ingsw.network.ClientImpl
     * @see Client
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PING_TO_CLIENT;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver and command's parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Client, CommandType:" + this.toEnum() + ", Parameters: NONE]";
    }
}
