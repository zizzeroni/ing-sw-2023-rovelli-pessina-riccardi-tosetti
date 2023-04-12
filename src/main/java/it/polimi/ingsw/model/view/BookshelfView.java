package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Bookshelf;

public class BookshelfView {
    private final Bookshelf bookshelfModel;

    public BookshelfView(Bookshelf bookshelfModel) {
        this.bookshelfModel = bookshelfModel;
    }

    public String getImage() {
        return this.bookshelfModel.getImage();
    }

    public TileView[][] getTiles() {
        TileView[][] tileViewsTiles = new TileView[this.bookshelfModel.getNumberOfRows()][this.bookshelfModel.getNumberOfColumns()];
        for (int row = 0; row < this.bookshelfModel.getNumberOfRows(); row++) {
            for (int column = 0; column < this.bookshelfModel.getNumberOfColumns(); column++) {
                tileViewsTiles[row][column] = new TileView(this.bookshelfModel.getSingleTile(row, column));
            }
        }
        return tileViewsTiles;
    }

    public TileView getSingleTile(int row, int column) { // funzione estrazione singola Tile selezionata
        return new TileView(this.bookshelfModel.getSingleTile(row, column));
    }

    public int getNumberOfEmptyCellsInColumn(int column) {
        return this.bookshelfModel.getNumberOfEmptyCellsInColumn(column);
    }

    public int getNumberOfColumns() {
        return this.bookshelfModel.getNumberOfColumns();
    }

    public int getNumberOfTilesInColumn(int column) {
        return this.bookshelfModel.getNumberOfTilesInColumn(column);
    }

    public int getNumberOfRows() {
        return this.bookshelfModel.getNumberOfRows();
    }

    public boolean isRowFull(int row) {
        return this.bookshelfModel.isRowFull(row);
    }

    public boolean isColumnFull(int column) {
        return this.bookshelfModel.isColumnFull(column);
    }

    @Override
    public String toString() {
        return this.bookshelfModel.toString();
    }

}
