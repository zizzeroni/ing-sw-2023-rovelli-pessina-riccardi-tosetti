package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer.AddPlayerCommand;
import it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer.CommandToServer;
import it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 *
 */
//Necessary for the server in order to function
public class ClientSkeleton implements Client {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public ClientSkeleton(Socket socket) throws RemoteException {
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot create output stream.", e);
        }
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot create input stream.", e);
        }
    }

    @Override
    public void updateModelView(GameView modelUpdated) throws RemoteException {
        CommandToClient command = new SendUpdatedModelCommand(modelUpdated);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to client.", e);
        }
    }

    @Override
    public void ping() throws RemoteException {
        CommandToClient command = new SendPingToClientCommand();
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to client.", e);
        }
    }

    @Override
    public void receiveException(GenericException exception) throws RemoteException {
        CommandToClient command = new SendExceptionCommand(exception);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " to client.", e);
        }
    }

    @Override
    public void setAreThereStoredGamesForPlayer(boolean result) throws RemoteException {
        CommandToClient command = new SendAreThereStoredGamesForPlayerCommand(result);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to client: " + e.getMessage());
        }
    }

    public void receive(Server server) throws RemoteException {
        CommandToServer message;
        try {
            System.out.println("Ready to receive (from Client)");
            message = (CommandToServer) this.ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot receive message from client.", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot cast message received by the client.", e);
        }
        message.setActuator(server);
        try {
            if (message.toEnum() == CommandType.ADD_PLAYER) {
                AddPlayerCommand convertedMessage = (AddPlayerCommand) message;
                convertedMessage.setClient(this);
                convertedMessage.execute();
            } else {
                message.execute();
            }
        } catch (NullPointerException e) {
            throw new RemoteException("Error while executing command.", e);
        }
    }
}
