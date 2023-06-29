package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that enables {@code Player}'s disconnection.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 * @see CommandToServer
 * @see it.polimi.ingsw.model.Player
 */
public class DisconnectPlayerCommand implements CommandToServer {
    private Server actuator;
    private String nickname;

    /**
     * Class constructor.
     * Initialize the {@code Player}'s command, based on his nickname.
     *
     * @param nickname is the {@code Player}'s nickname.
     * @see it.polimi.ingsw.model.Player
     */
    public DisconnectPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Class constructor.
     * Initialize the {@code Player}'s command, based on the actuator.
     *
     * @param actuator is the server receiving the command.
     * @see it.polimi.ingsw.model.Player
     */
    public DisconnectPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * Initialize the actuator and the {@code Player}'s nickname to the given values.
     *
     * @param actuator the command's actuator.
     * @param nickname the {@code Player}'s nickname.
     * @see it.polimi.ingsw.model.Player
     */
    public DisconnectPlayerCommand(Server actuator, String nickname) {
        this.actuator = actuator;
        this.nickname = nickname;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the {@code Player}'s disconnection command.
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the {@code Player}'s disconnection command.
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the {@code PLayer}'s disconnection command.
     *
     * @throws NullPointerException if there is no actuator to execute the command.
     * @throws RemoteException      called if a communication error occurs.
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.disconnectPlayer(this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"disconnectPlayer(String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the {@code Player}'s disconnection command.
     * @see CommandType
     * @see it.polimi.ingsw.model.Player
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
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Nickname: " + this.nickname + "}]";
    }
}
