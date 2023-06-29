package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command of the broadcast message transmission.
 * It contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 * @see CommandToServer
 */
public class SendBroadcastMessageCommand implements CommandToServer {
    private Server actuator;
    private String sender;
    private String content;

    /**
     * Class constructor.
     */
    public SendBroadcastMessageCommand() {
        this.actuator = null;
    }

    /**
     * Class constructor.
     * Initialize the content and the sender of the broadcast message command.
     *
     * @param content the content of the message.
     * @param sender  the sender of the message.
     * @see it.polimi.ingsw.model.Player
     */
    public SendBroadcastMessageCommand(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    /**
     * Class constructor.
     * Initialize the actuator of the broadcast message command.
     *
     * @param actuator the command's actuator.
     * @see it.polimi.ingsw.model.Player
     */
    public SendBroadcastMessageCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * Initialize the actuator, content and sender of the broadcast message command.
     *
     * @param actuator the command's actuator.
     * @param content  the content of the message.
     * @param sender   the sender of the message.
     */
    public SendBroadcastMessageCommand(Server actuator, String sender, String content) {
        this.actuator = actuator;
        this.sender = sender;
        this.content = content;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the broadcast messaging command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the broadcast messaging command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the broadcast messaging command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException      if a connection error occurs
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.sendBroadcastMessage(this.sender, this.content);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendBroadcastMessage(String,String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the broadcast messaging command.
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_BROADCAST_MESSAGE;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Sender: " + this.sender + "; Content: " + this.content + "}]";
    }
}
