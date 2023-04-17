package it.polimi.ingsw.network;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.*;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.UI;
import it.polimi.ingsw.model.Choice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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

        //invio della view relativa al modello al client

//        GameView view = new GameView(model);
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try {
//            ObjectOutputStream out = new ObjectOutputStream(os);
//            out.writeObject(view);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        while(true) {
            try {
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                Scanner input = new Scanner(socket.getInputStream());

                output.flush();

                //Il client invierà metodo con / senza parametri e qua verra lanciato nel GameController

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
