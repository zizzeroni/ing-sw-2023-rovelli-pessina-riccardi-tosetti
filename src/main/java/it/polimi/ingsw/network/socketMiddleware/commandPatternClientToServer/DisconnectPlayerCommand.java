package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class DisconnectPlayerCommand implements CommandToServer {
    private Server actuator;
    private String nickname;

    public DisconnectPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    public DisconnectPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    public DisconnectPlayerCommand(Server actuator, String nickname) {
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
            this.actuator.disconnectPlayer(this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"disconnectPlayer(String)\" command because this.actuator is NULL");
        }
    }
    @Override
    public CommandType toEnum() {
        return CommandType.DISCONNECT_PLAYER;
    }
    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:disconnectPlayer, Parameters:{Nickname: " + this.nickname + "}]";
    }
}
