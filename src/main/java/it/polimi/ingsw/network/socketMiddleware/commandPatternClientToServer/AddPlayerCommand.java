package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class AddPlayerCommand implements CommandToServer {
    private Server actuator;
    private String nickname;
    private Client client;

    public AddPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    public AddPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    public AddPlayerCommand(Server actuator, String nickname) {
        this.actuator = actuator;
        this.nickname = nickname;
    }

    @Override
    public Server getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.addPlayer(this.client, this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"addPlayer(Client,String)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.ADD_PLAYER;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Client: " + this.client + ",Nickname: " + this.nickname + "}]";
    }
}
