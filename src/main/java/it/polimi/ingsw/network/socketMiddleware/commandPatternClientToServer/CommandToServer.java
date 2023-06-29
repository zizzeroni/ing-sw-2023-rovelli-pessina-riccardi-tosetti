package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * This interface extends the {@code Serializable} interface.
 * Allows the passing of a command to the server for its actuation.
 *
 * @see Serializable
 */
public interface CommandToServer extends Serializable {

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the {@code Player}'s command.
     * @see it.polimi.ingsw.model.Player
     */
    public Server getActuator();

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the command.
     * @see it.polimi.ingsw.model.Player
     */
    public void setActuator(Server actuator);

    /**
     * This method permits the execution of the class command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException      if a communication error occurs.
     */
    public void execute() throws NullPointerException, RemoteException;

    /**
     * Enumerates the class command.
     *
     * @return the type of the class command.
     * @see CommandType
     */
    public CommandType toEnum();
}
