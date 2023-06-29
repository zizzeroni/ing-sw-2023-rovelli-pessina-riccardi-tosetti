package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class is used to access methods related to the client sided stored games retrieving.
 *
 * @see it.polimi.ingsw.model.Game
 */
public class AreThereStoredGamesForPlayerCommand implements CommandToServer {
    private Server actuator;
    private String nickname;

    /**
     * Class constructor.
     *
     * @param nickname is the nickname of the player associated to his stored games.
     */
    public AreThereStoredGamesForPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Class constructor.
     *
     * @param actuator the server that receive the message
     */
    public AreThereStoredGamesForPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     *
     * @param nickname is the nickname of the player associated to his stored games.
     * @param actuator the server that receive the message
     */
    public AreThereStoredGamesForPlayerCommand(Server actuator, String nickname) {
        this.actuator = actuator;
        this.nickname = nickname;
    }

    /**
     * Gets the change turn command's actuator.
     *
     * @return the actuator of the class command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the change turn command's actuator.
     *
     * @param actuator the actuator of the class command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the class command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException      if a connection error occurs
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.areThereStoredGamesForPlayer(this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"areThereStoredGamesForPlayer(String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the class command.
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.DISCONNECT_PLAYER;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:GameController, CommandType:areThereStoredGamesForPlayer, Parameters:{Nickname: " + this.nickname + "}]";
    }
}
