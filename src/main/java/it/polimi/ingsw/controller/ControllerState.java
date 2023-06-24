package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;

public abstract class ControllerState {
    protected GameController controller;

    public ControllerState(GameController controller) {
        this.controller = controller;
    }

    public abstract void changeTurn();

    public abstract void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException;

    public abstract void sendPrivateMessage(String receiver, String sender, String content);

    public abstract void sendBroadcastMessage(String sender, String content);

    public abstract void addPlayer(String nickname) throws LobbyIsFullException;

    public abstract void tryToResumeGame();

    public abstract void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);
    public abstract void checkExceedingPlayer(int chosenNumberOfPlayers) throws ExcessOfPlayersException, WrongInputDataException;

    public abstract void startGame();

    public abstract void disconnectPlayer(String nickname);
}
