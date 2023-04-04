package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import controller.GameController;
import model.*;
import model.tile.ScoreTile;
import model.tile.Tile;
import model.tile.TileColor;
import model.view.GameView;
import view.TextualUI;
import view.UI;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {}.getType());
            reader.close();

            reader = Files.newBufferedReader(Paths.get("src/main/java/storage/boards.json"));
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
        Gson gson = new Gson();

        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {}.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        players.add(new Player("Alessandro", true, personalGoals.get(0), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Andrea", true, personalGoals.get(1), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Francesco", true, personalGoals.get(2), new ArrayList<ScoreTile>(), new Bookshelf()));
        players.add(new Player("Luca", true, personalGoals.get(3), new ArrayList<ScoreTile>(), new Bookshelf()));

        Game model = new Game(numPlayers, players, personalGoals);
        Tile[][] temp = {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, null, null},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.WHITE), new Tile(TileColor.PURPLE), null, null, null},
                {null, null, new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.WHITE), null},
                {null, new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.PURPLE), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), null, null},
                {null, null, null, new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.YELLOW), null, null, null},
                {null, null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, null},
                {null, null, null, null, null, null, null, null, null}
        };
        Board board = new Board("",29,temp);
        model.setBoard(board);
        GameView modelView = new GameView(model);
        UI view = new TextualUI(modelView);
        GameController controller = new GameController(model, view);

        modelView.addObserver(view);
        view.addObserver(controller);

        view.run();
    }
}
