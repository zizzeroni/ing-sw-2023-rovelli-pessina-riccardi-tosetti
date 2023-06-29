package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command of the private message transmission.
 * It contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 * @see CommandToServer
 */
public class SendPrivateMessageCommand implements CommandToServer {
    private Server actuator;
    private String receiver;
    private String sender;
    private String content;

    /**
     * Class constructor.
     */
    public SendPrivateMessageCommand() {
        this.actuator = null;
    }

    /**
     * Class constructor.
     * Initialize the content, sender and receiver of the private message command.
     *
     * @param content  the content of the message.
     * @param sender   the sender of the message.
     * @param receiver the receiver of the message.
     */
    public SendPrivateMessageCommand(String receiver, String sender, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    /**
     * Class constructor.
     * Initialize the actuator to the given value.
     *
     * @param actuator the command's actuator.
     */
    public SendPrivateMessageCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * Initialize the actuator, content, sender and receiver of the private message command.
     *
     * @param actuator the command's actuator.
     * @param content  the content of the message.
     * @param sender   the sender of the message.
     * @param receiver the receiver of the message.
     */
    public SendPrivateMessageCommand(Server actuator, String receiver, String sender, String content) {
        this.actuator = actuator;
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the private message sending command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the private message sending command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the private message sending command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.sendPrivateMessage(this.receiver, this.sender, this.content);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendPrivateMessage(String,String,String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the private message sending command.
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PRIVATE_MESSAGE;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Receiver: " + this.receiver + "; Sender: " + this.sender + "; Content: " + this.content + "}]";
    }
}
