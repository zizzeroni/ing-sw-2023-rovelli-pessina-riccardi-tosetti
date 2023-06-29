package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.BoardListener;
import it.polimi.ingsw.model.tile.Tile;

import java.util.List;

/**
 * This class represents the {@code Board} on which the {@code Game}'s {@code Tile}s are disposed.
 * It may vary in its size and form, depending on the number of {@code Player}s that will
 * be registered in the lobby when the game starts.
 *
 * @see Game
 * @see Board
 * @see Player
 * @see Tile
 */
public class Board {
    private transient BoardListener listener;
    private int numberOfUsableTiles;
    private final int numberOfColumns = 9;
    private final int numberOfRows = 9;
    private Tile[][] tiles;

    /**
     * Registers the {@code BoardListener} on the Board.
     *
     * @param listener the listener that will register on the {@code Board}.
     * @see BoardListener
     * @see java.net.http.WebSocket.Listener
     */
    public void registerListener(BoardListener listener) {
        this.listener = listener;
    }

    /**
     * Removes the {@code BoardListener} from the Board.
     *
     * @see BoardListener
     * @see java.net.http.WebSocket.Listener
     */
    public void removeListener() {
        this.listener = null;
    }

    /**
     * Class constructor.
     * Initializes all {@code Tile}s on the board.
     *
     * @see Tile
     */
    public Board() {
        this.numberOfUsableTiles = 0;
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++)
            for (int column = 0; column < this.numberOfColumns; column++)
                this.tiles[row][column] = new Tile();
    }

    /**
     * Initializes only the {@code Tile}s in the positions where there are ones in the {@code JsonBoardPattern},
     * and set non-usable tiles as tiles without a specified color.
     *
     * @param jsonBoardPattern pattern that contains the positions where the tiles can be inserted.
     * @see JsonBoardPattern
     * @see Tile
     */
    public Board(JsonBoardPattern jsonBoardPattern) {
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        this.setPattern(jsonBoardPattern);
    }

    /**
     * Initializes the number of usable tiles and the set of tiles that can ben placed on the Board.
     *
     * @param numberOfUsableTiles number of {@code Tile}s that are usable, set during the {@code Game}'s creation.
     * @param tiles               matrix with the considered tiles.
     * @see Tile
     * @see Game
     */
    public Board(int numberOfUsableTiles, Tile[][] tiles) {
        this.numberOfUsableTiles = numberOfUsableTiles;
        this.tiles = tiles;
    }

    /**
     * Adds the tiles selected by {@code Player}s in the positions previously chosen.
     *
     * @param tilesToAdd is the list of the selected {@code Tile}s.
     * @see Player
     * @see Tile
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
    }

    /**
     * The method searches in the board for "lonely" {@code Tile}s (without any nearby, adjacent tiles).
     *
     * @return if two or more nearby tiles are found, returns '0',
     * otherwise returns the number of "lonely" tiles.
     * @see Tile
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
     * The {@code Player} can take some {@code Tile}s from the board and keep playing.
     * When they are taken, selected tiles need to be removed from
     * their current position. This method implements tiles removal.
     *
     * @param coordinates are the coordinates of the tiles selected by a player to be removed.
     * @see Player
     * @see Tile
     */
    public void removeTiles(List<Coordinates> coordinates) {
        for (Coordinates coordinate : coordinates) {
            this.removeTile(coordinate.getX(), coordinate.getY());
        }
    }

    /**
     * Methods used to remove a {@code Tile} in a specific position from the {@code Board}.
     *
     * @param row    the row of the tile to be removed.
     * @param column the column of the tile to be removed.
     * @see Tile
     * @see Board
     */
    private void removeTile(int row, int column) {
        this.tiles[row][column] = null;
    }

    /**
     * Gets the number of usable {@code Tile}s.
     *
     * @return the number of usable tiles.
     * @see Tile
     */
    public int getNumberOfUsableTiles() {
        return this.numberOfUsableTiles;
    }

    /**
     * Sets the number of usable {@code Tile}s.
     *
     * @param numberOfUsableTiles the number of tiles that can be actually used during the {@code Game}.
     * @see Tile
     * @see Game
     */
    public void setNumberOfUsableTiles(int numberOfUsableTiles) {
        this.numberOfUsableTiles = numberOfUsableTiles;
    }

    /**
     * Getter used to retrieve the {@code Tile}s to be displaced on the board.
     *
     * @return the board's tile set.
     * @see Tile
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * Setter used to decide the {@code Tile}s to be displaced on the board.
     *
     * @param tiles matrix of the board
     * @see Tile
     */
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * Gets the number of rows of the {@code Board}.
     *
     * @return the {@code Board}'s number of rows.
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * Sets only the {@code Tile}s in the positions where there are ones in the {@code JsonBoardPattern},
     * also sets non-usable tiles as tiles without any specific color.
     *
     * @param boardPattern pattern that contains the positions where we need to insert the tiles.
     * @see Tile
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

    /**
     * Gets the number of rows of the {@code Board}.
     *
     * @return the {@code Board}'s number of rows.
     */
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    /**
     * Gets the {@code Tile} at the given coordinates,
     * expressed as rows and columns.
     *
     * @param row    is the first coordinate.
     * @param column is the second coordinate.
     * @return the Tile at the specified position on the {@code Board}.
     * @see Tile
     */
    public Tile getSingleTile(int row, int column) {
        return this.tiles[row][column];
    }

    /**
     * Sets the value of the {@code Tile} at the given coordinates,
     * expressed as rows and columns.
     *
     * @param row    is the first coordinate.
     * @param column is the second coordinate.
     * @param tile   is the tile's value to be set by the method.
     */
    public void setSingleTile(int row, int column, Tile tile) {
        this.tiles[row][column] = tile;
    }

    /**
     * Used to displays a simple representation of the {@code Board}.
     *
     * @return a representation of the current board, empty {@code Tile}s as indicated as '0'.
     * @see Tile
     */
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
