package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.ServerStub;
import it.polimi.ingsw.utils.CommandReader;
import it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.view.TextualUI;
import javafx.application.Application;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class AppClient {
    static CommandReader commandReader = new CommandReader();

    public static void main(String[] args) throws RemoteException, NotBoundException {
        commandReader.start();
        //Initialize client necessities
        ClientImpl client = null;
        System.out.println("Client avviato...");
        int uiChoice, connectionChoice;
        //------------------------------------TYPE CONNECTION & TYPE UI CHOICES------------------------------------
        do {
            System.out.println("Che interfaccia grafica preferisci utilizzare?");
            System.out.println("1)Testuale");
            System.out.println("2)Grafica");

            uiChoice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
        } while (uiChoice < 1 || uiChoice > 2);
        do {
            System.out.println("Che metodo di comunicazione preferisci utilizzare?");
            System.out.println("1)RMI");
            System.out.println("2)Socket");

            connectionChoice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
        } while (connectionChoice < 1 || connectionChoice > 2);

        switch (uiChoice) {
            case 1 -> {
                switch (connectionChoice) {
                    case 1 -> {
                        //Getting the remote server by RMI
                        Registry registry = LocateRegistry.getRegistry();
                        Server server = (Server) registry.lookup("server");

                        //Creating a new client with a TextualUI and a RMI Server
                        client = new ClientImpl(server, new TextualUI());


                        startPingSenderThread(server);


                        //Calling the run method of the UI
                        client.run();
                    }
                    case 2 -> {
                        //Creating an Object that will allow the client to communicate with the Server (In the RMI case, this was created by RMI itself)
                        ServerStub serverStub = new ServerStub("localhost", 1234);

                        //Creating a new client with a TextualUI and a Socket Server
                        client = new ClientImpl(serverStub, new TextualUI());

                        //Creating a new Thread that will take care of checking on availability of connected client
                        startPingSenderThread(serverStub);

                        //Creating a new Thread that will take care of the responses coming from the Server side
                        startReceiverThread(client, serverStub);
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

                        //Creating a new client with a TextualUI and a RMI Server
                        Application.launch(GUI.class, "");

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

        //Closing client app
        System.exit(0);
    }

    public static void startPingSenderThread(Server server) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    server.ping();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Timer pingSender = new Timer("PingSender");
        pingSender.scheduleAtFixedRate(timerTask, 30, 3000);
    }

    private static void startReceiverThread(Client client, ServerStub serverStub) {
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
}
