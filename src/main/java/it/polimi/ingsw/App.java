package it.polimi.ingsw;

import it.polimi.ingsw.network.ClientImpl;
import it.polimi.ingsw.network.Server;
import it.polimi.ingsw.network.ServerImpl;
import it.polimi.ingsw.view.TextualUI;

import java.rmi.RemoteException;
import java.util.Scanner;


/**
 *
 */
public class App {
    public static void main(String[] args) {

        /*int numPlayers = 4;
        Gson gson = new Gson();

        ArrayList<Player> players = new ArrayList<Player>(numPlayers);
        List<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();
        List<JsonBoardPattern> boardPatterns = new ArrayList<JsonBoardPattern>();

        players.add(new Player("Alessandro", true));
        players.add(new Player("Andrea", true));
        players.add(new Player("Francesco", true));
        players.add(new Player("Luca", true));

        //randomize player's starting order
        Collections.shuffle(players);

        //read available personal goals and boards from a file
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/storage/patterns/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {}.getType());

            reader = Files.newBufferedReader(Paths.get("src/main/resources/storage/patterns/boards.json"));
            boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {}.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game game = new Game(numPlayers, players, personalGoals, boardPatterns.stream().filter(boardPattern -> boardPattern.numberOfPlayers() == players.size()).findFirst().orElse(null));

        game.changeTurn();

        */

        /*
        int numPlayers = 4;
        ArrayList<Player> players = new ArrayList<Player>(numPlayers);
        ArrayList<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();
        List<JsonBoardPattern> boardPatterns = new ArrayList<>();
        Gson gson = new Gson();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources/storage/patterns/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {
            }.getType());

            reader = Files.newBufferedReader(Paths.get("src/main/resources/storage/patterns/boards.json"));
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

        Game model = new Game(numPlayers, players, personalGoals, boardPatterns.stream().filter(boardPattern -> boardPattern.numberOfPlayers() == players.size()).findFirst().orElse(null));

        GameView modelView = new GameView(model);
        UI view = new TextualUI(modelView);
        GameController controller = new GameController(model/*, view*//*);

        //modelView.addObserver(view);
        //view.addObserver(controller);
        /*model.registerListener(modelView);
        model.getBoard().registerListener(modelView);
        for (Player player : model.getPlayers()) {
            player.getBookshelf().registerListener(modelView);
        }
        modelView.registerListener(view);
        view.registerListener(controller);

        view.run();*/

        //REMINDER: This app doens't work anymore since we introduced the attribute "State" in UI class which oblige to have 2 or more players in order to start the game
        Server server = null;
        try {
            server = new ServerImpl();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        ClientImpl client = null;
        Scanner s = new Scanner(System.in);
        System.out.println("Benvenuto a MyShelfie, inserisci il tuo nickname!");
        String nick = s.next();
        try {
            client = new ClientImpl(server, new TextualUI(), nick);
        } catch (RemoteException e) {
            System.err.println("Error while creating new client: " + e.getMessage());
        }
        client.run();
    }
}
