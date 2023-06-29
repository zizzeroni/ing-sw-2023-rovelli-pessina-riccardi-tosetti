package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.utils.OptionsValues;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Class used to represent the paused game state.
 *
 * @see Game
 * @see ControllerState
 */
public class InPauseState extends ControllerState {
    private final Timer timer = new Timer();
    private boolean gameResumed;

    /**
     * Class constructor.
     * Sets the game's controller to given value.
     *
     * @param controller the GameController used.
     */
    public InPauseState(GameController controller) {
        super(controller);
        this.gameResumed = false;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!gameResumed) {
                    controller.getModel().setGameState(GameState.RESET_NEEDED);
                } else {
                    this.cancel();
                }
            }
        }, OptionsValues.MILLISECOND_COUNTDOWN_VALUE);
    }

    /**
     * Used to handle the pausing when changing game's turn.
     * Necessary in case this method is called while in the InPauseState state (SHOULDN'T BE HAPPENING but if happen then prevents being "stuck" when using socket).
     *
     * @see Game
     */
    @Override
    public void changeTurn(String gamesStoragePath, String gamesStoragePathBackup) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    /**
     * Used to handle the input insertion in the game's model
     *
     * @param playerChoice the choice made by the player.
     * @see Player
     */
    @Override
    public void insertUserInputIntoModel(Choice playerChoice) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    /**
     * This method is used to stream a message privately.
     * Only the specified receiver will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
     * the {@code nickname}s of the receiving {@code Player}s and its message type to {@code PRIVATE}.
     *
     * @param receiver the {@code Player} receiving the message.
     * @param sender   the {@code Player} sending the message.
     * @param content  the text of the message being sent.
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendPrivateMessage(String receiver, String sender, String content) {
        Message message = new Message(MessageType.PRIVATE, receiver, sender, content);
        for (Player player : this.controller.getModel().getPlayers()) {
            //sender and receiver will see the message, in order to keep the full history
            if (player.getNickname().equals(receiver) || player.getNickname().equals(sender)) {
                player.addMessage(message);
            }
        }
    }

    /**
     * This method is used to stream a message in broadcast mode.
     * All the players will be able to read the message
     * in any chat implementation. It builds a new object message at each call, setting
     * the {@code nickname} of the sending {@code Player} and its message type to {@code BROADCAST}.
     *
     * @param sender  the {@code Player} sending the message.
     * @param content the text of the message being sent.
     * @see Player
     * @see Player#getNickname()
     * @see Message#messageType()
     */
    @Override
    public void sendBroadcastMessage(String sender, String content) {
        for (Player player : this.controller.getModel().getPlayers()) {
            Message message = new Message(MessageType.BROADCAST, player.getNickname(), sender, content);
            player.addMessage(message);
        }
    }

    /**
     * Verifies if the current game can be redeemed (under some countdown circumstances it could not be possible).
     *
     * @return {@code true} if and only if the current game can be redeemed, {@code false} otherwise.
     */
    private boolean checkIfGameIsResumable() {
        Game model = this.controller.getModel();
        return model.getPlayers().stream().map(Player::isConnected).filter(connected -> connected).count() > OptionsValues.MIN_PLAYERS_TO_GO_ON_PAUSE;
    }

    /**
     * This method implementation in the different states enables
     * the possibility to add new players to the current {@code Game}.
     *
     * @param nickname the nickname of the {@code Player}
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#addPlayer(String)
     * @see FinishingState#addPlayer(String)
     * @see OnGoingState#addPlayer(String)
     */
    @Override
    public void addPlayer(String nickname) throws LobbyIsFullException {
        if (this.controller.getModel().getPlayerFromNickname(nickname) == null) {
            throw new LobbyIsFullException("Cannot access a game: Lobby is full or you were not part of it at the start of the game");
        } else {
            this.controller.getModel().getPlayerFromNickname(nickname).setConnected(true);
        }
    }


    /**
     * This method tries to resume the current's game when possible.
     */
    @Override
    public void tryToResumeGame() {
        if (checkIfGameIsResumable()) {
            this.gameResumed = true;
            this.timer.cancel();
            this.changeActivePlayer();
            this.controller.changeState(new OnGoingState(this.controller));
            this.controller.getModel().setGameState(GameState.ON_GOING);
        } else {
            //Set the current game state in order to generate a notification, sent to the client
            this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        }
    }

    /**
     * Verifies if whether the current {@code Player}
     * is considered active or not, mainly through a call to the
     * {@code getActivePlayerIndex} method in the {@code GameController}
     * (linked to the active {@code Game}).
     *
     * @see GameController#getModel()
     * @see Game#getActivePlayerIndex()
     */
    private void changeActivePlayer() {
        Game model = this.controller.getModel();
        if (!model.getPlayers().get(model.getActivePlayerIndex()).isConnected()) {
            if (model.getActivePlayerIndex() == model.getPlayers().size() - 1) {
                model.setActivePlayerIndex(0);
            } else {
                model.setActivePlayerIndex(model.getActivePlayerIndex() + 1);
            }
            changeActivePlayer();
        }
    }


    /**
     * Permits to set the number of active players in the current {@code Game}.
     * Used during the creation state.
     *
     * @param chosenNumberOfPlayers the number of players joining the {@code Game}.
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#chooseNumberOfPlayerInTheGame(int)
     */
    @Override
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }


    /**
     * Checks if the number of players in the current lobby is exceeding the game's set number of players.
     *
     * @param chosenNumberOfPlayers is the current number of players.
     * @throws ExcessOfPlayersException signals an excess in the player's number.
     * @throws WrongInputDataException  occurs when data has not been entered correctly.
     * @see Player
     */
    @Override
    public void checkExceedingPlayer(int chosenNumberOfPlayers) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    /**
     * The method starts verifying  if the {@code Game} creation has occurred properly,
     * confronting the number of active players registered during the previous phase with
     * that stored in the {@code Model}.
     * Then, it proceeds to adjust the {@code Board} and to draw a list of Tiles.
     * Finally, it initializes the {@code ScoreTile} list for each active {@code Player},
     * (necessary in order to replace them later if a player complete a {@code CommonGoal}).
     *
     * @see Game
     * @see Player
     * @see ScoreTile
     * @see CommonGoal
     * @see Game#getNumberOfPlayersToStartGame()
     * @see Game#getPlayers()
     * @see Board#setPattern(JsonBoardPattern)
     * @see Board#numberOfTilesToRefill()
     */
    @Override
    public void startGame(int numberOfCommonGoalCards) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }

    /**
     * Disconnects the selected {@code Player} from the {@code Game}
     * by changing his connectivity state.
     * (only possible because the {@code Game} has already started).
     *
     * @param nickname is the nickname identifying the player selected for disconnection.
     * @see Player
     * @see Game
     * @see Player#setConnected(boolean)
     */
    @Override
    public void disconnectPlayer(String nickname) {
        Game model = this.controller.getModel();
        model.getPlayerFromNickname(nickname).setConnected(false);
        this.controller.getModel().setGameState(GameState.RESET_NEEDED);
    }

    /**
     * Restores the current game for the considered player.
     *
     * @param server   the server controlling the game's execution.
     * @param nickname the given player's nickname.
     * @see Player
     * @see Game
     */
    @Override
    public void restoreGameForPlayer(GameListener server, String nickname, String gamesStoragePath) {
        //Necessary in case i call this method while I'm in InPauseState state (SHOULDN'T BE HAPPENING but if happen then i'm not "stuck" when using socket)
        this.controller.getModel().setGameState(this.controller.getModel().getGameState());
        //In pause so do nothing...
    }


    /**
     * Returns the current {@code State} of the {@code Game}.
     *
     * @return the {@code PAUSED} state of the {@code Game}.
     * @see GameState#PAUSED
     */
    public static GameState toEnum() {
        return GameState.PAUSED;
    }
}
