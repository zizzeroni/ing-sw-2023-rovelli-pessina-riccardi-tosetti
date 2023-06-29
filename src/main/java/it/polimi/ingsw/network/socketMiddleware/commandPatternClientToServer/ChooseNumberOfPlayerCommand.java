package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that permits to choose the number of the {@code Player}.
 * It contains a series of methods to access and modify the class attributes (getters and setters)
 * and for the associated command execution and displaying.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 * @see CommandToServer
 * @see it.polimi.ingsw.model.Player
 */

public class ChooseNumberOfPlayerCommand implements CommandToServer {
    private Server actuator;
    private int numberOfPlayers;

    /**
     * Class constructor.
     *
     * @param numberOfPlayers number of players to start the game
     */
    public ChooseNumberOfPlayerCommand(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Class constructor.
     * Initialize the content and the sender of the {@code Player}'s number choosing command.
     *
     * @param actuator the command's actuator.
     * @see it.polimi.ingsw.model.Player
     */
    public ChooseNumberOfPlayerCommand(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * Class constructor.
     * Initialize the content and the sender of the {@code Player}'s number choosing command.
     *
     * @param actuator        the command's actuator.
     * @param numberOfPlayers the {@code Player}s number to start the game.
     * @see it.polimi.ingsw.model.Player
     */
    public ChooseNumberOfPlayerCommand(Server actuator, int numberOfPlayers) {
        this.actuator = actuator;
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the {@code Player}'s number choosing command.
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the {@code Player}'s number choosing command.
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the {@code Player}'s number choosing command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException      if a connection error occurs
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.chooseNumberOfPlayerInTheGame(this.numberOfPlayers);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"chooseNumberOfPlayerInTheGame(int)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the {@code Player}'s number choosing command.
     * @see CommandType
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public CommandType toEnum() {
        return CommandType.CHOOSE_NUMBER_OF_PLAYER;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Server, CommandType:" + this.toEnum() + ", Parameters:{NumberOfPlayers: " + this.numberOfPlayers + "}]";
    }
}
