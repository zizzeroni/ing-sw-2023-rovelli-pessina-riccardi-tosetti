package it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;

import java.rmi.RemoteException;

public class SendUpdatedModelCommandToServer implements CommandToClient {
    private Client actuator;
    private GameView updatedModel;

    public SendUpdatedModelCommandToServer(Client actuator, GameView updatedModel) {
        this.actuator = actuator;
        this.updatedModel = updatedModel;
    }

    public SendUpdatedModelCommandToServer(GameView updatedModel) {
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
            this.actuator.updateModelView(updatedModel);
        } else {
            throw new NullPointerException("[RESOURCE:ERROR] Can't invoke \"updateModelView(GameView)\" command because this.actuator is NULL");
        }
    }

    @Override
    public String toString() {
        return "[CommandReceiver:UI, CommandType:updateModelView, Parameters:{modelUpdated: " + updatedModel + "}]";
    }
}
