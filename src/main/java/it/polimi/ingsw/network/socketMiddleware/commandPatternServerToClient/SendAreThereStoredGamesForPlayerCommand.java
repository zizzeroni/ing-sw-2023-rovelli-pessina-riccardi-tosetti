package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class is used to provide methods to verify if there are stored games for the given player.
 * It is developed as an implementation of the commandToClient interface.
 *
 * @see it.polimi.ingsw.model.Player
 * @see it.polimi.ingsw.model.Game
 */
public class SendAreThereStoredGamesForPlayerCommand implements CommandToClient {
    private Client actuator;
    private final boolean result;

    public SendAreThereStoredGamesForPlayerCommand(Client actuator, boolean result) {
        this.actuator = actuator;
        this.result = result;
    }

    /**
     * Class constructor.
     * Initialize the class's attributes values, setting the result parameter.
     *
     * @param result {@code true} iff there are stored games for the given player, {@code false} otherwise.
     */
    public SendAreThereStoredGamesForPlayerCommand(boolean result) {
        this.result = result;
    }

    /**
     * Gets the command's actuator.
     *
     * @return the actuator of the {@code Game}'s  command.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     */
    @Override
    public Client getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Client actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the class command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException      called when a communication error occurs.
     * @see it.polimi.ingsw.model.Player
     */
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.setAreThereStoredGamesForPlayer(this.result);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"updateModelView(boolean)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the {@code Game}'s command.
     * @see CommandType
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_UPDATED_MODEL;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver ({@code GameController}) and command parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:updateModelView, Parameters:{modelUpdated: " + this.result + "}]";
    }
}
