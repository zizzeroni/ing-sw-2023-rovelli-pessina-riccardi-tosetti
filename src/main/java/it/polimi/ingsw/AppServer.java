package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImpl;
import it.polimi.ingsw.network.SingleClientHandler;
import it.polimi.ingsw.network.socketMiddleware.ClientSkeleton;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.util.HashMap;
import java.util.Map;

public class AppServer {
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();
        startRMI(server);

        startSocket(server);
    }

    private static void startRMI(Server server) throws RemoteException {
        //RMI service creation


        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("server", server);
    }

    private static void startSocket(Server server) throws RemoteException {
        //Socket service creation
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                Socket socket = serverSocket.accept();
                SingleClientHandler singleClientHandler = new SingleClientHandler(server, socket);
                singleClientHandler.start();
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

}
