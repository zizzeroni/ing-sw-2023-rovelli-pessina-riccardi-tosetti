package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreationStateTest {

    GameController controller;
    CreationState state;

    @BeforeEach
    public void resetCreationState() {
        controller = new GameController(new Game());
        state = new CreationState(controller);
    }

    @Test
    @DisplayName("Test that you can't extract the same common goal twice")
    public void common_goals_cannot_be_the_same() {
        CommonGoal newCommonGoal;
        while (this.controller.getModel().getCommonGoals().size() < 12) {
            try {
                newCommonGoal = this.state.getRandomCommonGoalSubclassInstance();
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
        this.state.changeTurn();
    }

    @Test
    @DisplayName("Test that insertUserInputInto model method does  nothing ")
    public void user_input_into_model_method_does_nothing_in_creation_test() {
        this.state.insertUserInputIntoModel(new Choice());
    }

    @Test
    @DisplayName("Test that add player method adds a single player with a personal goal different from the others and the given nickname")
    public void add_player_method_adds_single_player_with_common_goal_and_nickname() {
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
        this.state.addPlayer("Francesco");
        this.state.addPlayer("Luca");
        this.state.addPlayer("Alessandro");
        this.state.startGame();

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
        this.state.addPlayer("Francesco");
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
    @DisplayName("Test that number of players cannot be set below 2")
    public void number_of_players_cannot_be_set_below_2() {
        this.state.chooseNumberOfPlayerInTheGame(1);
        assertNotEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 1);
    }

    @Test
    @DisplayName("Test that number of players cannot be set over 4")
    public void number_of_players_cannot_be_set_over_4() {
        this.state.chooseNumberOfPlayerInTheGame(5);
        assertNotEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 5);
    }

    @Test
    @DisplayName("Test that current number of players cannot exceed available number of players")
    public void available_players_cannot_exceed_number_of_players_that_can_play() {
        this.state.addPlayer("Francesco");
        this.state.addPlayer("Luca");
        this.state.addPlayer("Alessandro");
        this.state.addPlayer("Andrea");
        this.state.chooseNumberOfPlayerInTheGame(3);
        assertNotEquals(this.controller.getModel().getNumberOfPlayersToStartGame(), 3);
    }

    @Test
    @DisplayName("Test restoring a game for a player")
    public void restore_a_game_for_a_player() {
        this.state.restoreGameForPlayer("Andrea");
        assertEquals(this.state.controller.getModel().getPlayers().size(), 2);
    }

    @Test
    @DisplayName("Test that transformation of state into enum is In_Creation")
    public void state_as_enum_value_is_in_creation() {
       assertEquals(CreationState.toEnum(), GameState.IN_CREATION);
    }
}
