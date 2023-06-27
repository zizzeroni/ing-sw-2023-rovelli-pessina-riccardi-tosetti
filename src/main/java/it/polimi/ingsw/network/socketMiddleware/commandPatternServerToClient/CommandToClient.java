package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This class represents the command's archetype.
 * It contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an extension of the {@code Serializable} interface.
 *
 * @see Serializable
 */

public interface CommandToClient extends Serializable {

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the command.
     */
    public Client getActuator();

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the command.
     */
    public void setActuator(Client actuator);

    /**
     * This method permits the execution of the command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException called when a communication error occurs.
     */
    public void execute() throws NullPointerException, RemoteException;

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the command.
     *
     * @see CommandType
     */
    public CommandType toEnum();
}
