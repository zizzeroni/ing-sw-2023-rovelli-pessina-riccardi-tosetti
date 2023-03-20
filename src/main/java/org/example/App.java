package org.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Bookshelf;
import model.Game;
import model.PersonalGoal;
import model.Player;
import model.tile.GoalTile;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

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
        ArrayList<PersonalGoal> personalGoals = new ArrayList<PersonalGoal>();

        players.add(new Player("Alessandro", true));
        players.add(new Player("Andrea", true));
        players.add(new Player("Francesco", true));
        players.add(new Player("Luca", true));

        //randomize player's starting order
        Collections.shuffle(players);

        //read available personal goals from a file
        try {
            Reader reader = Files.newBufferedReader(Paths.get("src/main/java/storage/personal-goals.json"));
            personalGoals = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {}.getType());
            reader.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Game game = new Game(numPlayers, players, personalGoals);

        game.changeTurn();


    }
}
