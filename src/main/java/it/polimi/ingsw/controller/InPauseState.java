package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.Timer;
import java.util.TimerTask;

public class InPauseState extends ControllerState{
    private final Timer timer = new Timer();
    //TODO: Chiedere a rovo, non dovrebbe essere necessario ma per qualche motivo il metodo cancel chiamato nel metodo tryToResumeGame non cancella il timer
    private boolean gameResumed;
    public InPauseState(GameController controller) {
        super(controller);
        this.gameResumed = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(gameResumed) {
                    System.out.println(controller.getModel().getGameState());
                    controller.getModel().setGameState(GameState.RESET_NEEDED);
                    System.out.println("RESET_NEEDED Timer executed");
                } else {
                    this.cancel();
                }
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
            this.gameResumed = true;
            timer.cancel();
            System.out.println("RESET_NEEDED Timer cancelled");
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
