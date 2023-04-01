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
    public int goalPattern(Bookshelf b) {


        int[][] supportMatrix = new int[b.getNumRows()][b.getNumColumns()];

        for (int i = 0; i < b.getNumRows(); i++) {
            for (int j = 0; j < b.getNumColumns(); j++) {
                if (b.getSingleTile(i, j) == null) {
                    supportMatrix[i][j] = 0;
                } else {
                    supportMatrix[i][j] = 1;
                }
            }
        }

        int group = 1;

        for (int i = 0; i < b.getNumRows(); i++) {
            for (int j = 0; j < b.getNumColumns(); j++) {
                if (supportMatrix[i][j] == 1) {
                    group++;
                    searchGroup(b, supportMatrix, i, j, group, b.getSingleTile(i, j).getColor());
                }
            }
        }
//        for (int i = 0; i < b.getNumRows(); i++) {
//            for (int j = 0; j < b.getNumColumn(); j++) {
//
//                System.out.print(supportMatrix[i][j] + " ");
//
//            }
//            System.out.println(" ");
//        }
//     Control if I have to do the 2x2 or the sequence of tiles

        //Here I control if there is the number of element that is required
        int counterGeneral = 0;
        int numberOfCorrispective= 0;
        int counterGroup=0;

        for (int g = 2; g <= group; g++) {
            for (int r=0; r < b.getNumRows() - this.positions.length + 1;r++) {
                for (int c = 0; c < b.getNumColumns() - this.positions[0].length + 1; c++) {
                    if (supportMatrix[r][c] == g) {
                        for (int k = 0; k < this.positions.length; k++) {
                            for (int h = 0; h < this.positions[0].length; h++) {

                                if (this.positions[k][h] == 1 && ((r + k) < b.getNumRows()) && ((c + h) < b.getNumColumns()) && supportMatrix[r + k][c + h] == g) {
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

    private void searchGroup(Bookshelf b, int[][] supportMatrix, int r, int c, int group, TileColor currentTileColor) {


        if ((supportMatrix[r][c] == 1) && currentTileColor.equals(b.getSingleTile(r,c).getColor())) {
            supportMatrix[r][c] = group;

            //Control superior Tile
            if(r!=0){
                searchGroup(b, supportMatrix, r-1, c, group, currentTileColor);
            }

            if(c!=0){
                searchGroup(b, supportMatrix, r, c-1, group, currentTileColor);
            }

            if(r!=b.getNumRows()-1){
                searchGroup(b, supportMatrix, r+1, c, group, currentTileColor);
            }

            if(c!=b.getNumColumns()-1){
                searchGroup(b, supportMatrix, r, c+1, group, currentTileColor);
            }
        }
    }
}


