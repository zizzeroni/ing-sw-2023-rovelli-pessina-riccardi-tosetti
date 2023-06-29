package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InPauseStateTest {

    GameController controller;
    InPauseState state;
    String gamesPath = "src/test/resources/storage/games.json";
    String gamesPathBackup = "src/test/resources/storage/games-bkp.json";

    /**
     * Test class
     */
    @BeforeEach
    public void resetFinishingState() {
        controller = new GameController(new Game());
        state = new InPauseState(controller);
        this.controller.changeState(state);
        this.controller.getModel().setGameState(GameState.PAUSED);
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
    @DisplayName("Test that timer can be stopped")
    public void timer_can_be_stopped() {
        try {
            synchronized (this) {
                this.wait(3000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        this.resume_game_after_player_reconnection();

        try {
            synchronized (this) {
                this.wait(OptionsValues.MILLISECOND_COUNTDOWN_VALUE + 1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertNotEquals(GameState.RESET_NEEDED, this.controller.getModel().getGameState());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that when timer's state goes off, the game ends")
    public void timer_goes_off_and_game_ends() {
        try {
            synchronized (this) {
                this.wait(OptionsValues.MILLISECOND_COUNTDOWN_VALUE + 1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertEquals(GameState.RESET_NEEDED, this.controller.getModel().getGameState());
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
    @DisplayName("Test that changeTurn method does nothing ")
    public void change_turn_method_does_nothing_in_pause_state() {
        this.state.changeTurn(gamesPath, gamesPathBackup);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that insertUserInputInto model method does nothing ")
    public void user_input_into_model_method_does_nothing_in_pause_state() {
        this.state.insertUserInputIntoModel(new Choice());
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
    @DisplayName("Test that resume a game with a number of players less or equal to the minimum for going in pause fails ")
    public void resume_game_with_less_player_than_required_fails() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", false), new Player("Luca", true)));
        this.controller.tryToResumeGame();
        assertEquals(this.state.controller.getModel().getGameState(), GameState.PAUSED);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that resume a game after player's reconnection")
    public void resume_game_after_player_reconnection() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", false), new Player("Francesco", false), new Player("Luca", true)));
        this.controller.getModel().setActivePlayerIndex(1);
        try {
            this.controller.addPlayer("Andrea");
        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }
        this.controller.tryToResumeGame();
        assertEquals(GameState.ON_GOING, this.controller.getModel().getGameState());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that resume a game after player's reconnection with last player as active")
    public void resume_game_after_player_reconnection_with_last_player_as_active() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", false), new Player("Francesco", true), new Player("Luca", false)));
        this.controller.getModel().setActivePlayerIndex(2);
        try {
            this.controller.addPlayer("Andrea");
        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }
        this.controller.tryToResumeGame();
        assertEquals(GameState.ON_GOING, this.controller.getModel().getGameState());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that startGame method does nothing in finishing state")
    public void start_game_method_does_nothing_in_finishing_state() {
        this.controller.startGame();
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that disconnecting the last player set his connection to false and set state to reset the server")
    public void disconnecting_last_player_set_connection_false_and_set_reset_server_state() {

        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", false), new Player("Luca", true)));

        this.controller.disconnectPlayer("Luca");

        assertFalse(this.controller.getModel().getPlayerFromNickname("Luca").isConnected());
        assertEquals(GameState.RESET_NEEDED, this.controller.getModel().getGameState());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that choosing the number of players does nothing in finishing state")
    public void choosing_number_of_players_does_nothing() {
        this.controller.chooseNumberOfPlayerInTheGame(2);
        assertEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 0);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that checkExceedingPlayer method does nothing in finishing state")
    public void check_exceeding_number_of_players_does_nothing() {
        assertDoesNotThrow(() -> this.controller.checkExceedingPlayer(OptionsValues.MIN_SELECTABLE_NUMBER_OF_PLAYERS - 1));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that restoreGameForPlayer method does nothing in finishing state")
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
    @DisplayName("Test that transformation of state into enum is FINISHING")
    public void state_as_enum_value_is_finishing() {
        assertEquals(GameState.PAUSED, InPauseState.toEnum());
    }
}
