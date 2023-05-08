package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.ServerStub;
import it.polimi.ingsw.view.TextualUI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class AppClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        //Initialize client necessities
        ClientImpl client;
        Scanner s = new Scanner(System.in);
        System.out.println("Client avviato...");
        int uiChoice, connectionChoice;
        //------------------------------------TYPE CONNECTION & TYPE UI CHOICES------------------------------------
        do {
            System.out.println("Che interfaccia grafica preferisci utilizzare?");
            System.out.println("1)Testuale");
            System.out.println("2)Grafica");
            uiChoice = s.nextInt();
        } while (uiChoice < 1 || uiChoice > 2);
        do {
            System.out.println("Che metodo di comunicazione preferisci utilizzare?");
            System.out.println("1)RMI");
            System.out.println("2)Socket");
            connectionChoice = s.nextInt();
        } while (connectionChoice < 1 || connectionChoice > 2);

        switch (uiChoice) {
            case 1 -> {
                switch (connectionChoice) {
                    case 1 -> {
                        //Getting the remote server by RMI
                        Registry registry = LocateRegistry.getRegistry();
                        Server server = (Server) registry.lookup("server");

                        //Creating a new client with a TextualUI and a RMI Server
                        client = new ClientImpl(server, new TextualUI()/*,nick*/);
                    }
                    case 2 -> {
                        //Creating an Object that will allow the client to communicate with the Server (In the RMI case, this was created by RMI itself)
                        ServerStub serverStub = new ServerStub("localhost", 1234);

                        //Creating a new client with a TextualUI and a Socket Server
                        client = new ClientImpl(serverStub, new TextualUI());
                        //Creating a new Thread that will take care of the responses coming from the Server side
                        new Thread(() -> {
                            while (true) {
                                try {
                                    serverStub.receive(client);
                                } catch (RemoteException e) {
                                    System.err.println("[COMMUNICATION:ERROR] Error while receiving message from server (Server was closed)");
                                    try {
                                        serverStub.close();
                                    } catch (RemoteException ex) {
                                        System.err.println("[RESOURCE:ERROR] Cannot close connection with server. Halting...");
                                    }
                                    System.exit(1);
                                }
                            }
                        }).start();
                    }
                    default -> {
                        System.err.println("[INPUT:ERROR] Unexpected value for the type of connection choice");
                        return;
                    }
                }
            }
            case 2 -> {
                switch (connectionChoice) {
                    case 1 -> {
                        //Getting the remote server by RMI

                        /*Registry registry = LocateRegistry.getRegistry();
                        Server server = (Server) registry.lookup("server");*/

                        //Creating a new client with a GUI
                        System.err.println("To be implemented");
                        return;
                    }
                    case 2 -> {
                        //Getting the remote server by Socket
                        System.err.println("To be implemented");
                        return;
                    }
                    default -> {
                        System.err.println("[INPUT:ERROR] Unexpected value for the type of connection choice");
                        return;
                    }
                }
            }
            default -> {
                System.err.println("[INPUT:ERROR] Unexpected value for the type of view choice");
                return;
            }
        }
        //Calling the run method of the UI
        client.run();
        //Closing client app
        System.exit(0);
    }

}
