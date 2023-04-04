package model;

import model.tile.Tile;

public class Bookshelf {
    private final int numberOfColumns = 5;
    private final int numberOfRows = 6;

    private String image;

    private Tile[][] tiles;

    public Bookshelf() {
        image = null;
        tiles = new Tile[numberOfRows][numberOfColumns];
        for (int i = 0; i < numberOfRows; i++)
            for (int j = 0; j < numberOfColumns; j++)
                tiles[i][j] = null;
    }

    public boolean isFull() {
        for (int i = 0; i < numberOfColumns; i++) {
            if (tiles[i][0] == null) {
                return false;
            }
        }
        return true;
    }

    public void addTile(Tile tile, int i) {
        this.tiles[(this.numberOfRows - 1) - getNumberOfTilesInColumn(i)][i] = tile;
    }

    public int emptyCellsInColumn(int column) {
        int counter = 0;
        for(int i = this.numberOfRows - 1; i > 0; i--) {
            if(this.tiles[i][column] != null){
                return counter;
            }
            counter++;
        }

        return counter;
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

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfTilesInColumn(int c){
        int counter = 0;
        for(int i = 0; i<6; i++){
            if(tiles[i][c]!=null)
                counter++;
        }
        return counter;
    }
    public int getNumberOfRows() {
        return numberOfRows;
    }

    public boolean isRowFull(int r) {
        for (int i = 0; i < numberOfColumns; i++) {
            if (tiles[i][r] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnFull(int c) {
        for (int i = 0; i < numberOfRows; i++) {
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

