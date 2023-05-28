package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.Timer;
import java.util.TimerTask;

public class InPauseState extends ControllerState{
    private final Timer timer = new Timer();
    public InPauseState(GameController controller) {
        super(controller);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                controller.getModel().setGameState(GameState.RESET_NEEDED);
            }
        }, 15000);
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
    //TODO:Chiedere a rovo
    private boolean checkIfGameIsResumable() {
        Game model = this.controller.getModel();
        return model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() > 1;
    }

    @Override
    public void addPlayer(String nickname) {
        this.controller.getModel().getPlayerFromNickname(nickname).setConnected(true);
    }

    @Override
    public void tryToResumeGame() {
        if(checkIfGameIsResumable()) {
            timer.cancel();
            this.controller.changeState(new OnGoingState(this.controller));
            this.controller.getModel().setGameState(GameState.ON_GOING);
        } else {
            //Set the current game state in order to generate a notification, sent to the client
            this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        }
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
