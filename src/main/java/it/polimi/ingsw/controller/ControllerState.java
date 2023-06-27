package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;
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
     * @see CreationState#sendPrivateMessage(String, String, String)
     * @see FinishingState#sendPrivateMessage(String, String, String)
     * @see OnGoingState#sendPrivateMessage(String, String, String)
     */
    public abstract void changeTurn();

    /**
     * Through its various implementations, allows the {@code Player}
     * to insert {@code Tile}s, in a given order (contained in {@code Choice}) into the {@code Board} .
     *
     * @param playerChoice the choice made by the player.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     * @see Choice
     * 
     * @throws WrongInputDataException when input data is not valid
     */
    public abstract void insertUserInputIntoModel(Choice playerChoice) throws WrongInputDataException;

    /**
     * This method implementations allows to send
     * private messages in different states to a
     * specified {@code Player}.
     *
     * @param receiver the receiver of the private message.
     * @param sender the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     *
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#sendPrivateMessage(String, String, String)
     * @see FinishingState#sendPrivateMessage(String, String, String)
     * @see OnGoingState#sendPrivateMessage(String, String, String)
     *
     */
    public abstract void sendPrivateMessage(String receiver, String sender, String content);

    /**
     * This method implementations allow to send
     * broadcast messages, while in different states, to all
     * the {@code Player}s.
     *
     * @param sender the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     *
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#sendBroadcastMessage(String, String)
     * @see FinishingState#sendBroadcastMessage(String, String)
     * @see OnGoingState#sendBroadcastMessage(String, String)
     *
     */
    public abstract void sendBroadcastMessage(String sender, String content);

    /**
     * This method implementation in the different states enables
     * the possibility to add new players to the current {@code Game}.
     *
     * @param nickname the nickname of the {@code Player}
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#addPlayer(String)
     * @see FinishingState#addPlayer(String)
     * @see OnGoingState#addPlayer(String)
     *
     */
    public abstract void addPlayer(String nickname) throws LobbyIsFullException;

    /**
     * TODO
     */
    public abstract void tryToResumeGame();

    /**
     * Permits to set the number of active players in the current {@code Game}.
     * Used during the creation state.
     *
     * @param chosenNumberOfPlayers the number of players joining the {@code Game}.
     *
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#chooseNumberOfPlayerInTheGame(int)
     */
    public abstract void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);

    public abstract void checkExceedingPlayer(int chosenNumberOfPlayers) throws ExcessOfPlayersException, WrongInputDataException;

    /**
     * The implementation of this method (in the {@code CreationState})
     * controls that all the necessary preparing has been done due to initiating the {@code Game}.
     *
     * @see CreationState#startGame(int numberOfCommonGoalCards)
     * @see FinishingState#startGame(int numberOfCommonGoalCards)
     * @see OnGoingState#startGame(int numberOfCommonGoalCards)
     */
    public abstract void startGame(int numberOfCommonGoalCards);

    /**
     * The implementation of this method in the different states
     * enacts the disconnection of a {@code Player}.
     *
     * @param nickname the nickname of the disconnecting player.
     *
     * @see CreationState#disconnectPlayer(String)
     * @see FinishingState#disconnectPlayer(String)
     * @see OnGoingState#disconnectPlayer(String)
     */
    public abstract void disconnectPlayer(String nickname);

    public abstract void restoreGameForPlayer(GameListener server, String nickname, String gamesStoragePath);
}
