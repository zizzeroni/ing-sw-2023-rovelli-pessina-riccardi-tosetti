package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

//Necessary for the client in order to function
public class ServerStab implements Server {
    //Server's IP address
    private final String ip;
    //Server's port address
    private final int port;
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public ServerStab(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    @Override
    public void changeTurn() throws RemoteException {
        MsgSocket<Void> message = new MsgSocket<>(ip, Action.CHANGE_TURN, null);
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("Error while sending message: "+ message + " ,to server: "+e.getMessage());
        }
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) throws RemoteException {
        MsgSocket<Choice> message = new MsgSocket<>(ip, Action.USER_INSERTION, Arrays.asList(playerChoice));
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("Error while sending message: "+ message + " ,to server: "+e.getMessage());
        }
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) throws RemoteException {
        MsgSocket<String> message = new MsgSocket<>(ip, Action.SEND_PRIVATE_MESSAGE, Arrays.asList(receiver,sender,content));
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("Error while sending message: "+ message + " ,to server: "+e.getMessage());
        }
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) throws RemoteException {
        MsgSocket<String> message = new MsgSocket<>(ip, Action.SEND_BROADCAST_MESSAGE, Arrays.asList(sender,content));
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("Error while sending message: "+ message + " ,to server: "+e.getMessage());
        }
    }

    @Override
    public void addPlayer(String nickname) throws RemoteException {
        MsgSocket<String> message = new MsgSocket<>(ip, Action.ADD_PLAYER, Arrays.asList(nickname));
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("Error while sending message: "+ message + " ,to server: "+e.getMessage());
        }
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) throws RemoteException {
        MsgSocket<Integer> message = new MsgSocket<>(ip, Action.CHOOSE_NUMBER_OF_PLAYERS, Arrays.asList(chosenNumberOfPlayers));
        try {
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RemoteException("Error while sending message: "+ message + " ,to server: "+e.getMessage());
        }
    }

    @Override
    public void register(Client client) throws RemoteException {
        try {
            this.socket = new Socket(ip,port);
            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create output stream: "+e.getMessage());
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream: "+e.getMessage());
            }
            MsgSocket<Client> message = new MsgSocket<>(ip, Action.CLIENT_REGISTRATION, Arrays.asList(client));
            //this.oos.writeObject(message);

        } catch (IOException e) {
            throw new RemoteException("Error while connection to server: "+e.getMessage());
        }
    }


    public void receive(Client client) throws RemoteException {

        GameView gameView;
        try {
            gameView = (GameView) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive modelView: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot cast modelView: " + e.getMessage());
        }
        client.updateModelView(gameView);
    }

    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }
}
