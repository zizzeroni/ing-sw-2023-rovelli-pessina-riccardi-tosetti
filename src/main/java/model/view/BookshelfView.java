package model.view;

import model.Bookshelf;

public class BookshelfView {
    private final Bookshelf bookshelfModel;

    public BookshelfView(Bookshelf bookshelfModel) {
        this.bookshelfModel = bookshelfModel;
    }

    public String getImage() {
        return this.bookshelfModel.getImage();
    }
    public TileView[][] getTiles() {
        TileView[][] temp = new TileView[this.bookshelfModel.getNumberOfRows()][this.bookshelfModel.getNumberOfColumns()];
        for(int r = 0; r < this.bookshelfModel.getNumberOfRows(); r++) {
            for(int c = 0; c < this.bookshelfModel.getNumberOfColumns(); c++) {
                temp[r][c] = new TileView(this.bookshelfModel.getSingleTile(r,c));
            }
        }
        return temp;
    }
    public TileView getSingleTile(int i, int j){ // funzione estrazione singola Tile selezionata
        return new TileView(this.bookshelfModel.getSingleTile(i,j));
    }
    public int getNumColumns() {
        return this.bookshelfModel.getNumberOfColumns();
    }
    public int getNumElemColumn(int c){
        int counter = 0;
        for(int i = 0; i<this.bookshelfModel.getNumberOfRows(); i++){
            if(this.bookshelfModel.getSingleTile(i, c)!=null)
                counter++;
        }
        return counter;
    }
    public int getNumRows() {
        return this.bookshelfModel.getNumberOfRows();
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
        for (int i = 0; i < this.bookshelfModel.getNumberOfRows(); i++) {
            if (this.bookshelfModel.getSingleTile(i,c) == null) {
                return false;
            }
        }
        return true;
    }
}
