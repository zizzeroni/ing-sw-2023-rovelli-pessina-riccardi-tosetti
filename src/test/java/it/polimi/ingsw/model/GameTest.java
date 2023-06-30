package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.commongoal.EightShapelessPatternGoal;
import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
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
        this.players.get(1).setPersonalGoal(new PersonalGoal(2, new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(2).setPersonalGoal(new PersonalGoal(3, new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(3).setPersonalGoal(new PersonalGoal(4, new Tile[][]{
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

        assertThrows(IllegalArgumentException.class, () -> new GameView(null));

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

        this.game = new Game(null, 2, 1, players, new ArrayList<>(Arrays.asList(new Tile(TileColor.PURPLE, 1), new Tile(TileColor.BLUE, 3))), new Board(new JsonBoardPattern(2, pattern)), new ArrayList<>());
        GameView view = new GameView(this.game);

        assertNotNull(view.getPlayerViewFromNickname("Francesco"));
        assertEquals("Francesco", view.getPlayerViewFromNickname("Francesco").getNickname());
        assertTrue(view.isPlayerInGame("Francesco"));
        assertFalse(view.isPlayerInGame("Marco"));
        assertEquals(this.game.getActivePlayerIndex(), view.getActivePlayerIndex());
        assertEquals(this.game.getNumberOfPlayersToStartGame(), view.getNumberOfPlayersToStartGame());
        assertEquals(this.game.getGameState(), view.getGameState());

        for (int i = 0; i < view.getBag().size(); i++) {
            assertEquals(this.game.getBag().get(i).getId(), view.getBag().get(i).getId());
            assertEquals(this.game.getBag().get(i).getColor(), view.getBag().get(i).getColor());
        }

        assertEquals(this.game.getBoard().getNumberOfUsableTiles(), view.getBoard().getNumberOfUsableTiles());
        assertEquals(this.game.getBoard().getNumberOfColumns(), view.getBoard().getNumberOfColumns());
        assertEquals(this.game.getBoard().getNumberOfRows(), view.getBoard().getNumberOfRows());
        assertEquals("    1 2 3 4 5 6 7 8 9 \n" +
                "1 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "2 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "3 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "4 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "5 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "6 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "7 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "8 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "9 [ 0 0 0 0 0 0 0 0 0 ] ", view.getBoard().toString());

        for (int row = 0; row < view.getBoard().getTiles().length; row++) {
            for (int column = 0; column < view.getBoard().getTiles()[0].length; column++) {
                if (view.getBoard().getTiles()[row][column] != null) {
                    assertEquals(this.game.getBoard().getTiles()[row][column].getId(), view.getBoard().getTiles()[row][column].getId());
                    assertEquals(this.game.getBoard().getTiles()[row][column].getColor(), view.getBoard().getTiles()[row][column].getColor());
                }
            }
        }

        for (int i = 0; i < view.getPlayers().size(); i++) {
            assertEquals(this.game.getPlayers().get(i).getChat(), view.getPlayers().get(i).getChat());
            assertEquals(this.game.getPlayers().get(i).getNickname(), view.getPlayers().get(i).getNickname());

            for (int row = 0; row < view.getPlayers().get(i).getBookshelf().getTiles().length; row++) {
                for (int column = 0; column < view.getPlayers().get(i).getBookshelf().getTiles()[0].length; column++) {
                    if (view.getPlayers().get(i).getBookshelf().getTiles()[row][column] != null) {
                        assertEquals(this.game.getPlayers().get(i).getBookshelf().getTiles()[row][column].getId(), view.getPlayers().get(i).getBookshelf().getTiles()[row][column].getId());
                        assertEquals(this.game.getPlayers().get(i).getBookshelf().getTiles()[row][column].getColor(), view.getPlayers().get(i).getBookshelf().getTiles()[row][column].getColor());
                        assertEquals(this.game.getPlayers().get(i).getBookshelf().getNumberOfEmptyCellsInColumn(column), view.getPlayers().get(i).getBookshelf().getNumberOfEmptyCellsInColumn(column));
                    }
                }
            }

            assertEquals(this.game.getPlayers().get(i).getBookshelf().getNumberOfRows(), view.getPlayers().get(i).getBookshelf().getNumberOfRows());
            assertEquals(this.game.getPlayers().get(i).getBookshelf().getNumberOfColumns(), view.getPlayers().get(i).getBookshelf().getNumberOfColumns());
            assertEquals(this.game.getPlayers().get(i).getBookshelf().getMaxNumberOfCellsFreeInBookshelf(), view.getPlayers().get(i).getBookshelf().getMaxNumberOfCellsFreeInBookshelf());
            assertEquals(this.game.getPlayers().get(i).getBookshelf().getPointsForEachGroup(), view.getPlayers().get(i).getBookshelf().getPointsForEachGroup());

            for (int j = 0; j < view.getPlayers().get(i).getScoreTiles().size(); j++) {
                assertEquals(this.game.getPlayers().get(i).getScoreTiles().get(j).getValue(), view.getPlayers().get(i).getScoreTiles().get(j).getValue());
                assertEquals(this.game.getPlayers().get(i).getScoreTiles().get(j).getPlayerID(), view.getPlayers().get(i).getScoreTiles().get(j).getPlayerID());
                assertEquals(this.game.getPlayers().get(i).getScoreTiles().get(j).getCommonGoalID(), view.getPlayers().get(i).getScoreTiles().get(j).getCommonGoalID());
            }

            assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getNumberOfRows(), view.getPlayers().get(i).getPersonalGoal().getNumberOfRows());
            assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getNumberOfColumns(), view.getPlayers().get(i).getPersonalGoal().getNumberOfColumns());
            assertEquals("    1 2 3 4 5 \n" +
                    "1 [ 0 0 0 0 \u001B[36mC\u001B[39m ] \n" +
                    "2 [ 0 \u001B[33mY\u001B[39m 0 0 0 ] \n" +
                    "3 [ \u001B[37mW\u001B[39m 0 0 0 0 ] \n" +
                    "4 [ 0 0 0 \u001B[32mG\u001B[39m 0 ] \n" +
                    "5 [ 0 \u001B[34mB\u001B[39m 0 0 0 ] \n" +
                    "6 [ 0 0 0 \u001B[35mP\u001B[39m 0 ] ", view.getPlayers().get(i).getPersonalGoal().toString());

            for (int row = 0; row < view.getPlayers().get(i).getPersonalGoal().getPattern().length; row++) {
                for (int column = 0; column < view.getPlayers().get(i).getPersonalGoal().getPattern()[0].length; column++) {
                    if (view.getPlayers().get(i).getPersonalGoal().getPattern()[row][column] != null) {
                        assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getPattern()[row][column].getId(), view.getPlayers().get(i).getPersonalGoal().getPattern()[row][column].getId());
                        assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getPattern()[row][column].getColor(), view.getPlayers().get(i).getPersonalGoal().getPattern()[row][column].getColor());
                    }
                }
            }

            assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getId(), view.getPlayers().get(i).getPersonalGoal().getId());
            if (view.getPlayers().get(i).getPersonalGoal().getSingleTile(0, 4) != null) {
                assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getSingleTile(0, 4).getId(), view.getPlayers().get(i).getPersonalGoal().getSingleTile(0, 4).getId());
                assertEquals(this.game.getPlayers().get(i).getPersonalGoal().getSingleTile(0, 4).getColor(), view.getPlayers().get(i).getPersonalGoal().getSingleTile(0, 4).getColor());
            }
            assertEquals(this.game.getPlayers().get(i).getPersonalGoal().score(this.game.getPlayers().get(i).getBookshelf()), view.getPlayers().get(i).getPersonalGoal().score(view.getPlayers().get(i).getBookshelf()));

        }

        assertFalse(view.isPaused());

        for (int i = 0; i < view.connectedPlayers().size(); i++) {
            assertTrue(this.game.getPlayerFromNickname(view.connectedPlayers().get(i).getNickname()).isConnected());
        }

    }
}
