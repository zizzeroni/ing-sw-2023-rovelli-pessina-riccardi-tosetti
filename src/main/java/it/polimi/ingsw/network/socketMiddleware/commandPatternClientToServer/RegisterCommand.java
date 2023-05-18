package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class RegisterCommand implements CommandToServer{
    private Server actuator;
    private String nickname;
    private Client client;

    public RegisterCommand(String nickname) {
        this.nickname = nickname;
    }

    public RegisterCommand(Server actuator) {
        this.actuator = actuator;
    }

    public RegisterCommand(Server actuator, String nickname) {
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

    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.register(this.client,this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"register(Client,String)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return null;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:Register, Parameters:{Client: " + this.client + ",Nickname: " + this.nickname + "}]";
    }
}
