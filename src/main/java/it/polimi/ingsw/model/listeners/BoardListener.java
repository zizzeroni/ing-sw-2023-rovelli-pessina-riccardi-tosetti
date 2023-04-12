package it.polimi.ingsw.model.listeners;

import it.polimi.ingsw.model.Board;

public interface BoardListener {
    public void addedTilesToBoard(Board board);

    public void removedTilesFromBoard(Board board);
}
