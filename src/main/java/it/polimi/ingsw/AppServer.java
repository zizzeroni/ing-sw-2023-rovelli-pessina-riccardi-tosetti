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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();
    public static void main(String[] args) throws RemoteException {
        Server server = new ServerImpl();

        Thread rmiThread = new Thread() {
            @Override
            public void run() {
                try {
                    startRMI(server);
                } catch (RemoteException e) {
                    System.err.println("Cannot start RMI. This protocol will be disabled.");
                }
            }
        };

        rmiThread.start();
        Thread socketThread = new Thread() {
            public void run() {
                try {
                    startSocket(server);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        socketThread.start();

        try {
            rmiThread.join();
            socketThread.join();
        } catch (InterruptedException e) {
            System.err.println("No connection protocol available. Exiting...");
        }
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
                executorService.submit(new SingleClientHandler(server,socket));
                //SingleClientHandler singleClientHandler = new SingleClientHandler(server, socket);
                //singleClientHandler.start();
            }
        } catch (IOException e) {
            throw new RemoteException("Cannot start socket server", e);
        }
    }

}
