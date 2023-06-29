package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that enables registration of the {@code Player}.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 * @see CommandToServer
 * @see it.polimi.ingsw.model.Player
 */
public class RegisterCommand implements CommandToServer {
    private Server actuator;
    private String nickname;
    private Client client;

    /**
     * Class constructor.
     * Initialize the {@code Player}'s nickname to the given value.
     *
     * @param nickname the {@code Player}'s nickname.
     * @see it.polimi.ingsw.model.Player
     */
    public RegisterCommand(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Class constructor.
     * Initialize the actuator to the given value.
     *
     * @param actuator the command's actuator.
     */
    public RegisterCommand(Server actuator) {
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
    public RegisterCommand(Server actuator, String nickname) {
        this.actuator = actuator;
        this.nickname = nickname;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the registration command.
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the registration command.
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the registration command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException if a connection error occurs
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.register(this.client, this.nickname);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"register(Client,String)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the registration command.
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.REGISTER;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{Client: " + this.client + ",Nickname: " + this.nickname + "}]";
    }
}


