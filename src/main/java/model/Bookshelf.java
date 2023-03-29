package model;

import model.tile.Tile;

import java.awt.print.Book;

public class Bookshelf {
    private final int numColumns = 5;
    private final int numRows = 6;

    private String image;

    private Tile[][] tiles;

    public Bookshelf() {
        image = null;
        tiles = new Tile[numRows][numColumns];
        for (int i = 0; i < numColumns; i++)
            for (int j = 0; j < numRows; j++)
                tiles[j][i] = null;
    }

    public boolean isFull() {
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
        return i;
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

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumElemColumn(int c){

        int counter = 0;
        for(int i = 0; i<6; i++){
            if(tiles[i][c]!=null)
                counter++;
        }
        return counter;
    }
    public int getNumRows() {
        return numRows;
    }

    public boolean isRowFull(int r) {
        for (int i = 0; i < numColumns; i++) {
            if (tiles[i][r] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnFull(int c) {
        for (int i = 0; i < numRows; i++) {
            if (tiles[c][i] == null) {
                return false;
            }
        }
        return true;
    }

    public int score() {
        return 1;
    }

    public void setTiles(Tile[][] tiles){ // funzione estrazione singola Tile selezionata
        this.tiles = tiles;
    }

}

