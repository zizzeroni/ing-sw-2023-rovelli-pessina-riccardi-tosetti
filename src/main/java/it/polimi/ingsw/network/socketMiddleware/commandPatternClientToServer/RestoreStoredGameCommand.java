package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This command allow to restore the games that have been previously saved.
 *
 * @see it.polimi.ingsw.model.Game
 */
public class RestoreStoredGameCommand implements CommandToServer {
    private Server actuator;
    private String nickname;

    /**
     * Class builder.
     * Initialize class parameters, including player's nickname.
     *
     *
     * @param nickname the nickname to be initialized.
     */
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

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the games' restoring command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException if a communication error occurs.
     *
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.restoreGameForPlayer(this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"restoreGameForPlayer(String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Enumerates the games' restoring  command.
     *
     * @return the type of the class command.
     *
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.RESTORE_STORED_GAME;
    }

    /**
     * This method is used to identify the String to be sent as message, while setting all its parameters.
     *
     * @return the String message to be sent.
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:restoreGameForPlayer, Parameters:{Nickname: " + this.nickname + "}]";
    }
}
