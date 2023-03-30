package model.view;

import model.Bookshelf;
import model.tile.Tile;

import java.util.concurrent.TimeoutException;

public class BookshelfView {
    private final Bookshelf bookshelfModel;

    public BookshelfView(Bookshelf bookshelfModel) {
        this.bookshelfModel = bookshelfModel;
    }

    public String getImage() {
        return this.bookshelfModel.getImage();
    }
    public TileView[][] getTiles() {
        TileView[][] temp = new TileView[this.bookshelfModel.getNumRows()][this.bookshelfModel.getNumColumns()];
        for(int r = 0; r < this.bookshelfModel.getNumRows(); r++) {
            for(int c = 0; c < this.bookshelfModel.getNumColumns(); c++) {
                temp[r][c] = new TileView(this.bookshelfModel.getSingleTile(r,c));
            }
        }
        return temp;
    }
    public TileView getSingleTile(int i, int j){ // funzione estrazione singola Tile selezionata
        return new TileView(this.bookshelfModel.getSingleTile(i,j));
    }
    public int getNumColumns() {
        return this.bookshelfModel.getNumColumns();
    }
    public int getNumElemColumn(int c){
        int counter = 0;
        for(int i = 0; i<this.bookshelfModel.getNumRows(); i++){
            if(this.bookshelfModel.getSingleTile(i, c)!=null)
                counter++;
        }
        return counter;
    }
    public int getNumRows() {
        return this.bookshelfModel.getNumRows();
    }
    public boolean isRowFull(int r) {
        for (int i = 0; i < this.getNumColumns(); i++) {
            if (this.bookshelfModel.getSingleTile(r,i) == null) {
                return false;
            }
        }
        return true;
    }

    public boolean isColumnFull(int c) {
        for (int i = 0; i < this.bookshelfModel.getNumRows(); i++) {
            if (this.bookshelfModel.getSingleTile(i,c) == null) {
                return false;
            }
        }
        return true;
    }
}
