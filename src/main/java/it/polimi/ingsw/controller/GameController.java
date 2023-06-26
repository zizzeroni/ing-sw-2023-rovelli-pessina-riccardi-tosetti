package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.utils.GameModelDeserializer;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class GameController {
    //private ModelListener listener;
    private Game model;
    private ControllerState state;
    private List<PersonalGoal> personalGoalsDeck;
    private List<JsonBoardPattern> boardPatterns;
    private final Random randomizer = new Random();

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

    /*public void registerListener(ModelListener listener) {
        this.listener = listener;
    }
    public void removeListener() {
        this.listener = null;
    }*/
    public void changeState(ControllerState state) {
        this.state = state;
    }

    public ControllerState getState() {
        return state;
    }


    public void changeTurn() {
        state.changeTurn();
    }


    public void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException {
        state.insertUserInputIntoModel(playerChoice);
    }


    public void sendPrivateMessage(String receiver, String sender, String content) {
        state.sendPrivateMessage(receiver, sender, content);
    }

    public void sendBroadcastMessage(String sender, String content) {
        state.sendBroadcastMessage(sender, content);
    }


    public void addPlayer(String nickname) throws LobbyIsFullException {
        state.addPlayer(nickname);
    }

    public void tryToResumeGame() {
        state.tryToResumeGame();
    }

    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        state.chooseNumberOfPlayerInTheGame(chosenNumberOfPlayers);
    }

    public void checkExceedingPlayer(int chosenNumberOfPlayers) throws ExcessOfPlayersException, WrongInputDataException {
        state.checkExceedingPlayer(chosenNumberOfPlayers);
    }

    public void startGame() {
        state.startGame();
    }

    public void disconnectPlayer(String nickname) {
        System.out.println("Giocatori prima del disconnect:" + this.model.getPlayers().stream().map(Player::getNickname).toList() + ",valore disconnected:" + this.model.getPlayers().stream().map(Player::isConnected).toList());
        state.disconnectPlayer(nickname);
        System.out.println("Giocatori dopo del disconnect:" + this.model.getPlayers().stream().map(Player::getNickname).toList() + ",valore disconnected:" + this.model.getPlayers().stream().map(Player::isConnected).toList());
    }

    //------------------------------------UTILITY METHODS------------------------------------
    public Game getModel() {
        return this.model;
    }

    public void setModel(Game model) {
        this.model = model;
        //this.listener.gameRestored();
    }

    public int getNumberOfPlayersCurrentlyInGame() {
        return this.model.getPlayers().size();
    }

    public PersonalGoal getPersonalGoal(int index) {
        Collections.shuffle(personalGoalsDeck);
        return personalGoalsDeck.remove(index);
    }

    public List<PersonalGoal> getPersonalGoalsDeck() {
        return personalGoalsDeck;
    }

    public void addPersonalGoal(PersonalGoal personalGoal) {
        personalGoalsDeck.add(personalGoal);
    }

    public int getNumberOfPersonalGoals() {
        return personalGoalsDeck.size();
    }

    public List<JsonBoardPattern> getBoardPatterns() {
        return this.boardPatterns;
    }

    public Random getRandomizer() {
        return randomizer;
    }

    /**
     * Method to check if there are stored games for the given nickname.
     *
     * @return if there are stored games.
     */
    public boolean areThereStoredGamesForPlayer(String playerNickname) {
        String gamesPath = "src/main/resources/storage/games.json";
        this.model.createGameFileIfNotExist(gamesPath);
        Game[] games = this.getStoredGamesFromJson();

        if (games == null || games.length == 0) return false;

        Game storedCurrentGame = this.getStoredGameForPlayer(playerNickname, games);

        return storedCurrentGame != null;
    }


    /**
     * Method to get all the stored games from the local json file.
     *
     * @return all stored games.
     */
    private Game[] getStoredGamesFromJson() {
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(Game.class, new GameModelDeserializer());
        Gson gson = gsonBuilder.create();
        Reader fileReader;
        String gamesPath = "src/main/resources/storage/games.json";
        Path source = Paths.get(gamesPath);
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
     * Method to restore stored games.
     */
    public void restoreGameForPlayer(GameListener server, String playerNickname) {
        state.restoreGameForPlayer(server, playerNickname);
    }
}
