package it.polimi.ingsw.model;

import java.util.List;

import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.listeners.GameListener;
import it.polimi.ingsw.model.tile.Tile;

public class Board {
    private BoardListener listener;
    private int numberOfUsableTiles;

    private final int numberOfColumns = 9;

    private final int numberOfRows = 9;

    private Tile[][] tiles;

    public void registerListener(BoardListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    public Board() {
        this.numberOfUsableTiles = 0;
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++)
            for (int column = 0; column < this.numberOfColumns; column++)
                this.tiles[row][column] = new Tile();
    }

    public Board(JsonBoardPattern jsonBoardPattern) {
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        this.numberOfUsableTiles = 0;

        int[][] pattern = jsonBoardPattern.pattern();

        for (int row = 0; row < pattern.length; row++) {
            for (int column = 0; column < pattern[0].length; column++) {
                if (pattern[row][column] == 1) {
                    this.numberOfUsableTiles++;
                } else {
                    //set non-usable tiles as tiles without color
                    this.tiles[row][column] = new Tile();
                }
            }
        }
    }

    public Board(int numberOfUsableTiles, Tile[][] tiles) {
        this.numberOfUsableTiles = numberOfUsableTiles;
        this.tiles = tiles;
    }

    public void addTiles(List<Tile> tilesToAdd) {
        if (tilesToAdd.size() == 0) {
            return;
        }
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if (this.tiles[row][column] == null) {
                    this.tiles[row][column] = tilesToAdd.remove(0);
                }
            }
        }
        if(listener!=null) {
            listener.addedTilesToBoard(this);
        }
    }

    public int numberOfTilesToRefill() { //returns the number of tiles required for refill. 0 if not needed
        int usableTilesStillAvailable = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                //if the current tile and one of his neighbours (right or bottom) are not null, then there is no need to refill
                if (this.tiles[row][column] != null && this.tiles[row][column].getColor() != null) {
                    if ((this.tiles[row][column + 1] != null && this.tiles[row][column + 1].getColor() != null) || (this.tiles[row + 1][column] != null && this.tiles[row + 1][column].getColor() != null)) {
                        return 0;
                    }
                    //we keep the count of the number of tiles that are not null
                    usableTilesStillAvailable++;
                }
            }
        }
        return this.numberOfUsableTiles - usableTilesStillAvailable;
    }

    public void removeTiles(Tile[] tilesToRemove, int[] positions) {
        int i = 0;
        for (Tile tile : tilesToRemove) {
            this.tiles[positions[i]][positions[i + 1]] = null;
            i += 2;
        }
        this.listener.removedTilesFromBoard(this);
    }

    public int getNumberOfUsableTiles() {
        return this.numberOfUsableTiles;
    }

    public void setNumberOfUsableTiles(int numberOfUsableTiles) {
        this.numberOfUsableTiles = numberOfUsableTiles;
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public Tile getSingleTile(int x, int y) {
        return tiles[x][y];
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
            output += "] " + (row+1) +"\n";
        }
        return output.substring(0,output.length()-1);
    }
}
