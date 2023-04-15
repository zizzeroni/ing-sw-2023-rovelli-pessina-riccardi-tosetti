package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Board;

public class BoardView {
    private final Board boardModel;

    public BoardView(Board boardModel) {
        this.boardModel = boardModel;
    }

    public int getNumberOfUsableTiles() {
        return this.boardModel.getNumberOfUsableTiles();
    }

    public TileView[][] getTiles() {
        TileView[][] tileViewTiles = new TileView[this.boardModel.getNumberOfRows()][this.boardModel.getNumberOfColumns()];
        for (int row = 0; row < this.boardModel.getNumberOfRows(); row++) {
            for (int column = 0; column < this.boardModel.getNumberOfColumns(); column++) {
                tileViewTiles[row][column] = new TileView(this.boardModel.getSingleTile(row, column));
            }
        }
        return tileViewTiles;
    }

    public int getNumberOfColumns() {
        return this.boardModel.getNumberOfColumns();
    }

    public int getNumberOfRows() {
        return this.boardModel.getNumberOfRows();
    }

    @Override
    public String toString() {
        return this.boardModel.toString();
    }
}
