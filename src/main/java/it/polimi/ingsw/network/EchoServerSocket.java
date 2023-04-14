package it.polimi.ingsw.network;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EchoServerSocket extends Thread {
    private Game model;
    private GameController controller;
    private final int port = 1337;
    private ServerSocket serverSocket;
    private List<SingleClientHandler> clientHandlers;
    //Questa è una lista di client che verranno passati al server MultiClient che li gestirà singolarmente

    public EchoServerSocket() throws IOException {
        System.out.println("Sono nel main");
        clientHandlers = new ArrayList<>();

        serverSocket = new ServerSocket(port);

        int numPlayers = 4;
        ArrayList<Player> players = new ArrayList<Player>(numPlayers);
        ArrayList<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();
        List<JsonBoardPattern> boardPatterns = new ArrayList<>();
        Gson gson = new Gson();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {
            }.getType());

            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/boards.json"));
            boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {
            }.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        players.add(new Player("Alessandro", true, personalGoals.get(0), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Andrea", true, personalGoals.get(1), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Francesco", true, personalGoals.get(2), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Luca", true, personalGoals.get(3), new ArrayList<ScoreTile>(), new Bookshelf()));

        int[][] temp = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}};

        model = new Game(numPlayers, players, personalGoals, boardPatterns.stream().filter(boardPattern -> boardPattern.numberOfPlayers() == players.size()).findFirst().orElse(null));

        controller = new GameController(model/*, view*/);

        this.start();
    }
    public void closeConnection() throws IOException {
        serverSocket.close();
    }
    public void run() {

        //aggiunge un handler sulla connnessione appena arrivata e lo fa partire
        while(true) {
            try {
                clientHandlers.add(new SingleClientHandler(serverSocket.accept(), controller));
                System.out.println("Si è connesso un client, assegno l'handler");
                clientHandlers.get(clientHandlers.size()-1).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
