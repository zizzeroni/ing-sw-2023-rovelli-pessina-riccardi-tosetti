package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.network.socketMiddleware.ClientSkeleton;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SingleClientHandler extends Thread {

    Server generalServer;
    ClientSkeleton clientSkeleton;

    public SingleClientHandler(Server server, Socket socket) throws RemoteException {
        this.generalServer = server;
        clientSkeleton = new ClientSkeleton(socket);

    }

    @Override
    public void run() {
        try {
            generalServer.register(clientSkeleton);
            while (true) {
                clientSkeleton.receive(generalServer);
            }
        } catch (RemoteException e) {
            System.err.println("Cannot receive from client. Closing this connection...");
        }
    }
}
