package model.commongoal;


import model.Bookshelf;
import model.tile.GoalTile;

public class FourCornersPatternGoal extends CommonGoal {
    public FourCornersPatternGoal() {
    }

    public FourCornersPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public FourCornersPatternGoal(String image, int patternRepetition, CheckType type, GoalTile[] scoreTiles) {
        super(image, patternRepetition, type, scoreTiles);
    }

    public int goalPattern(Bookshelf b) {
        return (b.getSingleTile(0,0)!=null && b.getSingleTile(0,b.getNumColumns()-1)!=null
                    && b.getSingleTile(b.getNumRows()-1,0)!=null && b.getSingleTile(b.getNumRows()-1,b.getNumColumns()-1)!=null)

                        && (b.getSingleTile(0,0).getColor().equals(b.getSingleTile(0,b.getNumColumns()-1).getColor())
                            && b.getSingleTile(0,b.getNumColumns()-1).getColor().equals(b.getSingleTile(b.getNumRows()-1,0).getColor())
                                && b.getSingleTile(b.getNumRows()-1,0).getColor().equals(b.getSingleTile(b.getNumRows()-1,b.getNumColumns()-1).getColor())) ? 1 : 0;
    }
}
