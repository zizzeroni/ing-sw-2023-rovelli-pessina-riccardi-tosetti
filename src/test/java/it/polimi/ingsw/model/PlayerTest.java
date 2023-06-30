package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    Player player;

    @BeforeEach
    public void setUpForTests() {
        this.player = null;
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test constructors validity")
    public void check_if_constructor_are_valid() {
        this.player = new Player("Andrea", true);
        assertEquals(this.player.getNickname(), "Andrea");
        assertTrue(this.player.isConnected());

        List<ScoreTile> emptyScoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile()));
        this.player = new Player("Andrea", true, emptyScoreTiles);
        assertEquals(this.player.getNickname(), "Andrea");
        assertTrue(this.player.isConnected());

        for (int i = 0; i < emptyScoreTiles.size(); i++) {
            assertEquals(this.player.getScoreTiles().get(i), emptyScoreTiles.get(i));
        }

        Bookshelf bookshelf = new Bookshelf();

        this.player = new Player("Andrea", true, emptyScoreTiles, bookshelf);
        assertEquals("Andrea", this.player.getNickname());
        assertTrue(this.player.isConnected());

        for (int i = 0; i < emptyScoreTiles.size(); i++) {
            assertEquals(this.player.getScoreTiles().get(i), emptyScoreTiles.get(i));
        }

        assertEquals(bookshelf, this.player.getBookshelf());

        PersonalGoal personalGoal = new PersonalGoal();
        this.player = new Player("Andrea", true, personalGoal, emptyScoreTiles, bookshelf);
        assertEquals("Andrea", this.player.getNickname());
        assertTrue(this.player.isConnected());

        for (int i = 0; i < emptyScoreTiles.size(); i++) {
            assertEquals(this.player.getScoreTiles().get(i), emptyScoreTiles.get(i));
        }

        assertEquals(bookshelf, this.player.getBookshelf());
        assertEquals(personalGoal, this.player.getPersonalGoal());

        List<Message> chat = new ArrayList<>(List.of(new Message(MessageType.PRIVATE, "Paolo", "Andrea", "Ciao")));
        this.player.addMessage(new Message(MessageType.BROADCAST, "Andrea", "Andrea", "Ciao a tutti"));

        this.player = new Player("Andrea", true, personalGoal, emptyScoreTiles, bookshelf, chat);
        assertEquals("Andrea", this.player.getNickname());
        assertTrue(this.player.isConnected());

        for (int i = 0; i < emptyScoreTiles.size(); i++) {
            assertEquals(this.player.getScoreTiles().get(i), emptyScoreTiles.get(i));
        }

        assertEquals(bookshelf, this.player.getBookshelf());
        assertEquals(personalGoal, this.player.getPersonalGoal());

        for (int i = 0; i < chat.size(); i++) {
            assertEquals(this.player.getChat().get(i), chat.get(i));
        }

    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that you can retrieve a player score")
    public void retrieve_player_score() {
        this.player = new Player("Andrea", true);
        this.player.setNickname("Marco");
        this.player.setScoreTiles(new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile())));
        this.player.addScoreTile(new ScoreTile());
        ScoreTile singleScoreTile = new ScoreTile();
        singleScoreTile.setPlayerID(1);
        singleScoreTile.setCommonGoalID(1);
        singleScoreTile.setValue(0);
        this.player.setSingleScoreTile(singleScoreTile, 2);
        this.player.setConnected(true);
        this.player.setPersonalGoal(new PersonalGoal());
        assertEquals(0, this.player.score());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that you can retrieve a correct player score")
    public void retrieve_correct_player_score() {
        this.player = new Player("Andrea", true);
        this.player.setScoreTiles(Arrays.asList(new ScoreTile(8, 1, 2), new ScoreTile(), new ScoreTile()));
        this.player.setBookshelf(new Bookshelf(new Tile[][]{
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}
        }));
        this.player.setPersonalGoal(new PersonalGoal());
        assertEquals(32, this.player.score());
    }

}


