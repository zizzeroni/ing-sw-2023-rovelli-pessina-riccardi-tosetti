package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.commongoal.EightShapelessPatternGoal;
import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.BookshelfView;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Game game;
    private List<Player> players;
    private List<CommonGoal> commonGoals;
    String gamesPath = "src/test/resources/storage/games.json";

    @BeforeEach
    public void resetAttributes() {
        this.game = null;
        this.players = new ArrayList<Player>(4);
        this.commonGoals = new ArrayList<CommonGoal>(2);

        this.players.add(new Player("Alessandro", true, new ArrayList<>(Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4))), new Bookshelf()));
        this.players.add(new Player("Andrea", true, new ArrayList<>(Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4))), new Bookshelf()));
        this.players.add(new Player("Francesco", false, new ArrayList<>(Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4))), new Bookshelf()));
        this.players.add(new Player("Luca", true, new ArrayList<>(Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4))), new Bookshelf()));

        this.commonGoals.add(new FourCornersPatternGoal());
        this.commonGoals.add(new EightShapelessPatternGoal());

        this.players.get(0).setPersonalGoal(new PersonalGoal(1, new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(0).setPersonalGoal(new PersonalGoal(2, new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(0).setPersonalGoal(new PersonalGoal(3, new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(0).setPersonalGoal(new PersonalGoal(4, new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test constructors validity")
    public void check_if_constructor_are_valid() {

        this.game = new Game();
        assertEquals(this.game.getGameState(), GameState.IN_CREATION);
        assertTrue(this.game.getPlayers().isEmpty());
        assertEquals(this.game.getActivePlayerIndex(), 0);

        for (int i = 0; i < this.game.getBoard().getTiles().length; i++) {
            for (int j = 0; j < this.game.getBoard().getTiles()[0].length; j++) {
                assertNull(this.game.getBoard().getTiles()[i][j].getColor());
                assertEquals(0, this.game.getBoard().getTiles()[i][j].getId());
            }
        }

        assertEquals(this.game.getNumberOfPlayersToStartGame(), 0);
        assertEquals(this.game.getBag().size(), OptionsValues.BAG_SIZE);
        assertEquals(0, this.game.getCommonGoals().size());

        int[][] pattern = new int[][]{
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0}
        };

        List<Player> players = new ArrayList<>(Arrays.asList(new Player("Andrea", true), new Player("Marco", false)));
        this.game = new Game(2, players, new ArrayList<>(Arrays.asList(new PersonalGoal(), new PersonalGoal())), new JsonBoardPattern(4, pattern));

        assertEquals(this.game.getGameState(), GameState.IN_CREATION);
        for (int i = 0; i < players.size(); i++) {
            assertNotNull(players.get(i).getBookshelf());
            assertNotNull(players.get(i).getPersonalGoal());
            assertNotNull(players.get(i).getScoreTiles());
        }
        assertEquals(0, this.game.getActivePlayerIndex());
        assertNotNull(this.game.getBoard());
        assertEquals(2, this.game.getNumberOfPlayersToStartGame());
        assertEquals(95, this.game.getBag().size());
        assertEquals(0, this.game.getCommonGoals().size());

        this.game = new Game(null, 2, 1, players, new ArrayList<>(Arrays.asList(new Tile(TileColor.PURPLE, 1), new Tile(TileColor.BLUE, 3))), new Board(new JsonBoardPattern(2, pattern)), new ArrayList<>());
        this.game.removeListener();
        assertNull(this.game.getGameState());
        this.game.setGameState(GameState.ON_GOING);
        assertEquals(this.game.getGameState(), GameState.ON_GOING);

        for (int i = 0; i < players.size(); i++) {
            assertNotNull(players.get(i).getBookshelf());
            assertNotNull(players.get(i).getPersonalGoal());
            assertNotNull(players.get(i).getScoreTiles());
        }

        assertEquals(1, this.game.getActivePlayerIndex());
        assertNotNull(this.game.getBoard());
        assertEquals(2, this.game.getNumberOfPlayersToStartGame());
        assertEquals(2, this.game.getBag().size());
        assertEquals(0, this.game.getCommonGoals().size());

        this.game.setActivePlayerIndex(2);
        assertEquals(2, this.game.getActivePlayerIndex());
        this.game.setPlayers(null);
        assertNull(this.game.getPlayers());
        this.game.setBag(null);
        assertNull(this.game.getBag());
        this.game.setBag(null);
        assertNull(this.game.getBag());
        this.game.setCommonGoals(null);
        assertNull(this.game.getCommonGoals());
        this.game.setBoard(null);
        assertNull(this.game.getBoard());
        this.game.setNumberOfPlayersToStartGame(3);
        assertEquals(3, this.game.getNumberOfPlayersToStartGame());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that you can retrieve a player from his nickname")
    public void get_player_from_nickname() {
        this.game = new Game();
        this.game.setPlayers(this.players);

        assertEquals(this.players.get(0), this.game.getPlayerFromNickname("Alessandro"));
        assertNotEquals(this.players.get(1), this.game.getPlayerFromNickname("Alessandro"));
        assertNotEquals(this.players.get(2), this.game.getPlayerFromNickname("Alessandro"));
        assertNotEquals(this.players.get(3), this.game.getPlayerFromNickname("Alessandro"));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that players are in the game, not necessarly connected")
    public void players_are_in_the_game_the_game() {
        this.game = new Game();
        this.game.setPlayers(this.players);

        assertTrue(this.game.isPlayerInGame("Luca"));
        assertFalse(this.game.isPlayerInGame("Fabrizio"));
        assertTrue(this.game.isPlayerInGame("Francesco"));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the game can create folders and files in which store himself")
    public void can_create_folders_and_files_to_store_a_game_locally() {
        this.game = new Game();
        this.game.createGameFileIfNotExist(gamesPath);

        File createdFile = new File(gamesPath);

        assertTrue(createdFile.exists() && createdFile.isFile());
    }

    @Test
    @DisplayName("Check if on turn change the size of the bag remains the same with a board who doesn't need a refill")
    public void bag_size_remains_the_same_when_board_refill_is_not_needed_on_turn_change() {

        Board board = new Board();
        board.setTiles(new Tile[][]{
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
                {new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile()},
                {new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()}
        });

        List<Tile> bag = new ArrayList<>();

        assertEquals("P", board.getTiles()[0][4].getColor().toGUI());
        board.getTiles()[0][4].setColor(TileColor.BLUE);
        board.getTiles()[0][4].setId(3);
        assertEquals("B", board.getTiles()[0][4].getColor().toGUI());

        for (int i = 0; i < 132; i++) {
            bag.add(new Tile(TileColor.values()[i % 6]));
        }

        int size = bag.size();

        this.game = new Game(4, 0, this.players, bag, board, this.commonGoals);
        //this.game.changeTurn();

        assertEquals(size, this.game.getBag().size());
    }

    @Test
    @DisplayName("Test view version of the game")
    public void test_view_version_of_the_game() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> new GameView(null));

        GameView view = new GameView(new Game());;

    }
}
