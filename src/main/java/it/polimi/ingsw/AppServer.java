package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImpl;
import it.polimi.ingsw.network.SingleClientHandler;
import it.polimi.ingsw.utils.OptionsValues;

import java.io.IOException;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @see Server
     * @see javax.management.remote.rmi.RMIConnection
     * @see Socket
     */
    public static void main(String[] args) throws RemoteException {
        //Setting the ipAddress of this server to the one chosen by the admin
        Scanner input = new Scanner(System.in);
        String ipAddress = args.length > 0 ? args[0] : "";
        String regex = "(localhost|\\b(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)(?::\\d{0,4})?\\b)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        while (ipAddress.equals("")) {
            System.out.println("Insert a valid IpAddress for the server:");
            ipAddress = input.next();
            matcher = pattern.matcher(ipAddress);
            ipAddress = matcher.matches() ? ipAddress : "";
        }
        System.setProperty("java.rmi.server.hostname", ipAddress.equals("localhost") ? getFirstUpNetworkInterface() : ipAddress);

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


        Registry registry = LocateRegistry.createRegistry(OptionsValues.RMI_PORT);
        registry.rebind(OptionsValues.SERVER_RMI_NAME, server);
    }

    /**
     * Creates and initialize the socket service.
     * Waits for a connection request from a client and pass the client handling
     * to another Thread, letting it taking care of new Client's connection requests.
     *
     * @param server the server involved in the socket service creation.
     * @throws RemoteException called if a communication error with the server occurs.
     * @see it.polimi.ingsw.network.Client
     * @see Server
     * @see Thread
     * @see Socket
     */
    private static void startSocket(Server server) throws RemoteException {
        //Socket service creation
        try (ServerSocket serverSocket = new ServerSocket(OptionsValues.SOCKET_PORT)) {
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

    private static String getFirstUpNetworkInterface() throws RemoteException {
        //TODO: Da verificarne funzionamento
        Random rand = new Random();
        List<NetworkInterface> networkInterfacesList;
        try {
            networkInterfacesList = NetworkInterface.networkInterfaces().filter(networkInterface -> {
                try {
                    return networkInterface.isUp() && !networkInterface.isLoopback();
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
        } catch (SocketException e) {
            throw new RemoteException("Error while retrieving network interfaces, closing client...");
        }
        return networkInterfacesList.get(rand.nextInt(networkInterfacesList.size())).getInetAddresses().nextElement().getHostAddress();
    }
}
