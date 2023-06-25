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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class represents the server's application.
 * It contains a series of methods to create a server's implementation used to execute
 * the threads employed in the RMI and socket connection and to start up the RMI and socket services.
 *
 * @see Server
 * @see javax.management.remote.rmi.RMIConnection
 * @see javax.management.remote.rmi.RMIServer
 * @see Socket
 */
public class AppServer {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Creates an implementation of the Server.
     * Starts the Thread that will take care of initializing RMI connection.
     * Starts the Thread that will take care of initializing Socket connection.
     *
     * @param args the main's arguments.
     * @throws RemoteException
     *
     * @see Server
     * @see javax.management.remote.rmi.RMIConnection
     * @see Socket
     */
    public static void main(String[] args) throws RemoteException {
        //Creating an implementation of a Server
        Server server = new ServerImpl();


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

    /**
     * Creates and initialize the RMI service.
     *
     * @param server the server involved in the RMI connection.
     * @throws RemoteException called if a communication error with the server occurs.
     */
    private static void startRMI(Server server) throws RemoteException {
        //RMI service creation


        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("server", server);
    }

    /**
     * Creates and initialize the socket service.
     * Waits for a connection request from a client and pass the client handling
     * to another Thread, letting it taking care of new Client's connection requests.
     *
     * @param server the server involved in the socket service creation.
     * @throws RemoteException called if a communication error with the server occurs.
     *
     * @see it.polimi.ingsw.network.Client
     * @see Server
     * @see Thread
     * @see Socket
     */
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


}
