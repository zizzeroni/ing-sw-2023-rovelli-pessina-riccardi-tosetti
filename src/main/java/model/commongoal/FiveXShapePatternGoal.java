package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class FiveXShapePatternGoal extends CommonGoal {
    public FiveXShapePatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {

        //[1,0,1,0,0
        // 0,1,0,0,0
        // 1,0,1,0,0
        // 0,0,0,0,0
        // 0,0,0,0,0
        // 0,0,0,0,0]

        int count = 0;
        for(int h=1; h<b.getNumColumns()-1; h++){
            for(int k=1; k<b.getNumRows()-1; k++) {
                if(b.getSingleTile(k, h)!=null) {
                    TileColor currentTileColor = b.getSingleTile(k, h).getColor();
                    if (currentTileColor == b.getSingleTile(k + 1, h + 1).getColor() &&
                            currentTileColor == b.getSingleTile(k - 1, h - 1).getColor() &&
                            currentTileColor == b.getSingleTile(k + 1, h - 1).getColor() &&
                            currentTileColor == b.getSingleTile(k - 1, h + 1).getColor()) {
                        count++;
                    }
                }
            }
        }
        return count;

        /*for(int i=1; i<b.getNumRows()-1; i++){
                for(int j = 1; j<b.getNumColumn()-1; j++){

                    TileColor currentTileColor = b.getSingleTile(i, j).getColor();

                    if(currentTileColor==b.getSingleTile(i+1, j+1).getColor()
                    && currentTileColor==b.getSingleTile(i-1, j-1).getColor()
                    && currentTileColor==b.getSingleTile(i+1, j-1).getColor()
                    && currentTileColor==b.getSingleTile(i-1, j+1).getColor()){
                        return 1;
                    }
                }
            }
        return 0;*/
    }


}
