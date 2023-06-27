package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 *
 */
public class AreThereStoredGamesForPlayerCommand implements CommandToServer {
    private Server actuator;
    private String nickname;

    public AreThereStoredGamesForPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    public AreThereStoredGamesForPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    public AreThereStoredGamesForPlayerCommand(Server actuator, String nickname) {
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
            this.actuator.areThereStoredGamesForPlayer(this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"areThereStoredGamesForPlayer(String)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.DISCONNECT_PLAYER;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:areThereStoredGamesForPlayer, Parameters:{Nickname: " + this.nickname + "}]";
    }
}
