package it.polimi.ingsw.network.socketMiddleware;

import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.commandPattern.Command;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

//Necessary for the server in order to function
public class ClientSkeleton implements Client {
    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;

    public ClientSkeleton(Socket socket) throws RemoteException {
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot create output stream: " + e.getMessage());
        }
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot create input stream: " + e.getMessage());
        }
    }

    @Override
    public void updateModelView(GameView modelUpdated) throws RemoteException {
        try {
            this.oos.writeObject(modelUpdated);
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot send modelView: " + e.getMessage());
        }
    }

    public void receive(Server server) throws RemoteException {
        //MsgSocket<Object> message;
        Command message;
        try {
            System.out.println("Ready to receive (from Client)");
            //message = (MsgSocket<Object>) this.ois.readObject();
            message = (Command) this.ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot receive message: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot cast message: " + e.getMessage());
        }
        message.setController(server);
        message.execute();
        /*switch (message.getAction()) {
            case CHANGE_TURN -> {
                server.changeTurn();
            }
            case USER_INSERTION -> {
                server.insertUserInputIntoModel((Choice) message.getParams().get(0));
            }
            case SEND_PRIVATE_MESSAGE -> {
                String receiver = (String) message.getParams().get(0);
                String sender = (String) message.getParams().get(1);
                String content = (String) message.getParams().get(2);
                server.sendPrivateMessage(receiver, sender, content);
            }
            case SEND_BROADCAST_MESSAGE -> {
                String sender = (String) message.getParams().get(0);
                String content = (String) message.getParams().get(1);
                server.sendBroadcastMessage(sender, content);
            }
            case ADD_PLAYER -> {
                String nickname = (String) message.getParams().get(0);
                server.addPlayer(nickname);
            }
            case CHOOSE_NUMBER_OF_PLAYERS -> {
                int chosenNumberOfPlayers = (int) message.getParams().get(0);
                server.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
            }
        }*/
    }
}
