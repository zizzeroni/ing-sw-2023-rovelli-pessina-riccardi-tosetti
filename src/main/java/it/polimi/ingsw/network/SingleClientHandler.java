package it.polimi.ingsw.network;

import it.polimi.ingsw.network.socketMiddleware.ClientSkeleton;

import java.io.IOException;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * This class depicts the handler used for the management of a specific client.
 *
 * @see Client
 * @see ClientImpl
 */
public class SingleClientHandler extends Thread {
    Socket socket;
    Server generalServer;
    ClientSkeleton clientSkeleton;

    /**
     * Links the client to the server through a socket communication port.
     *
     * @param server is the server communicating to.
     * @param socket is the communication port used to forward the machine messages.
     * @throws RemoteException called if a communication error occurs.
     */
    public SingleClientHandler(Server server, Socket socket) throws RemoteException {
        this.socket = socket;
        this.generalServer = server;
        this.clientSkeleton = new ClientSkeleton(socket);
    }

    /**
     * Consents to run the Client's implementation.
     * It basically waits to receive the nickname from the player's client and then
     * registers the client associated with the nickname received.
     *
     * @see Client
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public void run() {
        try {
            //Waiting to receive the nickname from the client
            //String nickname = this.clientSkeleton.receiveNickname(this.generalServer);
            //Register the client associated with the nickname received
            this.generalServer.register(this.clientSkeleton, null);
            while (true) {
                this.clientSkeleton.receive(this.generalServer);
            }
        } catch (RemoteException e) {
            System.err.println("[COMMUNICATION:ERROR] Cannot receive from client. Closing this connection...;\n     " + "Caused by: " + e.getMessage());
            try {
                socket.close();
            } catch (IOException ex) {
                System.err.println("[RESOURCE:ERROR] Cannot close connection with server. Halting...");
            }
        }
    }
}
