package it.polimi.ingsw;

import it.polimi.ingsw.network.Client;
import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.socketMiddleware.ServerStub;
import it.polimi.ingsw.utils.CommandReader;
import it.polimi.ingsw.utils.OptionsValues;
import it.polimi.ingsw.view.GUI.GraphicalUI;
import it.polimi.ingsw.view.TUI.TextualUI;
import javafx.application.Application;
import org.fusesource.jansi.AnsiConsole;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is used to represent the application's client.
 * It also contains the methods to initialize the pinging and receiving server's threads.
 *
 * @see Client
 * @see Server
 */
public class AppClient {
    static CommandReader commandReader = new CommandReader();

    /**
     * It is the main method of the AppClient.
     * Initialize all the necessary client's information.
     * Asks the preferred client connection's and UI types and manages the related player's choices.
     *
     * @param args the main's arguments.
     * @throws RemoteException   called to handle connection errors.
     * @throws NotBoundException called to handle UI errors.
     * @see it.polimi.ingsw.model.Player
     * @see Client
     * @see it.polimi.ingsw.view.GenericUILogic
     */
    public static void main(String[] args) throws RemoteException, NotBoundException {
        AnsiConsole.systemInstall();
        Scanner input = new Scanner(System.in);
        String ServeripAddress = "";
        String regex = "(localhost|\\b(?:(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d\\d?)(?::\\d{0,4})?\\b)";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        while (ServeripAddress.equals("")) {
            System.out.println("Insert a valid IpAddress for the server:");
            ServeripAddress = input.next();
            matcher = pattern.matcher(ServeripAddress);
            ServeripAddress = matcher.matches() ? ServeripAddress : "";
        }

        String clientIpAddress;
        if (ServeripAddress.equals("localhost") || ServeripAddress.equals("127.0.0.1")) {
            clientIpAddress = "127.0.0.1";
        } else {
            clientIpAddress = getFirstUpNetworkInterface();
        }
        System.setProperty("java.rmi.server.hostname", clientIpAddress);


        commandReader.start();
        //Initialize client necessities
        ClientImpl client = null;
        System.out.println("Client started...");
        int uiChoice, connectionChoice;
        //------------------------------------TYPE CONNECTION & TYPE UI CHOICES------------------------------------
        do {
            System.out.println("What interface do you prefer to use?");
            System.out.println("1)Textual");
            System.out.println("2)Graphical");

            uiChoice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
        } while (uiChoice < 1 || uiChoice > 2);
        do {
            System.out.println("What method of communication do you prefer to use?");
            System.out.println("1)RMI");
            System.out.println("2)Socket");

            connectionChoice = CommandReader.standardCommandQueue.waitAndGetFirstIntegerCommandAvailable();
        } while (connectionChoice < 1 || connectionChoice > 2);

        switch (uiChoice) {
            case 1 -> {
                switch (connectionChoice) {
                    case 1 -> {
                        //Getting the remote server by RMI
                        Registry registry = LocateRegistry.getRegistry(ServeripAddress, OptionsValues.RMI_PORT);
                        Server server = (Server) registry.lookup(OptionsValues.SERVER_RMI_NAME);

                        //Creating a new client with a TextualUI and a RMI Server
                        client = new ClientImpl(server, new TextualUI());
                        startPingSenderThread(server);

                        //Calling the run method of the UI
                        client.run();
                    }
                    case 2 -> {
                        //Creating an Object that will allow the client to communicate with the Server (In the RMI case, this was created by RMI itself)
                        ServerStub serverStub = new ServerStub(ServeripAddress, OptionsValues.SOCKET_PORT);

                        //Creating a new client with a TextualUI and a Socket Server
                        client = new ClientImpl(serverStub, new TextualUI());

                        //Creating a new Thread that will take care of checking on availability of connected client
                        startPingSenderThread(serverStub);

                        //Creating a new Thread that will take care of the responses coming from the Server side
                        startReceiverThread(client, serverStub);

                        //Calling the run method of the UI
                        client.run();
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
                        Application.launch(GraphicalUI.class, "1", ServeripAddress, String.valueOf(OptionsValues.RMI_PORT));
                    }
                    case 2 -> {
                        Application.launch(GraphicalUI.class, "2", ServeripAddress, String.valueOf(OptionsValues.SOCKET_PORT));
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

    /**
     * Start up the thread by ping the server.
     *
     * @param server the server to be pinged.
     * @see Server
     */
    public static void startPingSenderThread(Server server) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    server.ping();
                } catch (RemoteException e) {
                    System.err.println("Error while pinging server. " + e.getMessage());
                }
            }
        };

        Timer pingSender = new Timer("PingSender");
        pingSender.scheduleAtFixedRate(timerTask, 30, OptionsValues.MILLISECOND_PING_TO_SERVER_PERIOD);
    }

    /**
     * Creates a new Thread that will take care of the responses coming from the Server side.
     * Starts up the related thread's receiver.
     *
     * @param client     the player's client.
     * @param serverStub the stub used to enable server's interaction.
     * @see Thread
     * @see Server
     * @see ServerStub
     * @see Client
     */
    private static void startReceiverThread(Client client, ServerStub serverStub) {

        new Thread(() -> {
            while (true) {
                try {
                    serverStub.receive(client);
                } catch (RemoteException e) {
                    System.err.println("[COMMUNICATION:ERROR] Error while receiving message from server (Server was closed);\n" + "     Caused by: " + e.getMessage());
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

    /**
     * Get a random network interface (not localhost).
     *
     * @return the identified network interfaces.
     * @throws RemoteException when a communication error occurs.
     */
    private static String getFirstUpNetworkInterface() throws RemoteException {
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
