package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.TileView;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OnGoingStateTest {

    GameController controller;
    OnGoingState state;
    String gamesPath = "src/test/resources/storage/games.json";
    String gamesPathBackup = "src/test/resources/storage/games-bkp.json";

    /**
     * Test class
     */
    @BeforeEach
    public void resetOnGoingState() {
        controller = new GameController(new Game());
        state = new OnGoingState(controller);
        this.controller.changeState(state);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that private messages aren't sent to other players")
    public void private_messages_are_not_visible_to_other_players() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Francesco", true)));
        this.controller.sendPrivateMessage("Luca", "Andrea", "ciao");

        Message senderPrivateMessage = this.controller.getModel().getPlayers().get(0).getChat().get(0);
        Message receiverPrivateMessage = this.controller.getModel().getPlayers().get(1).getChat().get(0);
        List<Message> otherPlayerChat = this.controller.getModel().getPlayers().get(2).getChat();

        assertEquals(senderPrivateMessage.messageType(), MessageType.PRIVATE);
        assertEquals(receiverPrivateMessage.messageType(), MessageType.PRIVATE);
        assertEquals(senderPrivateMessage.content(), "ciao");
        assertEquals(receiverPrivateMessage.content(), "ciao");
        assertEquals(otherPlayerChat.size(), 0);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that broadcast messages are sent to all players")
    public void broadcast_messages_are_sent_to_all_players() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Francesco", true)));
        this.controller.sendBroadcastMessage("Andrea", "ciao");

        for (Player player : this.controller.getModel().getPlayers()) {
            Message playerLastMessage = player.getChat().get(0);
            assertEquals(playerLastMessage.messageType(), MessageType.BROADCAST);
            assertEquals(playerLastMessage.content(), "ciao");
        }
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that change turn with only single tiles on board cause it's refill")
    public void changing_turn_with_a_single_tiles_board_cause_the_refill() {

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

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Francesco", false)));
        this.controller.getModel().getBoard().setPattern(new JsonBoardPattern(3, pattern));
        this.state.changeTurn(gamesPath, gamesPathBackup);

        for (int row = 0; row < this.controller.getModel().getBoard().getTiles().length; row++) {
            for (int column = 0; column < this.controller.getModel().getBoard().getTiles()[0].length; column++) {
                assertNotNull(this.controller.getModel().getBoard().getTiles()[row][column]);
            }
        }

    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that if next player is not connected, his turn is skipped")
    public void changing_turn_to_someone_disconnected_makes_his_turn_being_skipped() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Francesco", false)));
        this.controller.getModel().setActivePlayerIndex(1);
        this.controller.changeTurn(gamesPath, gamesPathBackup);

        assertEquals(this.controller.getModel().getActivePlayerIndex(), 0);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the player's choice is valid and moves some tiles from the board to the bookshelf")
    public void player_choice_is_valid_and_move_tiles_from_board_to_player_bookshelf() {

        this.controller.getModel().getBoard().setTiles(new Tile[][]{
                {new Tile(TileColor.BLUE, 1), new Tile(TileColor.PURPLE, 1), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
        });

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true)));
        this.controller.getModel().setActivePlayerIndex(0);
        this.controller.getModel().getPlayers().get(0).setBookshelf(new Bookshelf());

        try {
            this.state.insertUserInputIntoModel(new Choice(
                    new ArrayList<>(Arrays.asList(new TileView(new Tile(TileColor.BLUE, 1)), new TileView(new Tile(TileColor.PURPLE, 1)))),
                    new ArrayList<>(Arrays.asList(new Coordinates(0, 0), new Coordinates(0, 1))),
                    new int[]{0, 1},
                    1
            ));
        } catch (WrongInputDataException e) {
            throw new RuntimeException(e);
        }

        assertNull(this.controller.getModel().getBoard().getTiles()[0][0]);
        assertNull(this.controller.getModel().getBoard().getTiles()[0][1]);
        assertEquals(this.controller.getModel().getPlayers().get(0).getBookshelf().getTiles()[4][1].getColor(), TileColor.PURPLE);
        assertEquals(this.controller.getModel().getPlayers().get(0).getBookshelf().getTiles()[5][1].getColor(), TileColor.BLUE);
    }


    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the player's choice is valid and getting a full bookshelf with a matched common goal gives the score tile for the common goal and start the last turn if it's the first player to fill the bookshelf")
    public void player_choice_is_valid_and_gives_score_tile_points_and_start_last_turn_if_first_player_to_fulfill_the_bookshelf() {

        this.controller.getModel().getBoard().setTiles(new Tile[][]{
                {new Tile(TileColor.BLUE, 1), new Tile(TileColor.PURPLE, 1), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
        });

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile(), new ScoreTile()))), new Player("Luca", true)));
        this.controller.getModel().setCommonGoals(List.of(new FourCornersPatternGoal()));
        this.controller.getModel().setActivePlayerIndex(0);
        this.controller.getModel().getPlayers().get(0).setBookshelf(new Bookshelf(new Tile[][]{
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}
        }
        ));

        try {
            this.state.insertUserInputIntoModel(new Choice(
                    new ArrayList<>(List.of(new TileView(new Tile(TileColor.BLUE, 1)))),
                    new ArrayList<>(List.of(new Coordinates(0, 0))),
                    new int[]{0},
                    0
            ));
        } catch (WrongInputDataException e) {
            throw new RuntimeException(e);
        }

        assertEquals(2, this.controller.getModel().getPlayerFromNickname("Andrea").getScoreTiles().stream().filter(scoreTile -> scoreTile.getValue() != 0).count());
        assertTrue(this.controller.getModel().getPlayerFromNickname("Andrea").getBookshelf().isFull());
        assertInstanceOf(FinishingState.class, this.controller.getState());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that if the player's choice is not valid it does not change the model")
    public void invalid_player_choice_does_not_change_the_models() {

        this.controller.getModel().getBoard().setTiles(new Tile[][]{
                {new Tile(TileColor.BLUE, 1), new Tile(TileColor.PURPLE, 1), new Tile(), null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, new Tile(TileColor.PURPLE, 1), null, null, null, null, null, null, null},
                {new Tile(TileColor.PURPLE, 1), new Tile(TileColor.PURPLE, 1), new Tile(TileColor.PURPLE, 1), null, null, null, null, null, null},
                {null, new Tile(TileColor.PURPLE, 1), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
        });

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true)));
        this.controller.getModel().setActivePlayerIndex(0);
        this.controller.getModel().getPlayers().get(0).setBookshelf(new Bookshelf());

        try {
            this.state.insertUserInputIntoModel(new Choice(
                    new ArrayList<>(Arrays.asList(new TileView(new Tile(TileColor.BLUE, 1)), new TileView(new Tile(TileColor.PURPLE, 1)))),
                    new ArrayList<>(Arrays.asList(new Coordinates(0, 2), new Coordinates(0, 2))),
                    new int[]{0, 1},
                    1
            ));
        } catch (WrongInputDataException e) {
            assertNotNull(this.controller.getModel().getBoard().getTiles()[0][0]);
            assertNotNull(this.controller.getModel().getBoard().getTiles()[0][1]);
            assertNull(this.controller.getModel().getPlayers().get(0).getBookshelf().getTiles()[4][1]);
            assertNull(this.controller.getModel().getPlayers().get(0).getBookshelf().getTiles()[5][1]);
        }

        try {
            this.controller.insertUserInputIntoModel(new Choice(
                    new ArrayList<>(Arrays.asList(new TileView(new Tile(TileColor.BLUE, 1)), new TileView(new Tile(TileColor.PURPLE, 1)))),
                    new ArrayList<>(Arrays.asList(new Coordinates(4, 1), new Coordinates(0, 2))),
                    new int[]{0, 1},
                    1
            ));
        } catch (WrongInputDataException e) {
            assertNotNull(this.controller.getModel().getBoard().getTiles()[0][0]);
            assertNotNull(this.controller.getModel().getBoard().getTiles()[0][1]);
            assertNull(this.controller.getModel().getPlayers().get(0).getBookshelf().getTiles()[4][1]);
            assertNull(this.controller.getModel().getPlayers().get(0).getBookshelf().getTiles()[5][1]);
        }
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that adding a player which wasn't in the original lobby cause an exception")
    public void adding_a_new_player_is_only_valid_for_reconnection() {

        Exception exception = assertThrows(LobbyIsFullException.class, () -> this.state.addPlayer("Andrea"));

        String expectedMessage = "Cannot access a game: Lobby is full or you were not part of it at the start of the game";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);

        assertNull(this.controller.getModel().getPlayers().stream().filter(player -> player.getNickname().equals("Andrea")).findFirst().orElse(null));
    }


    /**
     * Test class
     */
    @Test
    @DisplayName("Test that adding a player which was in the original lobby cause the connection of the same user")
    public void adding_a_disconnected_player_cause_his_reconnection() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", false), new Player("Luca", true)));

        try {
            this.controller.addPlayer("Andrea");

        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }

        assertNotNull(this.controller.getModel().getPlayerFromNickname("Andrea"));
        assertTrue(this.controller.getModel().getPlayerFromNickname("Andrea").isConnected());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that resuming the game changes the state of the game to the same as the controller")
    public void change_state_when_resuming_game() {
        this.controller.tryToResumeGame();
        assertEquals(this.state.controller.getModel().getGameState(), this.controller.getModel().getGameState());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that startGame method does nothing in on going state")
    public void start_game_method_does_nothing_in_on_going_state() {
        this.controller.startGame();
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that disconnecting a player, with enough players to not pause the game, set his connection to false and if it's the active player, it changes turn")
    public void disconneting_a_player_without_pausing_the_game_set_connnection_false_and_change_turn_if_active() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Alessandro", true)));
        this.controller.getModel().setActivePlayerIndex(0);

        this.controller.disconnectPlayer("Andrea");

        assertEquals(1, this.controller.getModel().getActivePlayerIndex());
        assertFalse(this.controller.getModel().getPlayerFromNickname("Andrea").isConnected());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that disconnecting a player with enough players to pause the game, pauses the game")
    public void disconneting_a_player_set_connnection_false_and_change_turn_if_active() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true)));
        this.controller.getModel().setActivePlayerIndex(0);

        this.controller.disconnectPlayer("Andrea");

        assertInstanceOf(InPauseState.class, this.controller.getState());
        assertEquals(0, this.controller.getModel().getActivePlayerIndex());
        assertFalse(this.controller.getModel().getPlayerFromNickname("Andrea").isConnected());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that disconnecting a non active player does not change the turn")
    public void disconneting_a_non_active_player_does_not_change_turn() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true)));
        this.controller.getModel().setActivePlayerIndex(0);

        this.state.disconnectPlayer("Luca");

        assertEquals(this.controller.getModel().getActivePlayerIndex(), 0);
        assertFalse(this.controller.getModel().getPlayerFromNickname("Luca").isConnected());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that disconnecting the last connected player cause the reset of the game")
    public void disconneting_the_last_connected_player_cause_reset() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", false)));
        this.controller.getModel().setActivePlayerIndex(0);

        this.state.disconnectPlayer("Andrea");

        assertEquals(GameState.RESET_NEEDED, this.controller.getModel().getGameState());
        assertEquals(0, this.controller.getModel().getActivePlayerIndex());
        assertFalse(this.controller.getModel().getPlayerFromNickname("Andrea").isConnected());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that choosing the number of players does nothing in on going state")
    public void choosing_number_of_players_does_nothing() {
        this.controller.chooseNumberOfPlayerInTheGame(2);
        assertEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 0);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that checkExceedingPlayer method does nothing in on going state")
    public void check_exceeding_number_of_players_does_nothing() {
        assertDoesNotThrow(() -> this.controller.checkExceedingPlayer(OptionsValues.MIN_SELECTABLE_NUMBER_OF_PLAYERS - 1));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that restoreGameForPlayer method does nothing in on going state")
    public void restore_a_game_for_a_player_does_nothing() {
        PrintWriter writer;
        try {
            writer = new PrintWriter(gamesPath);

            writer.println("[{'gameState':'ON_GOING','numberOfPlayersToStartGame':2,'activePlayerIndex':0,'players':[{'nickname':'Andrea','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,{'color':'CYAN','id':0},null,{'color':'GREEN','id':0}],[null,null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,null,null],[null,{'color':'YELLOW','id':0},null,{'color':'BLUE','id':0},null],[{'color':'PURPLE','id':0},null,null,null,null]],'id':6},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},{'color':'PURPLE','id':0},null,null,null]]},'chat':[]},{'nickname':'Toso','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,null,null,{'color':'BLUE','id':0}],[null,{'color':'GREEN','id':0},null,null,null],[null,null,{'color':'CYAN','id':0},null,null],[{'color':'PURPLE','id':0},null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,{'color':'YELLOW','id':0},null]],'id':8},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},null,null,null,null],[{'color':'YELLOW','id':0},null,null,null,null]]},'chat':[]}],'bag':[{'color':'YELLOW','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':2},{'color':'PURPLE','id':1},{'color':'CYAN','id':0},{'color':'CYAN','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':0},{'color':'WHITE','id':1},{'color':'GREEN','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':1},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'WHITE','id':0},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':1},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'GREEN','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':2},{'color':'CYAN','id':2},{'color':'YELLOW','id':1},{'color':'BLUE','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':0},{'color':'PURPLE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':2},{'color':'BLUE','id':1},{'color':'CYAN','id':0},{'color':'GREEN','id':1},{'color':'YELLOW','id':1},{'color':'YELLOW','id':0},{'color':'GREEN','id':2},{'color':'GREEN','id':0},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':2},{'color':'BLUE','id':2},{'color':'YELLOW','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':0},{'color':'YELLOW','id':1},{'color':'WHITE','id':1},{'color':'GREEN','id':0},{'color':'BLUE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'WHITE','id':0},{'color':'GREEN','id':0},{'color':'PURPLE','id':1},{'color':'YELLOW','id':2},{'color':'GREEN','id':0},{'color':'CYAN','id':1},{'color':'GREEN','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':0},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'PURPLE','id':0},{'color':'GREEN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':1},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'GREEN','id':1},{'color':'GREEN','id':0},{'color':'CYAN','id':2},{'color':'BLUE','id':1},{'color':'WHITE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':1}],'board':{'numberOfUsableTiles':29,'numberOfColumns':9,'numberOfRows':9,'tiles':[[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,null,{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,{'color':'YELLOW','id':0},{'color':'GREEN','id':1},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'CYAN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':0},{'id':0}],[{'id':0},null,{'color':'CYAN','id':2},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'CYAN','id':2},{'color':'PURPLE','id':0},{'id':0}],[{'id':0},{'color':'CYAN','id':1},{'color':'YELLOW','id':1},{'color':'BLUE','id':0},{'color':'WHITE','id':2},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'color':'WHITE','id':2},{'color':'WHITE','id':1},{'color':'YELLOW','id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'color':'PURPLE','id':0},{'color':'PURPLE','id':2},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}]]},'commonGoals':[{'numberOfPatternRepetitionsRequired':1,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':9},{'value':4,'playerID':0,'commonGoalID':9}],'id':9},{'direction':'VERTICAL','maxEqualsTiles':3,'numberOfPatternRepetitionsRequired':3,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':5},{'value':4,'playerID':0,'commonGoalID':5}],'id':5}]}]");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.controller.restoreGameForPlayer(null, "Andrea", gamesPath);
        assertEquals(this.state.controller.getModel().getPlayers().size(), 0);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that transformation of state into enum is ON_GOING")
    public void state_as_enum_value_is_on_going() {
        assertEquals(OnGoingState.toEnum(), GameState.ON_GOING);
    }
}
