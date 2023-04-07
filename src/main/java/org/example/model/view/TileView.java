package org.example.model.view;

import org.example.model.tile.Tile;
import org.example.model.tile.TileColor;

public class TileView {
    private final Tile tileModel;

    public TileView(Tile tileModel) {
        this.tileModel = tileModel;
    }

    public int getImageID() { return this.tileModel.getImageID(); }
    public TileColor getColor() {
        return this.tileModel.getColor();
    }
    //TODO: Verificare se è necessario inserire tale metodo
    public boolean isNull() {
        return this.tileModel == null;
    }
}
