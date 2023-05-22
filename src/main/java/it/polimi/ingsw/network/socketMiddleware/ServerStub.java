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

//Necessary for the client in order to function
public class ServerStub implements Server {
    //Server's IP address
    private final String ip;
    //Server's port address
    private final int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    //Lock used in order to synchronize the sending of a notification of an input or event coming from the View and sent to the Server, and the reception of
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
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
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

    @Override
    public void ping() throws RemoteException {
        CommandToServer command = new SendPingToServerCommand();
        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server: " + e.getMessage());
        }
    }

    @Override
    public void disconnectPlayer(String nickname) throws RemoteException {
        CommandToServer command = new DisconnectPlayerCommand(nickname);
        try {
            this.oos.writeObject(command);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + command + " ,to server: " + e.getMessage());
        }
    }


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

    public void close() throws RemoteException {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot close socket", e);
        }
    }
}
