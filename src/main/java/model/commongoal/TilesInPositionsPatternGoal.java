package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class TilesInPositionsPatternGoal extends CommonGoal {
    int[][] positions;
    public TilesInPositionsPatternGoal() {
        super();
        this.positions = new int[0][0];
    }
    public TilesInPositionsPatternGoal(int imageID, int patternRepetition, CheckType type, int[][] positions) {
        super(imageID, patternRepetition, type);
        this.positions = positions;
    }
    public int numberOfElement(){
        int numberOfElement = 0;
        for(int i=0; i< this.positions.length; i++){
            for(int j=0; j< this.positions[0].length; j++){
                if(this.positions[i][j]==1){
                    numberOfElement++;
                }
            }
        }
        return numberOfElement;
    }
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {


        int[][] supportMatrix = new int[bookshelf.getNumRows()][bookshelf.getNumColumns()];

        for (int i = 0; i < bookshelf.getNumRows(); i++) {
            for (int j = 0; j < bookshelf.getNumColumns(); j++) {
                if (bookshelf.getSingleTile(i, j) == null) {
                    supportMatrix[i][j] = 0;
                } else {
                    supportMatrix[i][j] = 1;
                }
            }
        }

        int group = 1;

        for (int i = 0; i < bookshelf.getNumRows(); i++) {
            for (int j = 0; j < bookshelf.getNumColumns(); j++) {
                if (supportMatrix[i][j] == 1) {
                    group++;
                    searchGroup(bookshelf, supportMatrix, i, j, group, bookshelf.getSingleTile(i, j).getColor());
                }
            }
        }

            //Control if I have to do the 2x2 or the sequence of tiles

        //Here I control if there is the number of element that is required
        int counterGeneral = 0;
        int numberOfCorrispective= 0;
        int counterGroup=0;

        for (int g = 2; g <= group; g++) {
            for (int r=0; r < bookshelf.getNumRows() - this.positions.length + 1;r++) {
                for (int c = 0; c < bookshelf.getNumColumns() - this.positions[0].length + 1; c++) {
                    if (supportMatrix[r][c] == g) {
                        for (int k = 0; k < this.positions.length; k++) {
                            for (int h = 0; h < this.positions[0].length; h++) {

                                if (this.positions[k][h] == 1 && ((r + k) < bookshelf.getNumRows()) && ((c + h) < bookshelf.getNumColumns()) && supportMatrix[r + k][c + h] == g) {
                                    numberOfCorrispective++;
                                }
                            }
                        }
                        if (numberOfCorrispective == numberOfElement()) {
                            counterGroup++;
                        }
                        numberOfCorrispective = 0;
                    }
                }
            }
            if(counterGroup>0){
                counterGeneral++;
            }
            counterGroup=0;
        }
        return counterGeneral;
    }

    private void searchGroup(Bookshelf bookshelf, int[][] supportMatrix, int row, int column, int group, TileColor currentTileColor) {


        if ((supportMatrix[row][column] == 1) && currentTileColor.equals(bookshelf.getSingleTile(row,column).getColor())) {
            supportMatrix[row][column] = group;

            //Control superior Tile
            if(row!=0){
                searchGroup(bookshelf, supportMatrix, row-1, column, group, currentTileColor);
            }

            if(column!=0){
                searchGroup(bookshelf, supportMatrix, row, column-1, group, currentTileColor);
            }

            if(row!=bookshelf.getNumRows()-1){
                searchGroup(bookshelf, supportMatrix, row+1, column, group, currentTileColor);
            }

            if(column!=bookshelf.getNumColumns()-1){
                searchGroup(bookshelf, supportMatrix, row, column+1, group, currentTileColor);
            }
        }
    }
}


