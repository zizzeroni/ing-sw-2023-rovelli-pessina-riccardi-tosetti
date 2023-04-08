package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.BookshelfListener;
import it.polimi.ingsw.model.tile.Tile;

public class Bookshelf {
    private BookshelfListener listener;
    private final int numberOfColumns = 5;
    private final int numberOfRows = 6;
    private String image;
    private Tile[][] tiles;

    public void registerListener(BookshelfListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public Bookshelf() {
        image = null;
        tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++)
            for (int column = 0; column < this.numberOfColumns; column++)
                tiles[row][column] = null;
    }

    public boolean isFull() {
        for (int column = 0; column < numberOfColumns; column++) {
            if (tiles[0][column] == null) {
                return false;
            }
        }
        return true;
    }

    public void addTile(Tile tile, int column) {
        this.tiles[(this.numberOfRows - 1) - getNumberOfTilesInColumn(column)][column] = tile;
        this.listener.tileAddedToBookshelf(this);
    }

    public int emptyCellsInColumn(int column) {
        int counter = 0;
        for (int i = this.numberOfRows - 1; i > 0; i--) {
            if (this.tiles[i][column] != null) {
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
        this.listener.imageModified(this.image);
    }


    public Tile[][] getTiles() {
        return tiles;
    }

    public void setSingleTiles(Tile tile, int i, int j) {
        this.tiles[i][j] = tile;
    }

    public Tile getSingleTile(int i, int j) { // funzione estrazione singola Tile selezionata
        return tiles[i][j];
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public int getNumberOfTilesInColumn(int column) {
        int counter = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] != null)
                counter++;
        }
        return counter;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public boolean isRowFull(int row) {
        for (int column = 0; column < this.numberOfColumns; column++) {
            if (this.tiles[row][column] == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnFull(int column) {
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] == null) {
                return false;
            }
        }
        return true;
    }

    public int score() {
        return 1;
    }

    public void setTiles(Tile[][] tiles) { // funzione estrazione singola Tile selezionata
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        String output = "  ";
        for(int column=0;column<this.numberOfColumns;column++) {
            output += column+1 + " ";
        }
        output += "\n";
        for (int row = 0; row < this.numberOfRows; row++) {
            output += "[ ";
            for (int column = 0; column < this.numberOfColumns; column++) {
                Tile currentTile = this.tiles[row][column];
                output = ((currentTile == null || currentTile.getColor() == null) ? output + "0 " : output + currentTile.getColor() + " ");
            }
            output += "] "+(row+1) +"\n";
        }
        return output.substring(0,output.length()-1);
    }
}

