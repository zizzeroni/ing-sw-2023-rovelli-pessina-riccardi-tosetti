package it.polimi.ingsw.network.socketMiddleware.commandPattern;

import it.polimi.ingsw.network.Server;
import java.rmi.RemoteException;

public class SendBroadcastMessageCommand implements Command {
    private Server controller;
    private String sender;
    private String content;

    public SendBroadcastMessageCommand() {
        controller = null;
    }

    public SendBroadcastMessageCommand(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public SendBroadcastMessageCommand(Server controller) {
        this.controller = controller;
    }

    public SendBroadcastMessageCommand(Server controller, String sender, String content) {
        this.controller = controller;
        this.sender = sender;
        this.content = content;
    }

    @Override
    public Server getController() {
        return this.controller;
    }

    @Override
    public void setController(Server controller) {
        this.controller = controller;
    }

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (controller != null) {
            this.controller.sendBroadcastMessage(sender, content);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendBroadcastMessage(String,String)\" command because this.controller is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:SendBroadcastMessage, Parameters:{Sender:" + sender + "; Content:" + content + "}]";
    }
}
