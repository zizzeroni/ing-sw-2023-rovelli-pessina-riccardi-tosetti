package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.ViewListener;
import it.polimi.ingsw.model.exceptions.GenericException;
import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.view.GameView;
import it.polimi.ingsw.view.GenericUILogic;


/**
 * This interface has been used to represent a series of common methods
 * used in the various implementations of the User's interface (both textual and graphical).
 */
public interface UI {
    /**
     * Displays a standard message to identify the starting of the next turn.
     * Calls the nickname of the active player and the shows the board's state.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Board
     */
    public void showNewTurnIntro();

    /**
     * Registers the {@code BoardListener} on the Board.
     *
     * @param listener the listener that will register on the {@code Board}.
     *
     * @see BoardListener
     * @see java.net.http.WebSocket.Listener
     */
    public void registerListener(ViewListener listener);

    /**
     * Removes the {@code GameListener}.
     *
     * Listener is the {@code GameListener} being registered
     *
     * @see   GameListener
     */
    public void removeListener();

    /**
     * Sets the player's nickname.
     *
     * @param nickname the player's nickname.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public void setNickname(String nickname);

    /**
     * Method used to update the model by receiving a GameView object from the Server. Depending on the UI state and different model attributes
     * this method changes the State of the game from the UI perspective.
     *
     * @param modelUpdated the updated game's model.
     *
     * @see it.polimi.ingsw.model.Game
     * @see GameView
     * @see it.polimi.ingsw.network.Server
     */
    public void modelModified(GameView modelUpdated);

    /**
     * Used to display a generic game's exception through the generic ui's logic.
     *
     * @param exception the given GenericException.
     *
     * @see GenericUILogic
     * @see GenericException
     */
    public void printException(GenericException exception);
    /**
     * Setter used to provided stored game's for the player reconnecting to the current's game server.
     *
     * @param result {@code true} if and only if the game has been stored properly, {@code false} otherwise.
     *
     * @see it.polimi.ingsw.model.Game
     */
    public void setAreThereStoredGamesForPlayer(boolean result);
    /**
     * Allows to identify the type of chat's command forwarded by the player.
     *
     * @see it.polimi.ingsw.model.Player
     */
    public void run();


}
