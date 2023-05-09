package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;

public interface ViewListener {
    public void changeTurn();

    public void insertUserInputIntoModel(Choice playerChoice);

    public void sendPrivateMessage(String receiver, String sender, String content);

    public void sendBroadcastMessage(String sender, String content);

    public void addPlayer(String nickname);

    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);

    public void startGame();
}
