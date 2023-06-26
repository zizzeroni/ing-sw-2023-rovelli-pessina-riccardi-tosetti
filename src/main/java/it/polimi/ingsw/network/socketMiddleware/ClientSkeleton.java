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
     * Initialize the client's socket.
     *
     * @param socket is the client's socket.
     * @throws RemoteException to signal the occurrence of a resource error on input or output streams.
     *
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
     *
     * @see javax.swing.text.View
     */
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

    /**
     * Allows to ping the client.
     *
     * @throws RemoteException signals the occurrence of a communication error with the client.
     *
     * @see Client
     */
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

    /**
     * Used to receive a generic exception in order to handle it.
     *
     * @param exception the GENERIC except being received.
     * @throws RemoteException called when a communication error with the client occurs.
     *
     * @see Client
     */
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

<<<<<<< HEAD
    /*@Override
    public void receiveException(RemoteException exception) throws RemoteException {
        CommandToClient command = new SendExceptionCommand(exception);
        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot send modelView: " + e.getMessage());
        }
    }*/

    /**
     * Receives (and provides a response) to server's messages.
     *
     * @param server is the server communicating to.
     * @throws RemoteException called when the server's message can't be cast or received.
     *
     * @see Server
     */
=======
>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd
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
<<<<<<< HEAD
        if (message.toEnum() == CommandType.ADD_PLAYER) {
            AddPlayerCommand convertedMessage = (AddPlayerCommand) message;
            convertedMessage.setClient(this);
            convertedMessage.execute();
        } else {
            message.execute();
        }
    }

    /**
     * Receives (and provides a response) to server's messages related to player's nickname.
     *
     * @param server is the server communicating to.
     * @throws RemoteException called when the server's message can't be cast or received.
     *
     * @see Server
     */
    public String receiveNickname(Server server) throws RemoteException {
=======
>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd
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
