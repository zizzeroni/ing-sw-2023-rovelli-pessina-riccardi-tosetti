package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.io.Serializable;

public class TileView implements Serializable {
    private final TileColor color;
    private final int id;
    public TileView(Tile tileModel) {
        this.color = tileModel.getColor();
        this.id = tileModel.getId();
    }
    public int getId() {
        return this.id;
    }

    public TileColor getColor() {
        return this.color;
    }
}
