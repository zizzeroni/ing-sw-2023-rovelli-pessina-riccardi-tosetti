package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.tile.Tile;

import java.util.List;

public class Board {
    private transient BoardListener listener;
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

    //Initialize all tiles on the board
    public Board() {
        this.numberOfUsableTiles = 0;
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++)
            for (int column = 0; column < this.numberOfColumns; column++)
                this.tiles[row][column] = new Tile();
    }

    /**
     * @param jsonBoardPattern
     */
    /*Initialize only the tiles in the positions where there are ones in the jsonBoardPattern, and set non-usable tiles as tiles without color
    @param jsonBoardPattern pattern that contains the positions where we need to insert the tiles
    */
    public Board(JsonBoardPattern jsonBoardPattern) {
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        this.setPattern(jsonBoardPattern);
    }

    /**
     * @param numberOfUsableTiles
     * @param tiles
     */
    /*
    @param numberOfUsableTiles number of tiles that are usable
    @param tiles
     */
    public Board(int numberOfUsableTiles, Tile[][] tiles) {
        this.numberOfUsableTiles = numberOfUsableTiles;
        this.tiles = tiles;
    }

    /**
    Add the tiles selected by players in the positions previously chosen
    @param tilesToAdd is the list of the selected tiles
     */
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
        /*if (this.listener != null) {
            this.listener.addedTilesToBoard(this);
        } else {
            System.err.println("Bookshelf's listener is NULL!");
        }*/
    }

    /**
     * Search in the board for "lonely" tiles (whitout any nearby tiles)
     *
     * @return if 2 or more nearby tiles are found, returns '0', otherwise returns the number of "lonely" tiles
     */
    public int numberOfTilesToRefill() { //returns the number of tiles required for refill. 0 if not needed
        int usableTilesStillAvailable = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                //if the current tile and one of his neighbours (right or bottom) are not null, then there is no need to refill

                if (this.tiles[row][column] != null && this.tiles[row][column].getColor() != null) {
                    if (row != this.numberOfRows - 1) {
                        if (this.tiles[row + 1][column] != null && this.tiles[row + 1][column].getColor() != null) {
                            return 0;
                        }
                    }
                    if (column != this.numberOfColumns - 1) {
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

    /**
     * The player can take some tiles from the board and keep playing.
     * When they are taken, selected tiles need to be removed from
     * their current position. This method implements tiles removal.
    @param coordinates are the coordinates of the tiles selected by a player to be removed.
     */
    public void removeTiles(List<Coordinates> coordinates) {
        for (Coordinates coordinate : coordinates) {
            this.removeTile(coordinate.getX(), coordinate.getY());
        }

        /*if (this.listener != null) {
            this.listener.removedTilesFromBoard(this);
        } else {
            System.err.println("Board's listener is NULL!");
        }*/
    }

    /**
     * @param row
     * @param column
     */
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

    /**
     * Set only the tiles in the positions where there are ones in the jsonBoardPattern, and set non-usable tiles as tiles without color
        @param boardPattern pattern that contains the positions where we need to insert the tiles
        */
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
