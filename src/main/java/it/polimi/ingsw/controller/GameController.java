package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class implements the ViewListener, representing the actual {@code GameController}.
 * The Controller is used as an intermediary between the Server
 * and the Client in order to evaluate and perform
 * any of the {@code Player} actions. <p>
 * It provides a series of methods to reference the current
 * state of the {@code Game} (such as IN_CREATION, ON_GOING, FINISHING, ...) and
 * some other methods used for turn and chat management, input insertion, retrieving
 * goals information... linked to other primary game functions.
 *
 * @see Player
 * @see Game
 * @see GameController#state
 */
public class GameController implements ViewListener {
    private final Game model;
    private ControllerState state;
    private List<PersonalGoal> personalGoalsDeck;
    private List<JsonBoardPattern> boardPatterns;
    private final Random randomizer = new Random();

    /**
     *
     *
     * @param model
     */
    public GameController(Game model) {
        this.model = model;
        this.state = new CreationState(this);
        this.personalGoalsDeck = new ArrayList<>();
        this.boardPatterns = new ArrayList<>();

        Gson gson = new Gson();
        Reader reader;
        try {
            reader = Files.newBufferedReader(Paths.get("src/main/resources/storage/patterns/personal-goals.json"));
            this.personalGoalsDeck = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {
            }.getType());
            reader.close();

            reader = Files.newBufferedReader(Paths.get("src/main/resources/storage/patterns/boards.json"));
            this.boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {
            }.getType());
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * A setter employed to change the current state of the {@Game}
     *
     * @param state is the current state to be setted.
     *
     * @see GameState
     */
    public void changeState(ControllerState state) {
        this.state = state;
    }

    /**
     * A getter which returns the current state of the {@code Game}.
     *
     * @return the game's current state.
     *
     * @see Game
     */
    public ControllerState getState() {
        return state;
    }

    /**
     * Change the turn in the context of the present {@code State}.
     */
    @Override
    public void changeTurn() {
        state.changeTurn();
    }

    /**
     * Allows the active {@code Player} to make his {@code Choice}
     * in the context of input selection ({@code Tile}s, their position on the {@code Board},... ), through
     * the controller linked to the actual state.
     *
     * @param playerChoice the {@code Choice} made by the {@code Player} in the current selection.
     *
     * @see Player
     * @see Choice
     * @see ControllerState#insertUserInputIntoModel(Choice)
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        state.insertUserInputIntoModel(playerChoice);
    }

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender the {@code Player} sending the message.
     * @param content the text of the message being sent.
     *
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        state.sendPrivateMessage(receiver, sender, content);
    }

    /**
     * This method is used to stream a message in broadcast mode.
     * All the players will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
     * the {@code nickname} of the sending {@code Player} and its message type to {@code BROADCAST}.
     *
     * @param sender the {@code Player} sending the message.
     * @param content the text of the message being sent.
     *
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendBroadcastMessage(String sender, String content) {
        state.sendBroadcastMessage(sender, content);
    }

    /**
     * This method is used to add a {@code Player} to the current {@code Game}
     * through the nickname he has chosen during game creation and to assign a player a
     * randomly chosen {@code PersonalGoal}. In order to provide the goal it as to
     * access the GameController to get the number of persona goals for each player.
     * <p>
     * All the added players are characterized by {@code Bookshelf},
     * {@code PersonalGoal} and an array of {@code ScoreTile} elements.
     * <p>
     * The method also sets the connection state of any given {@code Player} to {@code true}.
     *
     * @param nickname is the reference for the name of the {@code Player} being added.
     *
     * @see PersonalGoal
     * @see GameController#getNumberOfPersonalGoals()
     */
    @Override
    public void addPlayer(String nickname) {
        state.addPlayer(nickname);
    }

    /**
     * Method to implement the selection of the number of players for the {@code Game}.
     *
     * @param chosenNumberOfPlayers identifies the number of players present
     *                              in the lobby during the game creation.
     *
     * @see Game
     * @see Game#getPlayers()
     *
     */
    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        state.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    /**
     * The method starts verifying  if the {@code Game} creation has occurred properly,
     * confronting the number of active players registered during the previous phase with
     * that stored in the {@code Model}.
     * Then, it proceeds to adjust the {@code Board} and to draw a list of Tiles.
     * Finally, it initializes the {@code ScoreTile} list for each active {@code Player},
     * (necessary in order to replace them later if a player complete a {@code CommonGoal}).
     *
     * @see Game
     * @see Player
     * @see ScoreTile
     * @see CommonGoal
     * @see Game#getNumberOfPlayersToStartGame()
     * @see Game#getPlayers()
     * @see Board#setPattern(JsonBoardPattern)
     * @see Board#numberOfTilesToRefill()
     */
    @Override
    public void startGame() {
        state.startGame();
    }

    /** Disconnects the selected {@code Player} from the {@code Game}
     * by changing his connectivity state.
     * (only possible when the {@code Game} has already started).
     *
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     *
     * @see Player
     * @see Game
     * @see Player#setConnected(boolean)
     */
    @Override
    public void disconnectPlayer(String nickname) {
        System.out.println("Giocatori prima del disconnect:" + this.model.getPlayers().stream().map(Player::getNickname).toList() + ",valore disconnected:" + this.model.getPlayers().stream().map(Player::isConnected).toList());
        state.disconnectPlayer(nickname);
        System.out.println("Giocatori dopo del disconnect:" + this.model.getPlayers().stream().map(Player::getNickname).toList() + ",valore disconnected:" + this.model.getPlayers().stream().map(Player::isConnected).toList());
    }


    //------------------------------------UTILITY METHODS------------------------------------
    /**
     * Getter used to retrieve the {@code Game} model.
     *
     * @return the model of the game created.
     *
     * @see Game
     */
    public Game getModel() {
        return this.model;
    }

    /**
     * Getter used to retrieve the number of {@code Player}s in the {@code Game}.
     *
     * @return the number of active players for the current game.
     *
     * @see Player
     * @see Game
     */
    public int getNumberOfPlayersCurrentlyInGame() {
        return this.model.getPlayers().size();
    }

    /**
     * Getter used to access the {@code PersonalGoal} assigned to a {@code Player}.
     *
     *
     * @param index is the identifier of the goal.
     * @return the player's indexed personalGoal.
     *
     * @see PersonalGoal
     */
    public PersonalGoal getPersonalGoal(int index) {
        Collections.shuffle(personalGoalsDeck);
        return personalGoalsDeck.remove(index);
    }

    /**
     * This method is used to add a {@code PersonalGoal} to the list of the {@code Player}'s possible
     * personal goals ({@code personalGoalsDeck}).
     *
     * @param personalGoal is the goal being added to the deck.
     *
     * @see PersonalGoal
     * @see GameController#personalGoalsDeck
     */
    public void addPersonalGoal(PersonalGoal personalGoal) {
        personalGoalsDeck.add(personalGoal);
    }

    /**
     * Getter to access the size of the deck of personal goals available.
     *
     * @return the size of the {@code personalGoalsDeck}
     *
     * @see PersonalGoal
     * @see GameController#personalGoalsDeck
     */
    public int getNumberOfPersonalGoals() {
        return personalGoalsDeck.size();
    }

    /**
     * Getter to access the {@code Board}'s patterns of tiles (stored in a json file).
     *
     * @return the list of possible identified patterns
     *
     * @see GameController#boardPatterns
     */
    public List<JsonBoardPattern> getBoardPatterns() {
        return this.boardPatterns;
    }

    /**
     * This method is used to access the randomizer used to shuffle the {@code personalGoalsDeck}.
     *
     * @return the randomizer used for shuffling.
     *
     * @see GameController#personalGoalsDeck
     */
    public Random getRandomizer() {
        return randomizer;
    }
}
