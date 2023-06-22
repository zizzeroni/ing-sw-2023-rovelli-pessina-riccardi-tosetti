package it.polimi.ingsw;

import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImpl;
import it.polimi.ingsw.network.SingleClientHandler;

import java.io.IOException;
import java.net.*;
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

public class AppServer {
    private static final ExecutorService executorService = Executors.newCachedThreadPool();

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
