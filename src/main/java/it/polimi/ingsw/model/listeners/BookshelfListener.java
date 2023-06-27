package it.polimi.ingsw.model.listeners;

import it.polimi.ingsw.model.Bookshelf;

/**
 * An interface used to represent an object of type 'listener'.
 * In this case the listener registers itself to the {@code Bookshelf}.
 * When the view is subject to changes the listener responds to them
 * through a set of methods to notify the adding or removal of a {@code Tile}
 * from the {@code Bookshelf}.
 * It also detects if the linked image has been modified.
 */
public interface BookshelfListener {
    /**
     * Notifies when tiles are added to the {@code Bookshelf} passed as parameter.
     *
     * @param bookshelf the tiles are added on this board.
     *
     * @see Bookshelf
     */
    public void tileAddedToBookshelf(Bookshelf bookshelf);
    
    /**
     * Notifies if the current {@code Bookshelf} image has been modified.
     *
     * @param image the image that has changed following the method call.
     */
    public void imageModified(String image);
}
