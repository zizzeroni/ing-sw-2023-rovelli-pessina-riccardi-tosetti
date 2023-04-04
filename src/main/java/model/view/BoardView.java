package model.view;

import model.Board;
import model.tile.Tile;

public class BoardView {
    private final Board boardModel;

    public BoardView(Board boardModel) {
        this.boardModel = boardModel;
    }

    public int getMaxNumTiles() {
        return this.boardModel.getMaxNumTiles();
    }
    public TileView[][] getTiles() {
        TileView[][] temp = new TileView[this.boardModel.getNumRows()][this.boardModel.getNumColumns()];
        for(int r=0;r<this.boardModel.getNumRows();r++) {
            for(int c=0;c<this.boardModel.getNumColumns();c++) {
                temp[r][c]=new TileView(this.boardModel.getSingleTile(r,c));
            }
        }
        return temp;
    }
    public int getNumColumns() {
        return this.boardModel.getNumColumns();
    }
    public int getNumRows() {
        return this.boardModel.getNumRows();
    }
}
