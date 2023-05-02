package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.GameState;

public abstract class ControllerState {
    protected GameController controller;

    public ControllerState(GameController controller) {
        this.controller = controller;
    }

    public abstract void changeTurn();

    public abstract void insertUserInputIntoModel(Choice playerChoice);

    public abstract void sendPrivateMessage(String receiver, String sender, String content);

    public abstract void sendBroadcastMessage(String sender, String content);

    public abstract void addPlayer(String nickname);

    public abstract void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);

}
