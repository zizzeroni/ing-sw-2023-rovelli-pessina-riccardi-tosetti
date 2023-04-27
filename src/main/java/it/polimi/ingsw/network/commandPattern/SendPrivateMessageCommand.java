package it.polimi.ingsw.network.commandPattern;

import it.polimi.ingsw.network.Server;
import java.rmi.RemoteException;

public class SendPrivateMessageCommand implements Command {
    private Server controller;
    private String receiver;
    private String sender;
    private String content;

    public SendPrivateMessageCommand() {
        controller = null;
    }

    public SendPrivateMessageCommand(String receiver, String sender, String content) {
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
    }

    public SendPrivateMessageCommand(Server controller) {
        this.controller = controller;
    }

    public SendPrivateMessageCommand(Server controller, String receiver, String sender, String content) {
        this.controller = controller;
        this.receiver = receiver;
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
        if (this.controller != null) {
            this.controller.sendPrivateMessage(receiver, sender, content);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendPrivateMessage(String,String,String)\" command because this.controller is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:SendPrivateMessage, Parameters:{Receiver:" + receiver + "; Sender:" + sender + "; Content:" + content + "}]";
    }
}
