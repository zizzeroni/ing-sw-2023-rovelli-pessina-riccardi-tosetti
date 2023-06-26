package it.polimi.ingsw.model.listeners;

import it.polimi.ingsw.model.Game;

/**
 * An interface used to represent an object of type 'listener'.
 * In this case the listener registers itself to the {@code Game}.
 * When the view is subject to changes the listener responds to them
 * through a set of methods to notify the adding of a {@code Player},
 * if the number of active players has been modified, if the active
 * player index has been modified, ...
 * It also detects if the game's {@code Bag} has been modified.
 *
 * @see Game
 */
public interface GameListener {
    /**
     * Notifies if the active {@code numberOfPlayers}
     * into the {@code Game}'s lobby has been modified.
     *
     * @see it.polimi.ingsw.model.Game
     */
    public void numberOfPlayersModified();

    /**
     * Notifies if the {@code activePlayerIndex}
     * has been modified.
     */
    public void activePlayerIndexModified();

    /**
     * Notifies if the bag has been modified
     * trough shuffling its {@code Tile}s
     *
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public void bagModified();

    /**
     * Notifies if the evaluated {@code commonGoal}
     * has been modified in the current {@code Game}.
     *
     * @see it.polimi.ingsw.model.Game
     */
    public void commonGoalsModified();

    /**
     * Notifies if another {@code Player} has entered
     * into the {@code Game}'s lobby.
     *
     * @see it.polimi.ingsw.model.Game
     */
    public void addedPlayer();

    /**
     * Notifies if the active {@code Game}'s state
     * has changed
     *
     * @see Game#getGameState()
     */
    public void gameStateChanged();
}
