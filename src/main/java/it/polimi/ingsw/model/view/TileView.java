package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

public class TileView {
    //private final Tile tileModel;
    private final TileColor color;
    private final int imageID;

    public TileView(Tile tileModel) {
        this.color = tileModel.getColor();
        this.imageID = tileModel.getImageID();
    }

    public int getImageID() {
        return this.imageID;
    }

    public TileColor getColor() {
        return this.color;
    }

    /*public boolean isNull() {
        return this.tileModel == null;
    }

    public boolean hasNoColor(){
        return this.tileModel.getColor() == null;
    }*/
}
