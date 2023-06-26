package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Board;

import java.io.Serializable;

/**
 * This class represents the {@code BoardView} through the {@code Serializable} interface.
 * All the {@code Player}s always 'see' only the implementation of the {@code View} of the relative {@code Board},
 * and are sensible to the inherent modifies.
 * The class also contains a series of getters to access the number of
 * rows/columns and to retrieve the selected tiles from the {@code Board},
 * given the current number of active players.
 *
 * @see Serializable
 */
public class BoardView implements Serializable {
    //private final Board boardModel;
    private final int numberOfUsableTiles;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final TileView[][] tiles;

    /**
     * Class constructor.
     * Used to associate the representation of the {@code Board} in the {@code BoardView}
     * with logic in the {@code BoardModel} (passed as parameter).
     *
     * @param boardModel the model of the {@code Board}.
     * @see Board
     */
    public BoardView(Board boardModel) {
        this.numberOfUsableTiles = boardModel.getNumberOfUsableTiles();
        this.numberOfColumns = boardModel.getNumberOfColumns();
        this.numberOfRows = boardModel.getNumberOfRows();
        this.tiles = new TileView[boardModel.getNumberOfRows()][boardModel.getNumberOfColumns()];
        for (int row = 0; row < boardModel.getNumberOfRows(); row++) {
            for (int column = 0; column < boardModel.getNumberOfColumns(); column++) {
                this.tiles[row][column] = (boardModel.getSingleTile(row, column) != null ? new TileView(boardModel.getSingleTile(row, column)) : null);
            }
        }
    }

    public int getNumberOfUsableTiles() {
        return this.numberOfUsableTiles;
    }

    /**
     * Returns the set of tiles currently on the board.
     *
     * @return the {@code TileView} of the given {@code Board}.
     */
    public TileView[][] getTiles() {
        return this.tiles;
    }

    /**
     * Returns the number of columns in the {@code Board}.
     *
     * @return the number of columns.
     * @see Board
     */
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    /**
     * Returns the number of rows in the {@code Board}.
     *
     * @return the number of rows.
     * @see Board
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * This method is used to display the result of the user's interactions
     * during the {@code Game} (those linked with the {@code Board}).
     *
     * @return the current display of the board
     * @see it.polimi.ingsw.model.Game
     * @see Board
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
                TileView currentTile = this.tiles[row][column];
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
