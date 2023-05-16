package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImpl;
import it.polimi.ingsw.network.SingleClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppServer {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws RemoteException {
        //Creating an implementation of a Server
        Server server = new ServerImpl();

        startPingSenderThread(server);

        //Starting Thread that will take care of initializing RMI connection
        Thread rmiThread = new Thread() {
            @Override
            public void run() {
                try {
                    startRMI(server);
                } catch (RemoteException e) {
                    System.err.println("[RESOURCE:ERROR] Cannot start RMI. This protocol will be disabled.");
                }
            }
        };

        rmiThread.start();

        //Starting Thread that will take care of initializing Socket connection
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
            System.err.println("[CONNECTION:ERROR] No connection protocol available. Exiting...");
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
                //Waiting for a connection request from a client
                Socket socket = serverSocket.accept();

                //Let handle the client to another Thread, letting this thread to take care of new Client's connection requests
                executorService.submit(new SingleClientHandler(server, socket));
            }
        } catch (IOException e) {
            throw new RemoteException("[RESOURCE:ERROR] Cannot start socket server", e);
        }
    }

    private static void startPingSenderThread(Server server) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    server.pingClients();
                } catch (RemoteException e) {
                    System.err.println("prova");
                }
            }
        };

        Timer pingSender = new Timer("PingSender");
        pingSender.scheduleAtFixedRate(timerTask, 30, 3000);
    }
}
