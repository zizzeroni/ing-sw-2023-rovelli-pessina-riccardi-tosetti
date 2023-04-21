package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Player;

public interface ViewListener {
    public void changeTurn();

    public void insertUserInputIntoModel(Choice playerChoice);

    public void sendPrivateMessage(Player receiver, Player sender, String content);

    public void sendBroadcastMessage(Player sender, String content);

    public void addPlayer(String nickname);

    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);
}
