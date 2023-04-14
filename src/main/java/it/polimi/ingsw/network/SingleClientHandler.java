package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.*;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.model.Choice;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SingleClientHandler extends Thread {
    Socket socket;
    GameController gameController;
    Game model;
    public SingleClientHandler(Socket socket, GameController gameController){
        this.socket=socket;
        this.gameController= gameController;
        this.model = gameController.getModel();
    }
    @Override
    public void run() {

        //GameView view = new GameView(model);

        while(true) {
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                Scanner input = new Scanner(socket.getInputStream());

                output.flush();

                //Il client invierà metodo



                output.println("Insert 'Exit'  if you want to terminate the connnections");
                String line = input.nextLine();
                output.println("Hai scritto: " + line);

                if(line.equals("Exit")){
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
