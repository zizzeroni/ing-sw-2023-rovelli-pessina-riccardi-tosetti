package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.utils.OptionsValues;

import java.util.Timer;
import java.util.TimerTask;

public class InPauseState extends ControllerState {
    private final Timer timer = new Timer();
    //TODO: Chiedere a rovo, non dovrebbe essere necessario ma per qualche motivo il metodo cancel chiamato nel metodo tryToResumeGame non cancella il timer
    private boolean gameResumed;

    public InPauseState(GameController controller) {
        super(controller);
        this.gameResumed = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gameResumed) {
                    System.out.println(controller.getModel().getGameState());
                    controller.getModel().setGameState(GameState.RESET_NEEDED);
                    System.out.println("RESET_NEEDED Timer executed");
                } else {
                    this.cancel();
                }
            }
        }, OptionsValues.MILLISECOND_COUNTDOWN_VALUE);
    }

    @Override
    public void changeTurn() {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
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
        return model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() > OptionsValues.MIN_PLAYERS_TO_GO_ON_PAUSE;
    }

    @Override
    public void addPlayer(String nickname) throws LobbyIsFullException {
        if (this.controller.getModel().getPlayerFromNickname(nickname) == null) {
            throw new LobbyIsFullException("Cannot access a game: Lobby is full and you were not part of it at the start of the game");
        } else {
            this.controller.getModel().getPlayerFromNickname(nickname).setConnected(true);
        }
    }

    @Override
    public void tryToResumeGame() {
        if (checkIfGameIsResumable()) {
            this.gameResumed = true;
            this.timer.cancel();
            //executorService.shutdownNow();
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
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    @Override
    public void checkExceedingPlayer(int chosenNumberOfPlayers) throws ExcessOfPlayersException, WrongInputDataException {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    @Override
    public void startGame() {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    @Override
    public void disconnectPlayer(String nickname) {
        Game model = this.controller.getModel();
        model.getPlayerFromNickname(nickname).setConnected(false);
    }

    @Override
    public void restoreGameForPlayer(GameListener server, String nickname) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }


    public static GameState toEnum() {
        return GameState.PAUSED;
    }
}
