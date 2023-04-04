package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class ConsecutiveTilesPatternGoal extends CommonGoal {
    int consecutive;
    public ConsecutiveTilesPatternGoal() {
        super();
        this.consecutive = 0;
    }
    public ConsecutiveTilesPatternGoal(int imageID, int patternRepetition, CheckType type, int consecutive) {
        super();
        this.consecutive = consecutive;
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
        int groupCounter=0;
        int generalCounter=0;
        for (int g = 2; g <= group; g++) {
            for (int r = 0; r < b.getNumRows(); r++) {
                for (int c = 0; c < b.getNumColumns(); c++) {
                    if (supportMatrix[r][c] == g) {
                        groupCounter++;
                    }
                }
            }
            if(groupCounter>consecutive){
                generalCounter++;
            }
            groupCounter=0;
//            if(groupCounter=patternRepetition){
//            }
        }
        return generalCounter;
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
