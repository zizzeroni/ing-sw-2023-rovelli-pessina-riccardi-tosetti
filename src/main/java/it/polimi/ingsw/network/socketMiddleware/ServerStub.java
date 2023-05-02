package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.commandPattern.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

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
    private final Object lockUpdate = new Object();

    public ServerStub(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void changeTurn() throws RemoteException {
        Command message = new ChangeTurnCommand();
        //MsgSocket<Void> message = new MsgSocket<>(this.ip, Action.CHANGE_TURN, null);
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }
        synchronized (this.lockUpdate) {
            try {
                this.lockUpdate.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        Command message = new InsertUserInputCommand(playerChoice);
        //MsgSocket<Choice> message = new MsgSocket<>(this.ip, Action.USER_INSERTION, Arrays.asList(playerChoice));
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        synchronized (this.lockUpdate) {
            try {
                this.lockUpdate.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        //Necessary for how we implemented the adding of the tiles to the player's bookshelf
        //We add one tile at a time, this brings the Model (Bookshelf) to notify the Server a number of times equals to the number of tile chosen by the User
        for (int i = 0; i < playerChoice.getChosenTiles().size(); i++) {
            synchronized (this.lockUpdate) {
                try {
                    this.lockUpdate.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        Command message = new SendPrivateMessageCommand(receiver,sender,content);
        //MsgSocket<String> message = new MsgSocket<>(this.ip, Action.SEND_PRIVATE_MESSAGE, Arrays.asList(receiver, sender, content));
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        synchronized (this.lockUpdate) {
            try {
                this.lockUpdate.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) throws RemoteException {
        Command message = new SendBroadcastMessageCommand(sender,content);
        //MsgSocket<String> message = new MsgSocket<>(this.ip, Action.SEND_BROADCAST_MESSAGE, Arrays.asList(sender, content));
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        synchronized (this.lockUpdate) {
            try {
                this.lockUpdate.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addPlayer(String nickname) throws RemoteException {
        Command message = new AddPlayerCommand(nickname);
        //MsgSocket<String> message = new MsgSocket<>(this.ip, Action.ADD_PLAYER, Arrays.asList(nickname));
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        synchronized (this.lockUpdate) {
            try {
                this.lockUpdate.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        Command message = new ChooseNumberOfPlayerCommand(chosenNumberOfPlayers);
        //MsgSocket<Integer> message = new MsgSocket<>(this.ip, Action.CHOOSE_NUMBER_OF_PLAYERS, Arrays.asList(chosenNumberOfPlayers));
        try {
            this.oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Error while sending message: " + message + " ,to server: " + e.getMessage());
        }

        synchronized (this.lockUpdate) {
            try {
                this.lockUpdate.wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void register(Client client) throws RemoteException {
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


    public void receive(Client client) throws RemoteException {
        GameView gameView;
        try {
            //System.out.println("Ready to receive (from Server)");
            gameView = (GameView) this.ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot receive modelView: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot cast modelView: " + e.getMessage());
        }
        client.updateModelView(gameView);

        synchronized (this.lockUpdate) {
            this.lockUpdate.notifyAll();
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