package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.model.Choice;
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
 *
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

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void changeTurn() throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new ChangeTurnCommand();
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new InsertUserInputCommand(playerChoice);
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new SendPrivateMessageCommand(receiver, sender, content);
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void sendBroadcastMessage(String sender, String content) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new SendBroadcastMessageCommand(sender, content);
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e)
                    ;
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addPlayer(Client client, String nickname) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new AddPlayerCommand(nickname);
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void tryToResumeGame() throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new TryToResumeGameCommand();
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new ChooseNumberOfPlayerCommand(chosenNumberOfPlayers);
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void startGame() throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer message = new StartGameCommand();
        try {
            this.oos.writeObject(message);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server.", e);
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(Client client, String nickname) throws RemoteException {
        try {
            this.socket = new Socket(this.ip, this.port);
            try {
                this.oos = new ObjectOutputStream(this.socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("[RESOURCE:ERROR] Cannot create output stream.", e);
            }
            try {
                this.ois = new ObjectInputStream(this.socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("[RESOURCE:ERROR] Cannot create input stream.", e);
            }
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while creating socket connection to server.", e);
        }
    }

    @Override
    public void ping() throws RemoteException {
        CommandToServer command = new SendPingToServerCommand();
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server.", e);
        }
    }

    @Override
    public void disconnectPlayer(String nickname) throws RemoteException {
        CommandToServer command = new DisconnectPlayerCommand(nickname);
        try {
            this.oos.writeObject(command);
            this.oos.reset();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server.", e);
        }
    }

    @Override
    public void restoreGameForPlayer(String nickname) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer command = new RestoreStoredGameCommand(nickname);

        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void areThereStoredGamesForPlayer(String nickname) throws RemoteException {
        this.semaphoreUpdate.drainPermits();
        CommandToServer command = new AreThereStoredGamesForPlayerCommand(nickname);

        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server: " + e.getMessage());
        }

        try {
            this.semaphoreUpdate.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void receive(Client client) throws RemoteException {
        CommandToClient command;
        try {
            //System.out.println("Ready to receive (from Server)");
            command = (CommandToClient) this.ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot receive message from server.", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot cast message received by the server.", e);
        }
        command.setActuator(client);
        try {
            command.execute();
        } catch (NullPointerException e) {
            throw new RemoteException("Error while executing command.", e);
        }

        if (command.toEnum() != CommandType.SEND_PING_TO_CLIENT) {
            this.semaphoreUpdate.release();
        }
    }

    public void close() throws RemoteException {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot close socket", e);
        }
    }
}
