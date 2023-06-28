package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameControllerTest {

    GameController controller;
    CreationState state;
    String gamesPath = "src/test/resources/storage/games.json";
    String gamesPathBackup = "src/test/resources/storage/games-bkp.json";

    @BeforeEach
    public void resetGameCOntroller() {
        controller = new GameController(new Game());
    }
    
    @Test
    @DisplayName("Test that default game controller constructor sets the state to the creation one")
    public void default_game_controller_constructor_sets_state_in_creation() {
        assertInstanceOf(CreationState.class, this.controller.getState());
    }

    @Test
    @DisplayName("Test that if there aren't any games stored the response is false")
    public void are_stored_games_for_player_return_false_when_no_games_are_stored() {

        PrintWriter writer;
        try {
            writer = new PrintWriter(this.gamesPath);

            writer.println("");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertFalse(this.controller.areThereStoredGamesForPlayer("Andrea", gamesPath));
    }

    @Test
    @DisplayName("Test that if there are some games stored for the given player the response is true")
    public void are_stored_games_for_player_return_true_when_games_are_stored_for_the_given_player() {

        PrintWriter writer;
        try {
            writer = new PrintWriter(this.gamesPath);

            writer.println("[{'gameState':'ON_GOING','numberOfPlayersToStartGame':2,'activePlayerIndex':0,'players':[{'nickname':'Andrea','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,{'color':'CYAN','id':0},null,{'color':'GREEN','id':0}],[null,null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,null,null],[null,{'color':'YELLOW','id':0},null,{'color':'BLUE','id':0},null],[{'color':'PURPLE','id':0},null,null,null,null]],'id':6},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},{'color':'PURPLE','id':0},null,null,null]]},'chat':[]},{'nickname':'Toso','connected':true,'personalGoal':{'numberOfColumns':5,'numberOfRows':6,'pattern':[[null,null,null,null,{'color':'BLUE','id':0}],[null,{'color':'GREEN','id':0},null,null,null],[null,null,{'color':'CYAN','id':0},null,null],[{'color':'PURPLE','id':0},null,null,null,null],[null,null,null,{'color':'WHITE','id':0},null],[null,null,null,{'color':'YELLOW','id':0},null]],'id':8},'scoreTiles':[{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1},{'value':0,'playerID':-1,'commonGoalID':-1}],'bookshelf':{'numberOfColumns':5,'numberOfRows':6,'pointsForEachGroup':{'3':2,'4':3,'5':5,'6':8},'tiles':[[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[null,null,null,null,null],[{'color':'BLUE','id':0},null,null,null,null],[{'color':'YELLOW','id':0},null,null,null,null]]},'chat':[]}],'bag':[{'color':'YELLOW','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':2},{'color':'PURPLE','id':1},{'color':'CYAN','id':0},{'color':'CYAN','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':0},{'color':'WHITE','id':1},{'color':'GREEN','id':2},{'color':'BLUE','id':2},{'color':'BLUE','id':1},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'WHITE','id':0},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':1},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'GREEN','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':2},{'color':'CYAN','id':2},{'color':'YELLOW','id':1},{'color':'BLUE','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'PURPLE','id':0},{'color':'PURPLE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':2},{'color':'BLUE','id':1},{'color':'CYAN','id':0},{'color':'GREEN','id':1},{'color':'YELLOW','id':1},{'color':'YELLOW','id':0},{'color':'GREEN','id':2},{'color':'GREEN','id':0},{'color':'PURPLE','id':0},{'color':'YELLOW','id':0},{'color':'BLUE','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':2},{'color':'BLUE','id':2},{'color':'YELLOW','id':2},{'color':'CYAN','id':1},{'color':'WHITE','id':1},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'CYAN','id':2},{'color':'CYAN','id':0},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'GREEN','id':2},{'color':'WHITE','id':1},{'color':'CYAN','id':1},{'color':'YELLOW','id':0},{'color':'PURPLE','id':1},{'color':'BLUE','id':0},{'color':'CYAN','id':0},{'color':'YELLOW','id':0},{'color':'YELLOW','id':1},{'color':'WHITE','id':1},{'color':'GREEN','id':0},{'color':'BLUE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'WHITE','id':0},{'color':'GREEN','id':0},{'color':'PURPLE','id':1},{'color':'YELLOW','id':2},{'color':'GREEN','id':0},{'color':'CYAN','id':1},{'color':'GREEN','id':0},{'color':'GREEN','id':0},{'color':'BLUE','id':0},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'PURPLE','id':0},{'color':'GREEN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':2},{'color':'YELLOW','id':1},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'GREEN','id':1},{'color':'GREEN','id':0},{'color':'CYAN','id':2},{'color':'BLUE','id':1},{'color':'WHITE','id':1},{'color':'YELLOW','id':2},{'color':'WHITE','id':1}],'board':{'numberOfUsableTiles':29,'numberOfColumns':9,'numberOfRows':9,'tiles':[[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,null,{'id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},null,{'color':'YELLOW','id':0},{'color':'GREEN','id':1},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'color':'YELLOW','id':2},{'color':'WHITE','id':0},{'color':'PURPLE','id':2},{'color':'CYAN','id':1},{'color':'BLUE','id':1},{'color':'WHITE','id':0},{'id':0}],[{'id':0},null,{'color':'CYAN','id':2},{'color':'PURPLE','id':2},{'color':'GREEN','id':2},{'color':'GREEN','id':1},{'color':'CYAN','id':2},{'color':'PURPLE','id':0},{'id':0}],[{'id':0},{'color':'CYAN','id':1},{'color':'YELLOW','id':1},{'color':'BLUE','id':0},{'color':'WHITE','id':2},{'color':'PURPLE','id':2},{'color':'CYAN','id':2},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'color':'WHITE','id':2},{'color':'WHITE','id':1},{'color':'YELLOW','id':0},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'color':'PURPLE','id':0},{'color':'PURPLE','id':2},{'id':0},{'id':0},{'id':0}],[{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0},{'id':0}]]},'commonGoals':[{'numberOfPatternRepetitionsRequired':1,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':9},{'value':4,'playerID':0,'commonGoalID':9}],'id':9},{'direction':'VERTICAL','maxEqualsTiles':3,'numberOfPatternRepetitionsRequired':3,'type':'INDIFFERENT','scoreTiles':[{'value':8,'playerID':0,'commonGoalID':5},{'value':4,'playerID':0,'commonGoalID':5}],'id':5}]}]");
            writer.flush();
            writer.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        assertTrue(this.controller.areThereStoredGamesForPlayer("Andrea", gamesPath));
    }
}
