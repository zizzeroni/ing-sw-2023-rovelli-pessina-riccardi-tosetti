package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class GoalPattern_1_3_4 extends CommonGoal {
    int[][] positions;

    public GoalPattern_1_3_4() {
        super();
    }

    public GoalPattern_1_3_4(int imageID, int patternRepetition, CheckType type, int[][] positions) {
        super(imageID, patternRepetition, type, positions);
    }
    public int consecutiveColumn(int[][] positions){
        int consecutiveColumn = 1;
        for(int i=0; i< positions[0].length; i++){
            if(positions[0][i]==1){
                consecutiveColumn++;
            }
        }
        return consecutiveColumn;
    }

    //int righe = matrice.length;
    //int colonne =matrice[0].length;

//    public int consecutiveRows(int[][] positions){
//        int consecutiveRows = 0;
//
//        for(int i=0; i< positions.length; i++){
//            if(positions[i][0]==1){
//                consecutiveRows++;
//            }
//        }
//
//        return consecutiveRows;
//    }

    public int numberOfElement(int[][] positions){
        int numberOfElement = 0;
        for(int i=0; i< positions.length; i++){
            for(int j=0; j< positions[0].length; j++){
                if(positions[i][j]==1){
                    numberOfElement++;
                }
            }
        }
        return numberOfElement;
    }

    public int goalPattern(Bookshelf b) {


        int[][] support_matrix = new int[b.getNumRows()][b.getNumColumns()];

        for (int i = 0; i < b.getNumRows(); i++) {
            for (int j = 0; j < b.getNumColumns(); j++) {
                if (b.getSingleTile(i, j) == null) {
                    support_matrix[i][j] = 0;
                } else {
                    support_matrix[i][j] = 1;
                }
            }
        }

        int group = 1;

        for (int i = 0; i < b.getNumRows(); i++) {
            for (int j = 0; j < b.getNumColumns(); j++) {
                if (support_matrix[i][j] == 1) {
                    group++;
                    searchGroup(b, support_matrix, i, j, group, b.getSingleTile(i, j).getColor());
                }
            }
        }

//        for (int i = 0; i < b.getNumRows(); i++) {
//            for (int j = 0; j < b.getNumColumn(); j++) {
//
//                System.out.print(support_matrix[i][j] + " ");
//
//            }
//            System.out.println(" ");
//        }
//     Control if I have to do the 2x2 or the sequence of tiles

        //Here i control if there is the number of element that is required
        int counterGeneral = 0;
        int NumberOfCorrispective= 0;
        int CounterGroup=0;

        for (int g = 2; g <= group; g++) {
            for (int r=0; r<b.getNumRows();r++) {
                for (int c = 0; c < b.getNumColumns(); c++) {
                    if (support_matrix[r][c] == g) {
                        for (int k = 0; k < b.getNumRows(); k++) {
                            for (int h = 0; h < b.getNumColumns(); h++) {

                                if (positions[k][h] == 1 && ((r + k) < b.getNumRows()) && ((c + h) < b.getNumColumns()) && support_matrix[r + k][c + h] == g) {
                                    NumberOfCorrispective++;
                                }
                            }
                        }
                        if (NumberOfCorrispective == numberOfElement(positions)) {
                            CounterGroup++;
                        }
                        NumberOfCorrispective = 0;
                    }
                }
            }
            if(CounterGroup>0){
                counterGeneral++;
            }
            CounterGroup=0;
        }
        return counterGeneral;
    }

    private void searchGroup(Bookshelf b, int[][] support_matrix, int r, int c, int group, TileColor currentTileColor) {


        if ((support_matrix[r][c] == 1) && currentTileColor.equals(b.getSingleTile(r,c).getColor())) {
            support_matrix[r][c] = group;

            //Control superior Tile
            if(r!=0){
                searchGroup(b, support_matrix, r-1, c, group, currentTileColor);
            }

            if(c!=0){
                searchGroup(b, support_matrix, r, c-1, group, currentTileColor);
            }

            if(r!=b.getNumRows()-1){
                searchGroup(b, support_matrix, r+1, c, group, currentTileColor);
            }

            if(c!=b.getNumColumns()-1){
                searchGroup(b, support_matrix, r, c+1, group, currentTileColor);
            }
        }
    }


}


