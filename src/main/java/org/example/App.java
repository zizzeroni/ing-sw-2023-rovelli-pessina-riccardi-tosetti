package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import model.Game;
import model.Player;
import model.PersonalGoal;
import model.JsonBoardPattern;

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
        int numPlayers = 4;
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


    }
}
