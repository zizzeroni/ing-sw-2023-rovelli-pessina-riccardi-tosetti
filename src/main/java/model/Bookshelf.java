package model;

import model.tile.Tile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Bookshelf {
    private final int numColumns = 5;
    private final int numRows = 6;

    private String image;

    private Tile[][] tiles;

    public Bookshelf() {
        image = null;
        tiles = new Tile[numColumns][numRows];
        for (int i = 0; i < numColumns; i++)
            for (int j = 0; j < numRows; j++)
                tiles[i][j] = null;
    }

    public boolean isFull() throws NotImplementedException {
        for (int i = 0; i < numColumns; i++) {
            if (tiles[i][numRows] != null) {
                return true;
            }
        }
        return false;
    }

    public void addTile(Tile tile, int i) {
        // ...
    }

    public int emptyCellsInColumn(int i) {
        throw new NotImplementedException();
    }

    public Bookshelf(String image, Tile[][] tiles) {
        this.image = image;
        this.tiles = tiles;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

}


