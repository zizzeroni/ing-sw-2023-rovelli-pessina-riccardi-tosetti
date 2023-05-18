package it.polimi.ingsw.network;

import it.polimi.ingsw.network.socketMiddleware.ClientSkeleton;

import java.net.Socket;
import java.rmi.RemoteException;

public class SingleClientHandler extends Thread {
    Server generalServer;
    ClientSkeleton clientSkeleton;

    public SingleClientHandler(Server server, Socket socket) throws RemoteException {
        this.generalServer = server;
        this.clientSkeleton = new ClientSkeleton(socket);
    }

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
            System.err.println("[COMMUNICATION:ERROR] Cannot receive from client. Closing this connection...");
        }
    }
}
