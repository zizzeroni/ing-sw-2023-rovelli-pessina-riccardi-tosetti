package model.view;

import model.tile.Tile;
import model.tile.TileColor;

public class TileView {
    private final Tile tileModel;

    public TileView(Tile tileModel) {
        this.tileModel = tileModel;
    }

    public int getImageID() { return this.tileModel.getImageID(); }
    public TileColor getColor() {
        return this.tileModel.getColor();
    }
}
