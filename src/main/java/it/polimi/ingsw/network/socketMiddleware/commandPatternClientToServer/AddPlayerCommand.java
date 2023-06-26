package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * An implementation of the {@code CommandToServer} interface.
 * It allows to communicate a command sent by the {@code Player} to the {@code Server}.
 *
 * @see CommandToServer
 * @see Server
 * @see it.polimi.ingsw.network.ServerImpl
 * @see it.polimi.ingsw.model.Player
 */
public class AddPlayerCommand implements CommandToServer {
    private Server actuator;
    private String nickname;
    private Client client;

    /**
     * Class constructor.
     * Initialize the {@code Player}'s command, based on his nickname.
     *
     * @param nickname is the {@code Player}'s nickname.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public AddPlayerCommand(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Class constructor.
     * Initialize the {@code Player}'s command, based on the actuator.
     *
     * @param actuator is the server receiving the command.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public AddPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * Initialize the actuator and the {@code Player}'s nickname to the given values.
     *
     * @param actuator the command's actuator.
     * @param nickname the {@code Player}'s nickname.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public AddPlayerCommand(Server actuator, String nickname) {
        this.actuator = actuator;
        this.nickname = nickname;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the {@code Player}'s command.
     *
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the {@code Player}'s command.
     *
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Getter to identify the {@code Player}'s client.
     *
     * @return the player's client.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public Client getClient() {
        return client;
    }

    /**
     * Sets the client for the {@code Player} giving the command.
     *
     * @param client the client to be set.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * This method permits the execution of the {@code PLayer}'s command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     *
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.addPlayer(this.client, this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"addPlayer(Client,String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the add {@code Player}'s command.
     *
     * @see CommandType
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public CommandType toEnum() {
        return CommandType.ADD_PLAYER;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     *
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Client: " + this.client + ",Nickname: " + this.nickname + "}]";
    }
}
