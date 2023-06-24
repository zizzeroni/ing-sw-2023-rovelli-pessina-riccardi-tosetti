package it.polimi.ingsw.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GameController {
    private final Game model;
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

    public int getNumberOfPlayersCurrentlyInGame() {
        return this.model.getPlayers().size();
    }

    public PersonalGoal getPersonalGoal(int index) {
        Collections.shuffle(personalGoalsDeck);
        return personalGoalsDeck.remove(index);
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
}
