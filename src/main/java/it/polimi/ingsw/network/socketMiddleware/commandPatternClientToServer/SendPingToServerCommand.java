package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that permits to send a ping to the {@code Server}.
 * It contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 * @see CommandToServer
 * @see Server
 * @see it.polimi.ingsw.network.ServerImpl
 */
public class SendPingToServerCommand implements CommandToServer {
    private Server actuator;

    /**
     * Class constructor.
     * Initialize the actuator to the given value.
     *
     * @param actuator the command's actuator.
     */
    public SendPingToServerCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     */
    public SendPingToServerCommand() {
        this.actuator = null;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the server's ping sending command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the server's ping sending command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the server's ping sending command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException if a connection error occurs
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
     * @return the {@code CommandType} of the server's ping sending command.
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PING_TO_SERVER;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters: NONE]";
    }
}
