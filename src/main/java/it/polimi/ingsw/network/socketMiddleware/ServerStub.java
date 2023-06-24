package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.controller.CreationState;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.OnGoingState;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.commandPatternClientToServer.*;
import it.polimi.ingsw.network.socketMiddleware.commandPatternServerToClient.CommandToClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.Semaphore;

/**
 * This class identifies the functioning of the server's stub, it is necessary fo the {@code Client}'s correct functioning.
 * It incorporates the same methods of the server interface, implementing them.
 * It also contains semaphores used in order to synchronize the sending of a notification of an input or event coming from the View and sent to the Server, and the reception of
 * a "response" (A new GameView object) form the Server itself
 *
 * @see Server
 * @see it.polimi.ingsw.model.view.GameView
 * @see Client
 */
//Necessary for the client in order to function
public class ServerStub implements Server {
    //Server's IP address
    private final String ip;
    //Server's port address
    private final int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    //Sempahore used in order to synchronize the sending of a notification of an input or event coming from the View and sent to the Server, and the reception of
    //a "response" (A new GameView object) form the Server itself
    private final Semaphore semaphoreUpdate = new Semaphore(0);

    /**
     * Class constructor.
     * initialize the server's ip and port to the given values.
     *
     * @param ip the address of the server.
     * @param port the server's port number.
     */
    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    /**
     * Change the turn in the server's context.
     *
     * @see OnGoingState#changeTurn()
     */
    @Override
    public void changeTurn() throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new ChangeTurnCommand();
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Provides the server communication that allow the {@code Player} to insert {@code Tile}s,
     * in a given order (contained in {@code Choice}) into the {@code Board}.
     *
     *
     * @param playerChoice the choice made by the player.
     * @throws RemoteException
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     * @see Choice
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new InsertUserInputCommand(playerChoice);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Necessary for how we implemented the adding of the tiles to the player's bookshelf
        //We add one tile at a time, this brings the Model (Bookshelf) to notify the Server a number of times equals to the number of tile chosen by the User
        for (int i = 0; i < playerChoice.getChosenTiles().size(); i++) {
            try {
                this.semaphoreUpdate.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //Necessary because at the end of the game I receive the notification that the game passed from ON_GOING state to the FINISHING state
        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message
     * in any chat. It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender the {@code Player} sending the message.
     * @param content the text of the message being sent.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new SendPrivateMessageCommand(receiver, sender, content);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }


        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * This method implementations allow to send
     * broadcast messages to all the {@code Player}s.
     *
     * @param sender the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Message
     */
    @Override
    public void sendBroadcastMessage(String sender, String content) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new SendBroadcastMessageCommand(sender, content);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * This method is used to add a {@code Player} to the current {@code Game}
     * through the knowledge of the nickname he has chosen during game creation and the client
     * he has been assigned to.
     *
     *
     * @param client is the player's client
     * @param nickname is the reference for the name of the {@code Player} being added.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Client
     * @see Game
     * @see Player
     */
    @Override
    public void addPlayer(Client client, String nickname) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new AddPlayerCommand(nickname);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to add a {@code Player} to the current {@code Game}
     * through the knowledge of the nickname he has chosen during game creation and the client
     * he has been assigned to.
     *
     * @param nickname is the reference for the name of the {@code Player} being added.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Client
     * @see Game
     * @see Player
     */
    public void addPlayer(String nickname) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new AddPlayerCommand(nickname);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Permits to set the number of active {@code Player}s in the current {@code Game}.
     *
     * @param chosenNumberOfPlayers the number of players joining the {@code Game}.
     * @throws RemoteException signals the occurrence of an error while sending the message.
     *
     * @see it.polimi.ingsw.model.Game
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#chooseNumberOfPlayerInTheGame(int)
     */
    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new ChooseNumberOfPlayerCommand(chosenNumberOfPlayers);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Updates (or acquires) the permissions on the semaphores to start up the Game.
     *
     * @throws RemoteException called when occurs a communication error with the server.
     *
     * @see Game
     */
    @Override
    public void startGame() throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new StartGameCommand();
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Valore permit semaphore: " + this.semaphoreUpdate.availablePermits());
    }

    /**
     * Allows client's registration, every {@code Client} is associated to its {@code Player}'s nickname.
     *
     * @param client is the client being registered.
     * @param nickname is the client's player's nickname.
     * @throws RemoteException an exception called to notify that an error occurred while connecting to the server.
     *
     * @see Client
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void register(Client client, String nickname) throws RemoteException {
        try {
            this.socket = new Socket(this.ip, this.port);
            try {
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("[RESOURCE:ERROR] Cannot create output stream: " + e.getMessage());
            }
            try {
                this.ois = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("[RESOURCE:ERROR] Cannot create input stream: " + e.getMessage());
            }
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while connection to server: " + e.getMessage());
        }
    }

    /**
     * Allows to ping the server.
     *
     * @throws RemoteException called if a communication error occurs.
     *
     * @throws RemoteException signals the occurrence of a communication error with the server.
     *
     * @see Server
     */
    @Override
    public void ping() throws RemoteException {
        CommandToServer command = new SendPingToServerCommand();
        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server: " + e.getMessage());
        }
    }

    /** Signals the disconnection of the selected {@code Player} from the current game to the server
     * by changing his connectivity state.
     * (only possible when the {@code Game} has already started).
     *
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @throws RemoteException called if a communication error occurs.
     *
     * @see Player
     * @see Server
     * @see Game
     * @see Player#setConnected(boolean)
     */
    @Override
    public void disconnectPlayer(String nickname) throws RemoteException {
        CommandToServer command = new DisconnectPlayerCommand(nickname);
        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server: " + e.getMessage());
        }
    }


    /**
     * Receives (and provides a response) to client's messages.
     *
     * @param client is the client communicating to.
     * @throws RemoteException called when the client's message can't be cast or received.
     *
     * @see Client
     */
    public void receive(Client client) throws RemoteException {
        CommandToClient command;
        try {
            //System.out.println("Ready to receive (from Server)");
            command = (CommandToClient) this.ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot receive modelView: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot cast modelView: " + e.getMessage());
        }
        command.setActuator(client);
        command.execute();

        if (command.toEnum() != CommandType.SEND_PING_TO_CLIENT) {
            this.semaphoreUpdate.release();
        }
    }

    /**
     * Used to close the server's socket.
     *
     * @throws RemoteException is launched if it is not possible to close the socket connection.
     */
    public void close() throws RemoteException {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot close socket", e);
        }
    }
}
