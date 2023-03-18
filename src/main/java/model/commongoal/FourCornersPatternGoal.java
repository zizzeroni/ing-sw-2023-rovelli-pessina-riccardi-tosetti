package model.commongoal;


import model.Bookshelf;

public class FourCornersPatternGoal extends CommonGoal {
    public FourCornersPatternGoal() {
    }

    public FourCornersPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {
        if(b.getSingleTile(0,0).equals(b.getSingleTile(0,b.getNumColumns()-1))
                && b.getSingleTile(0,b.getNumColumns()-1).equals(b.getSingleTile(b.getNumRows()-1,0))
                    && b.getSingleTile(b.getNumRows()-1,0).equals(b.getSingleTile(b.getNumRows()-1,b.getNumColumns()-1))) {
            return 1;
        } else {
            return 0;
        }
    }
}
