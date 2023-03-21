package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class FiveDiagonalPatternGoal extends CommonGoal {
    public FiveDiagonalPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {
        int tempj=0;
        int tempi=0;

        for(int j = 0; j<b.getNumColumn()-3; j++) {
            for (int i = 0; i <= b.getNumRows(); i++) {


                TileColor currentTileColor = b.getSingleTile(i, j).getColor();
                if (i<b.getNumRows()-3) {

                    if (currentTileColor == b.getSingleTile(i + 1, j + 1).getColor()
                            && currentTileColor == b.getSingleTile(i + 2, j + 2).getColor()
                            && currentTileColor == b.getSingleTile(i + 3, j + 3).getColor()
                            && currentTileColor == b.getSingleTile(i + 4, j + 4).getColor()) {
                        return 1;
                    }
                } else if (i>3) {

                    if (currentTileColor == b.getSingleTile(i - 1, j + 1).getColor()
                            && currentTileColor == b.getSingleTile(i - 2, j + 2).getColor()
                            && currentTileColor == b.getSingleTile(i - 3, j + 3).getColor()
                            && currentTileColor == b.getSingleTile(i - 4, j + 4).getColor()) {
                        return 1;
                    }
                }

            }
        }
            return 0;
        }
    }
