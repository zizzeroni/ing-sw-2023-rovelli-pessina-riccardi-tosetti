package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.socketMiddleware.CommandType;

import java.rmi.RemoteException;

public class SendUpdatedModelCommand implements CommandToClient {
    private Client actuator;
    private final GameView updatedModel;

    public SendUpdatedModelCommand(Client actuator, GameView updatedModel) {
        this.actuator = actuator;
        this.updatedModel = updatedModel;
    }

    public SendUpdatedModelCommand(GameView updatedModel) {
        this.updatedModel = updatedModel;
    }

    @Override
    public Client getActuator() {
        return this.actuator;
    }

    @Override
    public void setActuator(Client actuator) {
        this.actuator = actuator;
    }

    public void execute() throws NullPointerException, RemoteException {
        if (this.actuator != null) {
            this.actuator.updateModelView(this.updatedModel);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"updateModelView(GameView)\" command because this.actuator is NULL");
        }
    }

    @Override
    public CommandType toEnum() {
        return CommandType.SEND_UPDATED_MODEL;
    }

    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:updateModelView, Parameters:{modelUpdated: " + this.updatedModel + "}]";
    }
}
