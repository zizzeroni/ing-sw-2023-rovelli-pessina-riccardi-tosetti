package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class GoalPattern_1_3_4 extends CommonGoal {

    public int goalPattern(Bookshelf b) {
        int[][] support_matrix = new int[b.getNumRows()][b.getNumColumn()];

        for (int i = 0; i < b.getNumRows(); i++) {
            for (int j = 0; j < b.getNumColumn(); j++) {
                if (b.getSingleTile(i, j) == null) {
                    support_matrix[i][j] = 0;
                } else {
                    support_matrix[i][j] = 1;
                }
            }
        }

        int group = 1;

        for (int i = 0; i < b.getNumRows(); i++) {
            for (int j = 0; j < b.getNumColumn(); j++) {
                if(support_matrix[i][j]==1){
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

       int n = 2; //Parametro letto
       int counterGeneral=0;
       int singleGroupCounter=0;
       for(int i=2; i<=group; i++){
           for (int k = 0; k < b.getNumRows(); k++) {
               for (int h = 0; h < b.getNumColumn(); h++) {
                   if(support_matrix[k][h]==i){
                       singleGroupCounter++;
                   }
               }
           }
           if(singleGroupCounter>=n){
               counterGeneral++;
           }
           singleGroupCounter=0;
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

            if(c!=b.getNumColumn()-1){
                searchGroup(b, support_matrix, r, c+1, group, currentTileColor);
            }
        }
    }


}


