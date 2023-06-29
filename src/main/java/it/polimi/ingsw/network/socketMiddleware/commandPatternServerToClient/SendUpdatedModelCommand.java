package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

/**
 * This class represents the command that allows to transmit the updated model to the {@code Players}' {@code Client}s.
 * It has been developed as in implementation of  the {@code CommandToClient} interface.
 *
 * @see Client
 * @see it.polimi.ingsw.network.ClientImpl
 * @see it.polimi.ingsw.model.Player
 * @see it.polimi.ingsw.model.Game
 */
public class SendUpdatedModelCommand implements CommandToClient {
    private Client actuator;
    private final GameView updatedModel;

    /**
     * Class constructor.
     * Initialize the command's actuator altogether with the updated model.
     *
     * @param actuator     the command's actuator.
     * @param updatedModel the model after being updated that is being forwarded through the model updating command.
     */
    public SendUpdatedModelCommand(Client actuator, GameView updatedModel) {
        this.actuator = actuator;
        this.updatedModel = updatedModel;
    }

    /**
     * Class constructor.
     * Initialize the updated model.
     *
     * @param updatedModel the model after being updated that is being forwarded through the model updating command.
     */
    public SendUpdatedModelCommand(GameView updatedModel) {
        this.updatedModel = updatedModel;
    }

    /**
     * Gets the model updating command's actuator.
     *
     * @return the actuator of the model updating command.
     */
    @Override
    public Client getActuator() {
        return this.actuator;
    }

    /**
     * Sets the model updating command's actuator.
     *
     * @param actuator the actuator of the model updating command.
     */
    @Override
    public void setActuator(Client actuator) {
        this.actuator = actuator;
    }

    /**
     * This method permits the execution of the model updating command.
     *
     * @throws NullPointerException if there is no command to execute.
     * @throws RemoteException if a connection error occurs
     */
    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.updateModelView(this.updatedModel);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"updateModelView(GameView)\" command because this.actuator is NULL");
        }
    }

    /**
     * Used to enumerate the type of the class command.
     *
     * @return the {@code CommandType} of the model updating command.
     * @see CommandType
     */
    @Override
    public CommandType toEnum() {
        return CommandType.SEND_UPDATED_MODEL;
    }

    /**
     * Displays the type of command being executed altogether with the command receiver and command's parameters.
     *
     * @return the string representing the class command.
     * @see it.polimi.ingsw.controller.GameController
     */
    @Override
    public String toString() {
        return "[CommandReceiver:Client, CommandType:" + this.toEnum() + ", Parameters:{updatedModel: " + this.updatedModel + "}]";
    }
}
