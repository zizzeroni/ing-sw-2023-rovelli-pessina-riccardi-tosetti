package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class FiveXShapePatternGoal extends CommonGoal {
    public FiveXShapePatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf B) {
            for(int i=1; i<B.getNumRows(); i++){
                for(int j=1; j<B.getNumColumns(); j++){

                    TileColor currentTileColor = B.getSingleTile(i, j).getColor();

                    if(currentTileColor==B.getSingleTile(i+1, j+1).getColor()
                    && currentTileColor==B.getSingleTile(i-1, j-1).getColor()
                    && currentTileColor==B.getSingleTile(i+1, j-1).getColor()
                    && currentTileColor==B.getSingleTile(i-1, j+1).getColor()){
                        return 1;
                    }
                }
            }
        return 0;
    }


}
