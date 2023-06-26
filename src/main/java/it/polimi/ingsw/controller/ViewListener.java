package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;

/**
 * An interface used to represent an object of type 'listener'.
 * In this case the listener registers itself to the {@code View}.
 * When the view is subject to changes the listener responds to them
 * through a set of various methods reported in the different states, having
 * similar, but distinct, implementations.
 *
 * @see javax.swing.text.View
 * @see com.sun.glass.ui.View
 * @see CreationState
 * @see FinishingState
 * @see OnGoingState
 */
public interface ViewListener {
    /**
     * Change the turn in the context of the present state through its implementations.
     *
     * @see CreationState#sendPrivateMessage(String, String, String)
     * @see FinishingState#sendPrivateMessage(String, String, String)
     * @see OnGoingState#sendPrivateMessage(String, String, String)
     */
    public void changeTurn();

    /**
     * Through its various implementations, allows the {@code Player}
     * to insert {@code Tile}s, in a given order (contained in {@code Choice}) into the {@code Board} .
     *
     * @param playerChoice the choice made by the player.
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     * @see Choice
     */
    public void insertUserInputIntoModel(Choice playerChoice);

    /**
     * This method implementations allows to send
     * private messages, while in different states, to a
     * specified {@code Player}.
     *
     * @param receiver the receiver of the private message.
     * @param sender   the sender of the broadcast {@code Message}.
     * @param content  the text of the message.
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#sendPrivateMessage(String, String, String)
     * @see FinishingState#sendPrivateMessage(String, String, String)
     * @see OnGoingState#sendPrivateMessage(String, String, String)
     */
    public void sendPrivateMessage(String receiver, String sender, String content);

    /**
     * This method implementations allows to send
     * broadcast messages in different states to all
     * the {@code Player}s.
     *
     * @param sender  the sender of the broadcast {@code Message}.
     * @param content the text of the message.
     * @see it.polimi.ingsw.model.Player
     * @see CreationState#sendBroadcastMessage(String, String)
     * @see FinishingState#sendBroadcastMessage(String, String)
     * @see OnGoingState#sendBroadcastMessage(String, String)
     */
    public void sendBroadcastMessage(String sender, String content);

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
    public void addPlayer(String nickname);

    /**
     * Permits to set the number of active players in the current {@code Game}.
     * Used during the creation state.
     *
     * @param chosenNumberOfPlayers the number of players joining the {@code Game}.
     * @see it.polimi.ingsw.model.Game
     * @see CreationState#chooseNumberOfPlayerInTheGame(int)
     */
    public void chooseNumberOfPlayerInTheGame(int chosenNumberOfPlayers);

    /**
     * The implementation of this method (in the {@code CreationState})
     * controls that all the necessary preparing has been done due to initiating the {@code Game}.
     *
     * @see CreationState#startGame()
     * @see FinishingState#startGame()
     * @see OnGoingState#startGame()
     */
    public void startGame();

    /**
     * The implementation of this method in the different states
     * enacts the disconnection of a {@code Player}.
     *
     * @param nickname the nickname of the disconnecting player.
     * @see CreationState#disconnectPlayer(String)
     * @see FinishingState#disconnectPlayer(String)
     * @see OnGoingState#disconnectPlayer(String)
     */
    public void disconnectPlayer(String nickname);

    public void restoreGameForPlayer(String nickname);

    public void areThereStoredGamesForPlayer(String nickname);
}
