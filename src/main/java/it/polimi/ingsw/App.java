package it.polimi.ingsw;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.TextualUI;
import it.polimi.ingsw.view.UI;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
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
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {}.getType());

            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/boards.json"));
            boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {}.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game game = new Game(numPlayers, players, personalGoals, boardPatterns.stream().filter(boardPattern -> boardPattern.numberOfPlayers() == players.size()).findFirst().orElse(null));

        game.changeTurn();

        */
        int numPlayers = 4;
        ArrayList<Player> players = new ArrayList<Player>(numPlayers);
        ArrayList<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();
        List<JsonBoardPattern> boardPatterns = new ArrayList<>();
        Gson gson = new Gson();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {}.getType());

            reader = Files.newBufferedReader(Paths.get("src/main/java/it/polimi/ingsw/storage/boards.json"));
            boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {}.getType());
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
        GameController controller = new GameController(model, view);

        modelView.addObserver(view);
        view.addObserver(controller);

        view.run();
    }
}
