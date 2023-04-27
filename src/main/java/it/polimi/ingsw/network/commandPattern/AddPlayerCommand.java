package it.polimi.ingsw.network.commandPattern;

import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class AddPlayerCommand implements Command {
    private Server controller;
    private String nickname;

    public AddPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    public AddPlayerCommand(Server controller) {
        this.controller = controller;
    }

    public AddPlayerCommand(Server controller, String nickname) {
        this.controller = controller;
        this.nickname = nickname;
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
            this.controller.addPlayer(nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"sendBroadcastMessage(Choice)\" command because this.controller is NULL");
        }
    }
    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:AddPlayer, Parameters:{Nickname: " + nickname + "}]";
    }
}
