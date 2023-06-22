package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.network.exceptions.WrongInputDataException;

public abstract class ControllerState {
    protected GameController controller;

    public ControllerState(GameController controller) {
        this.controller = controller;
    }

    public abstract void changeTurn();

    public abstract void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException;

    public abstract void sendPrivateMessage(String receiver, String sender, String content);

    public abstract void sendBroadcastMessage(String sender, String content);

    public abstract void addPlayer(String nickname);

    public abstract void tryToResumeGame();

    public abstract void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);

    public abstract void startGame();

    public abstract void disconnectPlayer(String nickname);

    public abstract void restoreGameForPlayer(String nickname);
}
