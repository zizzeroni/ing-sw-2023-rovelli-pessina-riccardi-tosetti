package it.polimi.ingsw.model;

import java.util.List;
import it.polimi.ingsw.model.listeners.BoardListener;
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
        this.setPattern(jsonBoardPattern);
    }

    public Board(int numberOfUsableTiles, Tile[][] tiles) {
        this.numberOfUsableTiles = numberOfUsableTiles;
        this.tiles = tiles;
    }

    //TODO: Chiedere se è da spostare nel controller
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
        if (this.listener != null) {
            this.listener.addedTilesToBoard(this);
        }
    }

    //TODO: Chiedere se è da spostare nel controller
    public int numberOfTilesToRefill() { //returns the number of tiles required for refill. 0 if not needed
        int usableTilesStillAvailable = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                //if the current tile and one of his neighbours (right or bottom) are not null, then there is no need to refill
                if (this.tiles[row][column] != null && this.tiles[row][column].getColor() != null) {
                    if(row != this.numberOfRows - 1) {
                        if (this.tiles[row + 1][column] != null && this.tiles[row + 1][column].getColor() != null) {
                            return 0;
                        }
                    }
                    if(column != this.numberOfColumns - 1) {
                        if (this.tiles[row][column + 1] != null && this.tiles[row][column + 1].getColor() != null) {
                            return 0;
                        }
                    }
                    //we keep the count of the number of tiles that are not null
                    usableTilesStillAvailable++;
                }
            }
        }
        return this.numberOfUsableTiles - usableTilesStillAvailable;
    }

    //TODO: Chiedere se è da spostare nel controller
    public void removeTiles(List<Coordinates> coordinates) {
        for (Coordinates coordinate: coordinates) {
            this.removeTile(coordinate.getX(), coordinate.getY());
        }

        if (this.listener != null) {
            this.listener.removedTilesFromBoard(this);
        }
    }

    private void removeTile(int row, int column) {
        this.tiles[row][column] = null;
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

    public void setPattern(JsonBoardPattern boardPattern) {
        this.numberOfUsableTiles = 0;
        int[][] pattern = boardPattern.pattern();

        for (int row = 0; row < pattern.length; row++) {
            for (int column = 0; column < pattern[0].length; column++) {
                if (pattern[row][column] == 1) {
                    this.numberOfUsableTiles++;
                    this.tiles[row][column] = null;
                } else {
                    //set non-usable tiles as tiles without color
                    this.tiles[row][column] = new Tile();
                }
            }
        }
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public Tile getSingleTile(int row, int column) {
        return this.tiles[row][column];
    }

    public void setSingleTile(int row, int column, Tile tile) {
        this.tiles[row][column] = tile;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("    ");
        for (int column = 0; column < this.numberOfColumns; column++) {
            output.append(column + 1).append(" ");
        }
        output.append("\n");
        for (int row = 0; row < this.numberOfRows; row++) {
            output.append(row + 1).append(" [ ");
            for (int column = 0; column < this.numberOfColumns; column++) {
                Tile currentTile = this.tiles[row][column];
                if (currentTile == null || currentTile.getColor() == null) {
                    output.append("0 ");
                } else {
                    output.append(currentTile.getColor()).append(" ");
                }
            }
            output.append("] \n");
        }
        return output.substring(0, output.length() - 1);
    }
}
