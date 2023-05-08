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
        Command message;
        try {
            System.out.println("Ready to receive (from Client)");
            message = (Command) this.ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot receive message: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RemoteException("[COMMUNICATION:ERROR] Cannot cast message: " + e.getMessage());
        }
        message.setController(server);
        message.execute();
    }
}
