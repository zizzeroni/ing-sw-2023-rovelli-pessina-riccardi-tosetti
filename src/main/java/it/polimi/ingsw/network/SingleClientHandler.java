package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.view.GameView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SingleClientHandler extends Thread {
    Socket socket;
    public SingleClientHandler(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        while(true) {
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                Scanner input = new Scanner(socket.getInputStream());

                output.println("Connection open");
                output.flush();

                output.println("Insert 'Exit'  if you want to terminate the connnections");
                String line = input.nextLine();
                output.println(line);

                if(line.equals("Exit")){
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
