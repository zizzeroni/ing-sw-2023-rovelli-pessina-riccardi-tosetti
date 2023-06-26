package it.polimi.ingsw.model.listeners;

/**
 * An interface used to represent an object of type 'listener'.
 * In this case the listener registers itself to the {@code Player}.
 * It contains a series of methods for chat management and to
 * notify a player's reconnection event.
 *
 * @see it.polimi.ingsw.model.Player
 */
public interface PlayerListener {
    /**
     * Signals if a chat update has occurred
     * during the current {@code Game}.
     */
    public void chatUpdated();

    /**
     * Verifies if a {@code Player} has reconnected
     * to the {@code Game}'s lobby.
     */
    public void playerHasReconnected();
}
