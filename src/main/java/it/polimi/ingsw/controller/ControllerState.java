package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.ExcessOfPlayersException;
import it.polimi.ingsw.model.exceptions.LobbyIsFullException;
import it.polimi.ingsw.model.exceptions.WrongInputDataException;
import it.polimi.ingsw.model.listeners.GameListener;

/**
 * This Class is used to describe the State Pattern
 * employed in the implementation of the {@code GameController}.
 */
public abstract class ControllerState {
    protected GameController controller;

    /**
     * Sets a {@code GameController} as {@code ControllerState}, in order to
     * link the controller itself to the states of the {@code Game}.
     *
     * @param controller is the game controller
     */
    public ControllerState(GameController controller) {
        this.controller = controller;
    }

    /**
     * Change the turn in the context of the present state through its implementations.
     *
     * @param gamesStoragePath       path in which are stored the games
     * @param gamesStoragePathBackup backup path in which are stored the games
     * @see CreationState#changeTurn(String, String)
     * @see FinishingState#changeTurn(String, String)
     * @see OnGoingState#changeTurn(String, String)
     * @see InPauseState#changeTurn(String, String)
     */
    public abstract void changeTurn(String gamesStoragePath, String gamesStoragePathBackup);

    /**
     * Through its various implementations, allows the {@code Player}
     * to insert {@code Tile}s, in a given order (contained in {@code Choice}) into the {@code Board} .
     *
     * @param playerChoice the choice made by the player.
     * @throws WrongInputDataException thrown when input data is not valid
     * @see CreationState#insertUserInputIntoModel(Choice)
     * @see FinishingState#insertUserInputIntoModel(Choice)
     * @see OnGoingState#insertUserInputIntoModel(Choice)
     * @see InPauseState#insertUserInputIntoModel(Choice)
     * @see Choice
     */
    public abstract void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException;

    /**
     * This method implementations allows to send
     * private messages in different states to a
     * specified {@code Player}.
     *
     * @param receiver the receiver of the private message.
     * @param sender   the sender of the broadcast {@code Message}.
     * @param content  the text of the message.
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#sendPrivateMessage(String, String, String)
     * @see FinishingState#sendPrivateMessage(String, String, String)
     * @see OnGoingState#sendPrivateMessage(String, String, String)
     * @see InPauseState#sendPrivateMessage(String, String, String)
     */
    public abstract void sendPrivateMessage(String receiver, String sender, String content);

    /**
     * This method implementations allow to send
     * broadcast messages, while in different states, to all
     * the {@code Player}s.
     *
     * @param sender  the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#sendBroadcastMessage(String, String)
     * @see FinishingState#sendBroadcastMessage(String, String)
     * @see OnGoingState#sendBroadcastMessage(String, String)
     * @see InPauseState#sendBroadcastMessage(String, String)
     */
    public abstract void sendBroadcastMessage(String sender, String content);

    /**
     * This method implementation in the different states enables
     * the possibility to add new players to the current {@code Game}.
     *
     * @param nickname the nickname of the {@code Player}
     * @throws LobbyIsFullException if the lobby is full
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#addPlayer(String)
     * @see FinishingState#addPlayer(String)
     * @see OnGoingState#addPlayer(String)
     * @see InPauseState#addPlayer(String)
     */
    public abstract void addPlayer(String nickname) throws LobbyIsFullException;

    /**
     * This method tries to resume the current's game when possible.
     */
    public abstract void tryToResumeGame();

    /**
     * This method implementation in the different states enables
     * setting the number of active players in the current {@code Game}.
     *
     * @param chosenNumberOfPlayers the number of players joining the {@code Game}.
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#chooseNumberOfPlayerInTheGame(int)
     * @see FinishingState#chooseNumberOfPlayerInTheGame(int)
     * @see OnGoingState#chooseNumberOfPlayerInTheGame(int)
     * @see InPauseState#chooseNumberOfPlayerInTheGame(int)
     */
    public abstract void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);

    /**
     * Checks if the number of players in the current lobby is exceeding the game's set number of players
     * through its various implementations.
     *
     * @param chosenNumberOfPlayers number of players chosen by the first player.
     * @throws ExcessOfPlayersException signals an excess in the player's number.
     * @throws WrongInputDataException  occurs when data has an unexpected value.
     * @see CreationState#checkExceedingPlayer(int)
     * @see OnGoingState#checkExceedingPlayer(int)
     * @see FinishingState#checkExceedingPlayer(int)
     * @see InPauseState#checkExceedingPlayer(int)
     */
    public abstract void checkExceedingPlayer(int chosenNumberOfPlayers) throws ExcessOfPlayersException, WrongInputDataException;

    /**
     * Method that permit to start the game through its different implementation
     * controls that all the necessary preparing has been done due to initiating the {@code Game}.
     *
     * @param numberOfCommonGoalCards number of common goal card from which extract the common goal
     *                                used during the game
     * @see CreationState#startGame(int)
     * @see FinishingState#startGame(int)
     * @see OnGoingState#startGame(int)
     * @see InPauseState#startGame(int)
     */
    public abstract void startGame(int numberOfCommonGoalCards);

    /**
     * The implementation of this method in the different states
     * enacts the disconnection of a {@code Player}.
     *
     * @param nickname the nickname of the disconnecting player.
     * @see Player
     * @see CreationState#disconnectPlayer(String)
     * @see FinishingState#disconnectPlayer(String)
     * @see OnGoingState#disconnectPlayer(String)
     * @see InPauseState#disconnectPlayer(String)
     */
    public abstract void disconnectPlayer(String nickname);

    /**
     * Restores the current game for the considered player.
     *
     * @param server           the server to which the model notifies its changes.
     * @param nickname         player's nickname that requested the restore.
     * @param gamesStoragePath the path where are stored the games.
     * @see Player
     * @see Game
     * @see CreationState#restoreGameForPlayer(GameListener, String, String)
     * @see FinishingState#restoreGameForPlayer(GameListener, String, String)
     * @see OnGoingState#restoreGameForPlayer(GameListener, String, String)
     * @see InPauseState#restoreGameForPlayer(GameListener, String, String)
     */
    public abstract void restoreGameForPlayer(GameListener server, String nickname, String gamesStoragePath);
}
