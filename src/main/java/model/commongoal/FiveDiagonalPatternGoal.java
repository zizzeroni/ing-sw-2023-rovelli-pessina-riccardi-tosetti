package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class FiveDiagonalPatternGoal extends CommonGoal {
    public FiveDiagonalPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf B) {
        int j=0;
            for(int i=0; i<=B.getNumRows(); i++){
                TileColor currentTileColor = B.getSingleTile(i, j).getColor();
                if(i==0 || i==1 ){


                    if(currentTileColor==B.getSingleTile(i+1, j+1 ).getColor()
                       && currentTileColor==B.getSingleTile(i+2, j+2).getColor()
                       && currentTileColor==B.getSingleTile(i+3, j+3).getColor()
                       && currentTileColor==B.getSingleTile(i+4, j+4).getColor()){
                        return 1;
                    }
                    }else if(i==B.getNumRows()-1 || i==B.getNumRows()){

                    if(currentTileColor==B.getSingleTile(i-1, j+1).getColor()
                            && currentTileColor==B.getSingleTile(i-2, j+2).getColor()
                            && currentTileColor==B.getSingleTile(i-3, j+3).getColor()
                            && currentTileColor==B.getSingleTile(i-4, j+4).getColor()){
                        return 1;
                    }
                }



            }
            return 0;
        }
    }
