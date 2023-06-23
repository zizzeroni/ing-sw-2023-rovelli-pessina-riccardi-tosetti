package it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;
/**
 * This class represents the command that enables the {@code Game}'s startup.
 * It is developed as an implementation of the {@code CommandToServer} interface.
 *
 *
 * @see CommandToServer
 * @see it.polimi.ingsw.model.Player
 */
public class StartGameCommand implements CommandToServer {
    private Server actuator;

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the {@code Game}'s start command.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    @Override
    public Server getActuator() {
        return this.actuator;
    }

    /**
     * Sets the command's actuator.
     *
     * @param actuator the actuator of the {@code Game}'s start command.
     *
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void setActuator(Server actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the {@code Game}'s start command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException
     *
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.startGame();
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"startGame()\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the {@code Game}'s start command.
     *
     * @see CommandType
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public CommandType toEnum() {
        return CommandType.START_GAME;
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
        return "[CommandReceiver:GameController, CommandType:StartGame, Parameters:NONE]";
    }
}
