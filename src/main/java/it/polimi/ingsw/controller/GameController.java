package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.utils.GameModelDeserializer;
import it.polimi.ingsw.utils.OptionsValues;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * This class implements the ViewListener, representing the actual {@code GameController}.
 * The Controller is used as an intermediary between the Server
 * and the model in order to evaluate and perform
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
public class GameController {
    //private ModelListener listener;
    private Game model;
    private ControllerState state;
    private List<PersonalGoal> personalGoalsDeck;
    private List<JsonBoardPattern> boardPatterns;
    private final Random randomizer = new Random();

    /**
     * Class constructor.
     * Used to associate the game's controller with the game's model.
     *
     * @param model is the model class used to represent the main elements of the active game.
     * @see Game
     */
    public GameController(Game model) {
        this.model = model;
        this.state = new CreationState(this);
        this.personalGoalsDeck = new ArrayList<>();
        this.boardPatterns = new ArrayList<>();

        Gson gson = new Gson();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(OptionsValues.PERSONAL_GOALS_STORAGE_BACKUP_DEFAULT_PATH);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            this.personalGoalsDeck = gson.fromJson(reader, new TypeToken<ArrayList<PersonalGoal>>() {
            }.getType());
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to load personal-goals.json", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load personal-goals.json", ex);

        }

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(OptionsValues.BOARDS_STORAGE_DEFAULT_PATH);
             Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            this.boardPatterns = gson.fromJson(reader, new TypeToken<ArrayList<JsonBoardPattern>>() {
            }.getType());
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to load boards.json", ex);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to load personal-goals.json", ex);
        }
    }

    /**
     * A setter employed to change the current state of the {@code Game}
     *
     * @param state is the current state to be set.
     * @see GameState
     */
    public void changeState(ControllerState state) {
        this.state = state;
    }

    /**
     * A getter which returns the current state of the {@code Game}.
     *
     * @return the game's current state.
     * @see Game
     */
    public ControllerState getState() {
        return state;
    }

    /**
     * Change the turn in the context of the present {@code State}.
     *
     * @param gamesStoragePath       path in which are stored the games
     * @param gamesStoragePathBackup backup path in which are stored the games
     */
    public void changeTurn(String gamesStoragePath, String gamesStoragePathBackup) {
        state.changeTurn(gamesStoragePath, gamesStoragePathBackup);
    }

    /**
     * Allows the active {@code Player} to make his {@code Choice}
     * in the context of input selection ({@code Tile}s, their position on the {@code Board},... ), through
     * the controller linked to the actual state.
     *
     * @param playerChoice the {@code Choice} made by the {@code Player} in the current selection.
     * @throws WrongInputDataException when input data is not valid.
     * @see Player
     * @see Choice
     * @see ControllerState#insertUserInputIntoModel(Choice)
     */
    public void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException {
        state.insertUserInputIntoModel(playerChoice);
    }

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message.
     * It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender   the {@code Player} sending the message.
     * @param content  the text of the message being sent.
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    public void sendPrivateMessage(String receiver, String sender, String content) {
        state.sendPrivateMessage(receiver, sender, content);
    }

    /**
     * This method is used to stream a message in broadcast mode.
     * All the players will be able to read the message.
     * It builds a new object message at each call, setting
     * the {@code nickname} of the sending {@code Player} and its message type to {@code BROADCAST}.
     *
     * @param sender  the {@code Player} sending the message.
     * @param content the text of the message being sent.
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
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
     * @throws LobbyIsFullException when lobby is full of players or the given nickname is not in the allowed ones.
     * @see PersonalGoal
     * @see GameController#getNumberOfPersonalGoals()
     */
    public void addPlayer(String nickname) throws LobbyIsFullException {
        state.addPlayer(nickname);
    }


    /**
     * Signals that an attempt of resuming the game is in progress,
     * modifying the related state.
     *
     * @see Game
     */
    public void tryToResumeGame() {
        state.tryToResumeGame();
    }

    /**
     * Method to implement the selection of the number of players for the {@code Game}.
     *
     * @param chosenNumberOfPlayers identifies the number of players present
     *                              in the lobby during the game creation.
     * @see Game
     * @see Game#getPlayers()
     */
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
    public void startGame() {
        state.startGame(OptionsValues.NUMBER_OF_COMMON_GOAL_CARDS);
    }

    /**
     * Checks if there are some player in excess within the game lobby that shouldn't be connected.
     *
     * @param chosenNumberOfPlayers number of player chosen by the first player
     * @throws ExcessOfPlayersException if there is an excess of player in the lobby when starting
     * @throws WrongInputDataException  when input data is not valid.
     * @see CreationState#checkExceedingPlayer(int)
     * @see FinishingState#checkExceedingPlayer(int)
     * @see InPauseState#checkExceedingPlayer(int)
     * @see OnGoingState#checkExceedingPlayer(int)
     */
    public void checkExceedingPlayer(int chosenNumberOfPlayers) throws ExcessOfPlayersException, WrongInputDataException {
        state.checkExceedingPlayer(chosenNumberOfPlayers);
    }

    /**
     * Disconnects the selected {@code Player} from the {@code Game}
     * by changing his connectivity state.
     * (only possible when the {@code Game} has already started).
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @see Player
     * @see Game
     * @see Player#setConnected(boolean)
     */
    public void disconnectPlayer(String nickname) {
        System.out.println("Players before disconnect:" + this.model.getPlayers().stream().map(Player::getNickname).toList() + ",valore disconnected:" + this.model.getPlayers().stream().map(Player::isConnected).toList());
        state.disconnectPlayer(nickname);
        System.out.println("Players after disconnect:" + this.model.getPlayers().stream().map(Player::getNickname).toList() + ",valore disconnected:" + this.model.getPlayers().stream().map(Player::isConnected).toList());
    }


    //------------------------------------UTILITY METHODS------------------------------------

    /**
     * Getter used to retrieve the {@code Game} model.
     *
     * @return the model of the game created.
     * @see Game
     */
    public Game getModel() {
        return this.model;
    }

    /**
     * Setter used to set the model
     *
     * @param model the new model
     */
    public void setModel(Game model) {
        this.model = model;
    }

    /**
     * Getter used to retrieve the number of {@code Player}s in the {@code Game}.
     *
     * @return the number of active players for the current game.
     * @see Player
     * @see Game
     */
    public int getNumberOfPlayersCurrentlyInGame() {
        return this.model.getPlayers().size();
    }

    /**
     * Getter used to get a {@code PersonalGoal} from the personal goal deck.
     *
     * @param index is the identifier of the goal.
     * @return the player's indexed personalGoal.
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
     * @see PersonalGoal
     * @see GameController#personalGoalsDeck
     */
    public void addPersonalGoal(PersonalGoal personalGoal) {
        personalGoalsDeck.add(personalGoal);
    }

    /**
     * Getter used to get the personal goal deck
     *
     * @return the personal goal deck
     */
    public List<PersonalGoal> getPersonalGoalsDeck() {
        return personalGoalsDeck;
    }

    /**
     * Getter to access the size of the deck of personal goals available.
     *
     * @return the size of the {@code personalGoalsDeck}
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
     * @see GameController#boardPatterns
     */
    public List<JsonBoardPattern> getBoardPatterns() {
        return this.boardPatterns;
    }

    /**
     * This method is used to access the randomizer used to shuffle the {@code personalGoalsDeck}.
     *
     * @return the randomizer used for shuffling.
     * @see GameController#personalGoalsDeck
     */
    public Random getRandomizer() {
        return randomizer;
    }

    /**
     * Method to check if there are stored games for the given nickname.
     *
     * @param playerNickname nickname of the first player who joined the lobby
     * @param gamesPath      path of the file in which are stored the games
     * @return if there are stored games.
     */
    public boolean areThereStoredGamesForPlayer(String playerNickname, String gamesPath) {
        this.model.createGameFileIfNotExist(gamesPath);
        Game[] games = this.getStoredGamesFromJson(gamesPath);

        if (games == null || games.length == 0) return false;

        Game storedCurrentGame = this.getStoredGameForPlayer(playerNickname, games);

        return storedCurrentGame != null;
    }


    /**
     * Method to get all the stored games from the file.
     *
     * @param gamesStoragePath path of the file in which are stored the games
     * @return all stored games.
     */
    private Game[] getStoredGamesFromJson(String gamesStoragePath) {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Game.class, new GameModelDeserializer());
        Gson gson = gsonBuilder.create();
        Reader fileReader;
        Path source = Paths.get(gamesStoragePath);
        Game[] games;

        try {
            fileReader = Files.newBufferedReader(source);

            games = gson.fromJson(fileReader, Game[].class);
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return games;
    }

    /**
     * Method to get the stored game for the given nickname.
     *
     * @param playerNickname nickname of the player who requested the restore of the game
     * @param gamesAsArray   set of game retrieved from the file
     * @return the stored game.
     */
    private Game getStoredGameForPlayer(String playerNickname, Game[] gamesAsArray) {
        List<Game> games = Arrays.asList(gamesAsArray);

        //use hash set in filter to increase performance
        return games.stream()
                .filter(game -> new HashSet<>(
                        game.getPlayers().stream()
                                .map(Player::getNickname).toList())
                        .contains(playerNickname))
                .findFirst()
                .orElse(null);
    }

    /**
     * Restores the current game for the considered player.
     *
     * @param server           the server controlling the game's execution.
     * @param playerNickname   the given player's nickname.
     * @param gamesStoragePath path of the file in which are stored the games
     * @see Player
     * @see Game
     */
    public void restoreGameForPlayer(GameListener server, String playerNickname, String gamesStoragePath) {
        state.restoreGameForPlayer(server, playerNickname, gamesStoragePath);
    }
}
