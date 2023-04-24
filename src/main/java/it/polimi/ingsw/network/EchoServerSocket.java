package it.polimi.ingsw.network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//REMINDER: This class is not used anymore
public class EchoServerSocket extends Thread {
    private final int port = 1337;
    private ServerSocket serverSocket;
    private List<SingleClientHandler> clientHandlers;
    //Questa è una lista di client che verranno passati al server MultiClient che li gestirà singolarmente

    public EchoServerSocket() throws IOException {
        clientHandlers = new ArrayList<>();

        serverSocket = new ServerSocket(port);

        this.start();
    }

    public void closeConnection() throws IOException {
        serverSocket.close();
    }

    public void run() {
        //aggiunge un handler sulla connnessione appena arrivata e lo fa partire
        /*while(true) {
            try {
                clientHandlers.add(new SingleClientHandler(serverSocket.accept()));
                clientHandlers.get(clientHandlers.size()-1).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }*/
    }
}
