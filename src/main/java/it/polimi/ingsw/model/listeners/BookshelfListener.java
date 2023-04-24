package it.polimi.ingsw.model.listeners;

import it.polimi.ingsw.model.Bookshelf;

public interface BookshelfListener {
    public void tileAddedToBookshelf(Bookshelf bookshelf);

    public void imageModified(String image);
}
