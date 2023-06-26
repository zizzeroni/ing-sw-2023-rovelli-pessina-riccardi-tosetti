package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class RestoreStoredGameCommand implements CommandToServer {
    private Server actuator;
    private String nickname;

    public RestoreStoredGameCommand(String nickname) {
        this.nickname = nickname;
    }

    public RestoreStoredGameCommand(Server actuator) {
        this.actuator = actuator;
    }

    public RestoreStoredGameCommand(Server actuator, String nickname) {
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
            this.actuator.restoreGameForPlayer(this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"restoreGameForPlayer(String)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.RESTORE_STORED_GAME;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:restoreGameForPlayer, Parameters:{Nickname: " + this.nickname + "}]";
    }
}