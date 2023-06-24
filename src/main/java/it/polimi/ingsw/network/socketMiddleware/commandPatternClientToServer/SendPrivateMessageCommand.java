package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class SendPrivateMessageCommand implements CommandToServer {
    private Server actuator;
    private String receiver;
    private String sender;
    private String content;

    public SendPrivateMessageCommand() {
        this.actuator = null;
    }

    public SendPrivateMessageCommand(String receiver, String sender, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    public SendPrivateMessageCommand(Server actuator) {
        this.actuator = actuator;
    }

    public SendPrivateMessageCommand(Server actuator, String receiver, String sender, String content) {
        this.actuator = actuator;
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    @Override
    public Server getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.sendPrivateMessage(this.receiver, this.sender, this.content);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendPrivateMessage(String,String,String)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.SEND_PRIVATE_MESSAGE;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Receiver: " + this.receiver + "; Sender: " + this.sender + "; Content: " + this.content + "}]";
    }
}
