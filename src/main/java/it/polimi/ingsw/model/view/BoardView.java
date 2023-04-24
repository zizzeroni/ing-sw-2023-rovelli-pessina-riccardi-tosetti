package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.tile.Tile;

import java.io.Serializable;

public class BoardView implements Serializable {
    //private final Board boardModel;
    private final int numberOfUsableTiles;
    private final int numberOfColumns;
    private final int numberOfRows;
    private TileView[][] tiles;

    public BoardView(Board boardModel) {
        this.tiles = new TileView[boardModel.getNumberOfRows()][boardModel.getNumberOfColumns()];

        this.numberOfUsableTiles = boardModel.getNumberOfUsableTiles();
        this.numberOfColumns = boardModel.getNumberOfColumns();
        this.numberOfRows = boardModel.getNumberOfRows();
        for (int row = 0; row < boardModel.getNumberOfRows(); row++) {
            for (int column = 0; column < boardModel.getNumberOfColumns(); column++) {
                this.tiles[row][column] = (boardModel.getSingleTile(row, column) != null ? new TileView(boardModel.getSingleTile(row, column)) : null);
            }
        }
    }

    public int getNumberOfUsableTiles() {
        return this.numberOfUsableTiles;
    }

    public TileView[][] getTiles() {
        return this.tiles;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    @Override
    public String toString() {
        String output = "    ";
        for (int column = 0; column < this.numberOfColumns; column++) {
            output += column + 1 + " ";
        }
        output += "\n";
        for (int row = 0; row < this.numberOfRows; row++) {
            output += (row + 1) + " [ ";
            for (int column = 0; column < this.numberOfColumns; column++) {
                TileView currentTile = this.tiles[row][column];
                output = ((currentTile == null || currentTile.getColor() == null) ? output + "0 " : output + currentTile.getColor() + " ");
            }
            output += "] " + "\n";
        }
        return output.substring(0, output.length() - 1);
    }
}
