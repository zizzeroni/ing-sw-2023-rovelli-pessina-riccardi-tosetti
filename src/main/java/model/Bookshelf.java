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

    public void setSingleTiles(Tile tile, int i, int j) {
        this.tiles[i][j] = tile;
    }

    public Tile getSingleTile(int i, int j){ // funzione estrazione singola Tile selezionata
        return tiles[i][j];
    }



    // numero colonne e numero righe
    // is row full is col full

    public boolean isRowFull(int r) throws NotImplementedException {
        for (int i = 0; i < numColumns; i++) {
            if (tiles[i][r] != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isColumnFull(int c) throws NotImplementedException {
        for (int i = 0; i < numRows; i++) {
            if (tiles[c][i] != null) {
                return true;
            }
        }
        return false;
    }


    public void setTiles(Tile[][] tiles){ // funzione estrazione singola Tile selezionata
        this.tiles = tiles;
    }

}

