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
 * This class represents the {@code Client}'s skeleton, it is necessary for the correct functioning of the {@code Server}
 * It contains for pinging the client, updating the model view, receiving exceptions, ...
 * and also displaying modifies to the current client's implementation.
 * It implements the client-server communication using sockets.
 *
 * @see Client
 * @see javax.swing.text.View
 * @see Server
 * @see it.polimi.ingsw.network.ClientImpl
 */
//Necessary for the server in order to function
public class ClientSkeleton implements Client {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    /**
     * The class constructor.
     * Initialize the socket used by the server to communicate with the client.
     *
     * @param socket is the server's socket.
     * @throws RemoteException to signal the occurrence of a resource error on input or output streams.
     * @see Client
     */
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

    /**
     * This method permits to convey the updated model view.
     *
     * @param modelUpdated contains the model updates.
     * @throws RemoteException is called when a communication error occurs and the modelView can't be sent.
     * @see javax.swing.text.View
     */
    @Override
    public synchronized void updateModelView(GameView modelUpdated) throws RemoteException {
        CommandToClient command = new SendUpdatedModelCommand(modelUpdated);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to client.", e);
        }
    }

    /**
     * Allows to ping the client.
     *
     * @throws RemoteException signals the occurrence of a communication error with the client.
     * @see Client
     */
    @Override
    public synchronized void ping() throws RemoteException {
        CommandToClient command = new SendPingToClientCommand();
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to client.", e);
        }
    }

    /**
     * Used by the server to communicate a generic exception.
     *
     * @param exception the GENERIC except to be sent.
     * @throws RemoteException called when a communication error with the client occurs.
     * @see Client
     */
    @Override
    public synchronized void receiveException(GenericException exception) throws RemoteException {
        CommandToClient command = new SendExceptionCommand(exception);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " to client.", e);
        }
    }

    /**
     * Setter used to provide knowledge on the stored game's for the player reconnecting to the current's game server.
     *
     * @param result {@code true} if and only if the game has been stored properly, {@code false} otherwise.
     * @see it.polimi.ingsw.model.Game
     */
    @Override
    public synchronized void setAreThereStoredGamesForPlayer(boolean result) throws RemoteException {
        CommandToClient command = new SendAreThereStoredGamesForPlayerCommand(result);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to client: " + e.getMessage());
        }
    }

    /**
     * Receives (and provides a response) to client's messages.
     *
     * @param server is the server communicating to.
     * @throws RemoteException called when the server's message can't be cast or received.
     * @see Server
     */
    public void receive(Server server) throws RemoteException {
        CommandToServer message;
        try {
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
