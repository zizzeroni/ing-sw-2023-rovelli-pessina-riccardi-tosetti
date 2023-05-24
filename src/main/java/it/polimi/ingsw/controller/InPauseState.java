package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public class InPauseState extends ControllerState{
    public InPauseState(GameController controller) {
        super(controller);
    }

    @Override
    public void changeTurn() {
        //In pause so do nothing...
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        //In pause so do nothing...
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        //No other player in the game...
    }

    @Override
    public void sendBroadcastMessage(String sender, String content) {
        for (Player player : this.controller.getModel().getPlayers()) {
            Message message = new Message(MessageType.BROADCAST, player.getNickname(), sender, content);
            player.addMessage(message);
        }
    }

    @Override
    public void addPlayer(String nickname) {
        this.controller.getModel().getPlayerFromNickname(nickname).setConnected(true);
    }

    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        //Game is in pause so do nothing...
    }

    @Override
    public void startGame() {
        //Game is in pause so do nothing...
    }

    @Override
    public void disconnectPlayer(String nickname) {
        Game model = this.controller.getModel();
        model.getPlayerFromNickname(nickname).setConnected(false);
    }

    public static GameState toEnum() {
        return GameState.PAUSED;
    }
}
