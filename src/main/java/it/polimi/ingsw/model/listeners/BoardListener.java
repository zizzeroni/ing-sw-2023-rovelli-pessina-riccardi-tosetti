package it.polimi.ingsw.model.listeners;

import it.polimi.ingsw.model.Board;

/**
 * An interface used to represent an object of type 'listener'.
 * In this case the listener registers itself to the {@code Board}.
 * When the view is subject to changes the listener responds to them
 * through a set of methods to notify the adding or removal of a {@code Tile}
 * from the {@code Board}.
 *
 * @see javax.swing.text.View
 * @see Board
 */
public interface BoardListener {
    /**
     * Notifies when tiles are added to the {@code Board} passed as parameter.
     *
     * @param board the tiles are added on this board.
     * @see Board
     */
    public void addedTilesToBoard(Board board);

    /**
     * Notifies when tiles are removed from the {@code Board} passed as parameter.
     *
     * @param board the tiles are removed from this board.
     * @see Board
     */
    public void removedTilesFromBoard(Board board);
}
