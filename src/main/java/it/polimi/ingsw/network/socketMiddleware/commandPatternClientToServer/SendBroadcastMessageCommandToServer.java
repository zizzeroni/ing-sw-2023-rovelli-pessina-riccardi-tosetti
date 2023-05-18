package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class SendBroadcastMessageCommandToServer implements CommandToServer {
    private Server actuator;
    private String sender;
    private String content;

    public SendBroadcastMessageCommandToServer() {
        this.actuator = null;
    }

    public SendBroadcastMessageCommandToServer(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public SendBroadcastMessageCommandToServer(Server actuator) {
        this.actuator = actuator;
    }

    public SendBroadcastMessageCommandToServer(Server actuator, String sender, String content) {
        this.actuator = actuator;
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
            this.actuator.sendBroadcastMessage(this.sender, this.content);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendBroadcastMessage(String,String)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.SEND_BROADCAST_MESSAGE;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:SendBroadcastMessage, Parameters:{Sender:" + this.sender + "; Content:" + this.content + "}]";
    }
}
