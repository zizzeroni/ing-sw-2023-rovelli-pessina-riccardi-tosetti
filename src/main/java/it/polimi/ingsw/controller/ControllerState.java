package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Choice;

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
     */
    public abstract void insertUserInputIntoModel(Choice playerChoice);

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
     * This method implementations allows to send
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
    public abstract void addPlayer(String nickname);

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

    /**
     * The implementation of this method (in the {@code CreationState})
     * controls that all the necessary preparing has been done due to initiating the {@code Game}.
     *
     * @see CreationState#startGame()
     * @see FinishingState#startGame()
     * @see OnGoingState#startGame()
     */
    public abstract void startGame();

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
}
