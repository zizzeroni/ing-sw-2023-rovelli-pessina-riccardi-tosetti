package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.Tile;

public class BookshelfView {
    //private final Bookshelf bookshelfModel;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final String image;
    private final TileView[][] tiles;
    public BookshelfView(Bookshelf bookshelfModel) {
        this.tiles = new TileView[bookshelfModel.getNumberOfRows()][bookshelfModel.getNumberOfColumns()];

        this.numberOfColumns=bookshelfModel.getNumberOfColumns();
        this.numberOfRows=bookshelfModel.getNumberOfRows();
        this.image=bookshelfModel.getImage();
        for (int row = 0; row < bookshelfModel.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelfModel.getNumberOfColumns(); column++) {
                this.tiles[row][column] = (bookshelfModel.getSingleTile(row, column) != null ? new TileView(bookshelfModel.getSingleTile(row, column)) : null);
            }
        }
    }

    public String getImage() {
        return this.image;
    }

    public TileView[][] getTiles() {
        return this.tiles;
    }

    public TileView getSingleTile(int row, int column) { // funzione estrazione singola Tile selezionata
        return this.tiles[row][column];
    }

    public int getNumberOfEmptyCellsInColumn(int column) {
        int counter = 0;
        for (int i = this.numberOfRows - 1; i > 0; i--) {
            if (this.tiles[i][column] != null) {
                return counter;
            }
            counter++;
        }
        return counter;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
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
        return this.numberOfRows;
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
    public int score() {
        return 1;
    }
}
