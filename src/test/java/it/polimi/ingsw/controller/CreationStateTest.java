package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.utils.GameModelDeserializer;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreationStateTest {

    GameController controller;
    CreationState state;
    String gamesPath = "src/test/resources/storage/games.json";
    String gamesPathBackup = "src/test/resources/storage/games-bkp.json";

    @BeforeEach
    public void resetCreationState() {
        controller = new GameController(new Game());
        state = new CreationState(controller);
        this.controller.changeState(state);
    }

    @Test
    @DisplayName("Test that you can't extract the same common goal twice")
    public void common_goals_cannot_be_the_same() {
        CommonGoal newCommonGoal;
        while (this.controller.getModel().getCommonGoals().size() < OptionsValues.NUMBER_OF_COMMON_GOAL_CARDS) {
            try {
                newCommonGoal = this.state.getRandomCommonGoalSubclassInstance(OptionsValues.NUMBER_OF_COMMON_GOAL_CARDS);
                if (!this.controller.getModel().getCommonGoals().contains(newCommonGoal)) {
                    this.controller.getModel().getCommonGoals().add(newCommonGoal);
                }
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        }

        assertTrue(this.controller.getModel().getCommonGoals().stream().allMatch(new HashSet<>()::add));
    }

    @Test
    @DisplayName("Test that you trying to get not defined common goals throws an exception")
    public void getting_not_defined_common_goals_throws_an_exception() {
        CommonGoal newCommonGoal;
        boolean errorHasBeenThrowed = false;
        while (this.controller.getModel().getCommonGoals().size() < OptionsValues.NUMBER_OF_COMMON_GOAL_CARDS + 1 && !errorHasBeenThrowed) {
            try {
                newCommonGoal = this.state.getRandomCommonGoalSubclassInstance(OptionsValues.NUMBER_OF_COMMON_GOAL_CARDS + 10000);
                if (!this.controller.getModel().getCommonGoals().contains(newCommonGoal)) {
                    this.controller.getModel().getCommonGoals().add(newCommonGoal);
                }
            } catch (Exception exception) {
                errorHasBeenThrowed = true;
                String expectedMessage = "This class does not exists";
                String actualMessage = exception.getMessage();

                assertEquals(actualMessage, expectedMessage);
            }
        }

        if (!errorHasBeenThrowed) {
            fail("Exception has not been thrown");
        }
    }


    @Test
    @DisplayName("Test that private messages aren't sent to other players ")
    public void private_messages_are_not_visible_to_other_players() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Francesco", true)));
        this.state.sendPrivateMessage("Luca", "Andrea", "ciao");

        Message senderPrivateMessage = this.controller.getModel().getPlayers().get(0).getChat().get(0);
        Message receiverPrivateMessage = this.controller.getModel().getPlayers().get(1).getChat().get(0);
        List<Message> otherPlayerChat = this.controller.getModel().getPlayers().get(2).getChat();

        assertEquals(senderPrivateMessage.messageType(), MessageType.PRIVATE);
        assertEquals(receiverPrivateMessage.messageType(), MessageType.PRIVATE);
        assertEquals(senderPrivateMessage.content(), "ciao");
        assertEquals(receiverPrivateMessage.content(), "ciao");
        assertEquals(otherPlayerChat.size(), 0);
    }

    @Test
    @DisplayName("Test that broadcast messages are sent to all players ")
    public void broadcast_messages_are_sent_to_all_players() {
        this.controller.getModel().setPlayers(Arrays.asList(new Player("Andrea", true), new Player("Luca", true), new Player("Francesco", true)));
        this.state.sendBroadcastMessage("Andrea", "ciao");

        for (Player player : this.controller.getModel().getPlayers()) {
            Message playerLastMessage = player.getChat().get(0);
            assertEquals(playerLastMessage.messageType(), MessageType.BROADCAST);
            assertEquals(playerLastMessage.content(), "ciao");
        }
    }

    @Test
    @DisplayName("Test that changeTurn method does  nothing ")
    public void change_turn_method_does_nothing_in_creation_test() {
        this.state.changeTurn(gamesPath, gamesPathBackup);
    }

    @Test
    @DisplayName("Test that insertUserInputInto model method does  nothing ")
    public void user_input_into_model_method_does_nothing_in_creation_test() {
        this.state.insertUserInputIntoModel(new Choice());
    }

    @Test
    @DisplayName("Test that add player method adds a single player with a personal goal different from the others and the given nickname")
    public void add_player_method_adds_single_player_with_common_goal_and_nickname() {
        try {
            this.state.addPlayer("Andrea");
            assertEquals(this.controller.getModel().getPlayers().size(), 1);
            assertEquals(this.controller.getModel().getPlayers().get(0).getNickname(), "Andrea");

            this.state.addPlayer("Alessandro");
            assertEquals(this.controller.getModel().getPlayers().size(), 2);
            assertEquals(this.controller.getModel().getPlayers().get(1).getNickname(), "Alessandro");

            this.state.addPlayer("Francesco");
            assertEquals(this.controller.getModel().getPlayers().size(), 3);
            assertEquals(this.controller.getModel().getPlayers().get(2).getNickname(), "Francesco");

            this.state.addPlayer("Luca");
            assertEquals(this.controller.getModel().getPlayers().size(), 4);
            assertEquals(this.controller.getModel().getPlayers().get(3).getNickname(), "Luca");

        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }

        assertTrue(this.controller.getModel().getPlayers().stream().map(Player::getPersonalGoal).allMatch(new HashSet<>()::add));
    }

    @Test
    @DisplayName("Test that resuming the game changes the state of the game to the same as the controller")
    public void change_state_when_resuming_game() {
        this.state.tryToResumeGame();
        assertEquals(this.state.controller.getModel().getGameState(), this.controller.getModel().getGameState());
    }

    @Test
    @DisplayName("Test that starting the game set game state on OnGoing, initializing board, tiles and common goals only if number of players is correct")
    public void starting_the_game_sets_state_board_tiles_and_common_goals() {
        this.controller.getModel().setNumberOfPlayersToStartGame(3);
        try {
            this.state.addPlayer("Francesco");
            this.state.addPlayer("Luca");
            this.state.addPlayer("Alessandro");

        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }

        this.state.startGame(OptionsValues.NUMBER_OF_COMMON_GOAL_CARDS);

        assertEquals(this.controller.getNumberOfPlayersCurrentlyInGame(), 3);
        assertEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 3);
        assertEquals(this.state.controller.getModel().getGameState(), GameState.ON_GOING);
        assertEquals(this.controller.getModel().getGameState(), GameState.ON_GOING);
        assertNotNull(this.controller.getModel().getBoard());
        assertNotNull(this.controller.getModel().getBag());
        assertNotNull(this.controller.getModel().getCommonGoals());
        this.controller.getModel().getPlayers().forEach(player -> assertNotNull(player.getScoreTiles()));
        this.controller.getModel().getPlayers().forEach(player -> assertEquals(player.getScoreTiles().size(), this.controller.getModel().getCommonGoals().size() + 1));
    }

    @Test
    @DisplayName("Test that disconnecting a player restore his personal goal in available common goals and removes him from available players")
    public void disconnet_player_removes_the_player_and_restore_the_personal_goal() {
        try {
            this.state.addPlayer("Francesco");
        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }

        PersonalGoal addedPersonalGoal = new PersonalGoal(this.controller.getModel().getPlayers().get(0).getPersonalGoal());
        assertFalse(this.controller.getPersonalGoalsDeck().contains(addedPersonalGoal));
        this.state.disconnectPlayer("Francesco");

        assertTrue(this.controller.getPersonalGoalsDeck().contains(addedPersonalGoal));
        assertEquals(this.controller.getModel().getPlayers().size(), 0);
    }

    @Test
    @DisplayName("Test that you can set number of players to play within 2 to 4 range")
    public void number_of_players_is_settable_between_2_and_4() {
        this.state.chooseNumberOfPlayerInTheGame(2);
        assertEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 2);
        this.state.chooseNumberOfPlayerInTheGame(3);
        assertEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 3);
        this.state.chooseNumberOfPlayerInTheGame(4);
        assertEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 4);
    }

    @Test
    @DisplayName("Test that number of players cannot be set over the maximum defined and throws Lobby is full exception")
    public void number_of_players_cannot_be_set_over_the_maximum_defined_with_relative_exception_thrown() {

        int i = 0;
        while (i < OptionsValues.MAX_NUMBER_OF_PLAYERS_TO_START_GAME) {
            try {
                this.state.addPlayer(String.valueOf(i));

            } catch (LobbyIsFullException e) {
                throw new RuntimeException(e);
            }
            i++;
        }

        Exception exception = assertThrows(LobbyIsFullException.class, () -> this.state.addPlayer("Andrea"));

        String expectedMessage = "Cannot access a game: Lobby is full";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
    }

    @Test
    @DisplayName("Test that given number of players must be a value between minimum and maximum number of players")
    public void given_number_of_players_is_between_its_minimum_and_maximum() {
        Exception exception = assertThrows(WrongInputDataException.class, () -> this.state.checkExceedingPlayer(OptionsValues.MIN_SELECTABLE_NUMBER_OF_PLAYERS - 1));

        String expectedMessage = "Unexpected value for number of lobby's players";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        assertNotEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 3);

        exception = assertThrows(WrongInputDataException.class, () -> this.state.checkExceedingPlayer(OptionsValues.MAX_SELECTABLE_NUMBER_OF_PLAYERS + 1));

        expectedMessage = "Unexpected value for number of lobby's players";
        actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        assertNotEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 3);
    }

    @Test
    @DisplayName("Test that current number of players cannot exceed available number of players")
    public void available_players_cannot_exceed_number_of_players_that_can_play() {
        try {
            this.state.addPlayer("Francesco");
            this.state.addPlayer("Luca");
            this.state.addPlayer("Alessandro");
            this.state.addPlayer("Andrea");
        } catch (LobbyIsFullException e) {
            throw new RuntimeException(e);
        }

        Exception exception = assertThrows(ExcessOfPlayersException.class, () -> this.state.checkExceedingPlayer(3));

        String expectedMessage = "The creator of the lobby has chosen a number of players smaller than the number of connected one";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        assertNotEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 3);
    }

    @Test
    @DisplayName("Test restoring a game for a player")
    public void restore_a_game_for_a_player() {

        
        this.controller.getModel().createGameFileIfNotExist(this.gamesPath);

        PrintWriter writer;
        try {
            writer = new PrintWriter(this.gamesPath);

            writer.println("[{'gameState':'ON_GOING','numberOfPlayersToStartGame':2,'activePlayerIndex':0,'players':[{'nickname':'Andrea','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,{'color':'CYAN','id':0},null,{'color':'GREEN','id':0}],[null,null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,null,null],[null,{'color':'YELLOW','id':0},null,{'color':'BLUE','id':0},null],[{'color':'PURPLE','id':0},null,null,null,null]],'id':6},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},{'color':'PURPLE','id':0},null,null,null]]},'chat':[]},{'nickname':'Toso','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,null,null,{'color':'BLUE','id':0}],[null,{'color':'GREEN','id':0},null,null,null],[null,null,{'color':'CYAN','id':0},null,null],[{'color':'PURPLE','id':0},null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,{'color':'YELLOW','id':0},null]],'id':8},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},null,null,null,null],[{'color':'YELLOW','id':0},null,null,null,null]]},'chat':[]}],'bag':[{'color':'YELLOW','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':2},{'color':'PURPLE','id':1},{'color':'CYAN','id':0},{'color':'CYAN','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':0},{'color':'WHITE','id':1},{'color':'GREEN','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':1},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'WHITE','id':0},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':1},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'GREEN','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':2},{'color':'CYAN','id':2},{'color':'YELLOW','id':1},{'color':'BLUE','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':0},{'color':'PURPLE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':2},{'color':'BLUE','id':1},{'color':'CYAN','id':0},{'color':'GREEN','id':1},{'color':'YELLOW','id':1},{'color':'YELLOW','id':0},{'color':'GREEN','id':2},{'color':'GREEN','id':0},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':2},{'color':'BLUE','id':2},{'color':'YELLOW','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':0},{'color':'YELLOW','id':1},{'color':'WHITE','id':1},{'color':'GREEN','id':0},{'color':'BLUE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'WHITE','id':0},{'color':'GREEN','id':0},{'color':'PURPLE','id':1},{'color':'YELLOW','id':2},{'color':'GREEN','id':0},{'color':'CYAN','id':1},{'color':'GREEN','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':0},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'PURPLE','id':0},{'color':'GREEN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':1},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'GREEN','id':1},{'color':'GREEN','id':0},{'color':'CYAN','id':2},{'color':'BLUE','id':1},{'color':'WHITE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':1}],'board':{'numberOfUsableTiles':29,'numberOfColumns':9,'numberOfRows':9,'tiles':[[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,null,{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,{'color':'YELLOW','id':0},{'color':'GREEN','id':1},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'CYAN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':0},{'id':0}],[{'id':0},null,{'color':'CYAN','id':2},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'CYAN','id':2},{'color':'PURPLE','id':0},{'id':0}],[{'id':0},{'color':'CYAN','id':1},{'color':'YELLOW','id':1},{'color':'BLUE','id':0},{'color':'WHITE','id':2},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'color':'WHITE','id':2},{'color':'WHITE','id':1},{'color':'YELLOW','id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'color':'PURPLE','id':0},{'color':'PURPLE','id':2},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}]]},'commonGoals':[{'numberOfPatternRepetitionsRequired':1,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':9},{'value':4,'playerID':0,'commonGoalID':9}],'id':9},{'direction':'VERTICAL','maxEqualsTiles':3,'numberOfPatternRepetitionsRequired':3,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':5},{'value':4,'playerID':0,'commonGoalID':5}],'id':5}]}]");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.state.restoreGameForPlayer(null, "Andrea", this.gamesPath);
        assertEquals(this.state.controller.getModel().getPlayers().size(), 2);
    }

    @Test
    @DisplayName("Test that you can't restore any game if there are no games available")
    public void cannot_restore_a_game_if_any_are_present() {

        this.controller.getModel().createGameFileIfNotExist(this.gamesPath);

        PrintWriter writer;
        try {
            writer = new PrintWriter(this.gamesPath);

            writer.println("[]");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Exception exception = assertThrows(RuntimeException.class, () -> this.state.restoreGameForPlayer(null, "Andrea", this.gamesPath));

        String expectedMessage = "There aren't available games to restore!";
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        assertEquals(this.state.controller.getModel().getPlayers().size(), 0);
    }

    @Test
    @DisplayName("Test that you can't restore any game if there are no games available for the given nickname")
    public void cannot_restore_a_game_if_any_are_present_for_the_given_nickname() {

        this.controller.getModel().createGameFileIfNotExist(this.gamesPath);

        PrintWriter writer;
        try {
            writer = new PrintWriter(this.gamesPath);

            writer.println("[{'gameState':'ON_GOING','numberOfPlayersToStartGame':2,'activePlayerIndex':0,'players':[{'nickname':'Andrea','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,{'color':'CYAN','id':0},null,{'color':'GREEN','id':0}],[null,null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,null,null],[null,{'color':'YELLOW','id':0},null,{'color':'BLUE','id':0},null],[{'color':'PURPLE','id':0},null,null,null,null]],'id':6},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},{'color':'PURPLE','id':0},null,null,null]]},'chat':[]},{'nickname':'Toso','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,null,null,{'color':'BLUE','id':0}],[null,{'color':'GREEN','id':0},null,null,null],[null,null,{'color':'CYAN','id':0},null,null],[{'color':'PURPLE','id':0},null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,{'color':'YELLOW','id':0},null]],'id':8},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},null,null,null,null],[{'color':'YELLOW','id':0},null,null,null,null]]},'chat':[]}],'bag':[{'color':'YELLOW','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':2},{'color':'PURPLE','id':1},{'color':'CYAN','id':0},{'color':'CYAN','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':0},{'color':'WHITE','id':1},{'color':'GREEN','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':1},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'WHITE','id':0},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':1},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'GREEN','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':2},{'color':'CYAN','id':2},{'color':'YELLOW','id':1},{'color':'BLUE','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':0},{'color':'PURPLE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':2},{'color':'BLUE','id':1},{'color':'CYAN','id':0},{'color':'GREEN','id':1},{'color':'YELLOW','id':1},{'color':'YELLOW','id':0},{'color':'GREEN','id':2},{'color':'GREEN','id':0},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':2},{'color':'BLUE','id':2},{'color':'YELLOW','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':0},{'color':'YELLOW','id':1},{'color':'WHITE','id':1},{'color':'GREEN','id':0},{'color':'BLUE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'WHITE','id':0},{'color':'GREEN','id':0},{'color':'PURPLE','id':1},{'color':'YELLOW','id':2},{'color':'GREEN','id':0},{'color':'CYAN','id':1},{'color':'GREEN','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':0},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'PURPLE','id':0},{'color':'GREEN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':1},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'GREEN','id':1},{'color':'GREEN','id':0},{'color':'CYAN','id':2},{'color':'BLUE','id':1},{'color':'WHITE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':1}],'board':{'numberOfUsableTiles':29,'numberOfColumns':9,'numberOfRows':9,'tiles':[[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,null,{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,{'color':'YELLOW','id':0},{'color':'GREEN','id':1},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'CYAN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':0},{'id':0}],[{'id':0},null,{'color':'CYAN','id':2},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'CYAN','id':2},{'color':'PURPLE','id':0},{'id':0}],[{'id':0},{'color':'CYAN','id':1},{'color':'YELLOW','id':1},{'color':'BLUE','id':0},{'color':'WHITE','id':2},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'color':'WHITE','id':2},{'color':'WHITE','id':1},{'color':'YELLOW','id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'color':'PURPLE','id':0},{'color':'PURPLE','id':2},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}]]},'commonGoals':[{'numberOfPatternRepetitionsRequired':1,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':9},{'value':4,'playerID':0,'commonGoalID':9}],'id':9},{'direction':'VERTICAL','maxEqualsTiles':3,'numberOfPatternRepetitionsRequired':3,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':5},{'value':4,'playerID':0,'commonGoalID':5}],'id':5}]}]");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String nickname = "Marco";
        Exception exception = assertThrows(RuntimeException.class, () -> this.state.restoreGameForPlayer(null, nickname, this.gamesPath));

        String expectedMessage = "There aren't available games to restore for player " + nickname;
        String actualMessage = exception.getMessage();

        assertEquals(actualMessage, expectedMessage);
        assertEquals(this.state.controller.getModel().getPlayers().size(), 0);
    }

    @Test
    @DisplayName("Test that transformation of state into enum is In_Creation")
    public void state_as_enum_value_is_in_creation() {
        assertEquals(CreationState.toEnum(), GameState.IN_CREATION);
    }
}
