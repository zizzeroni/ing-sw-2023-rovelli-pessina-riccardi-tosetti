package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.Direction;

import java.io.Serializable;

public class MinEqualsTilesPatternView implements Serializable {
    private final Direction direction;
    private final int maxEqualsTiles;

    public MinEqualsTilesPatternView(int maxEqualsTiles, Direction direction) {
        this.maxEqualsTiles = maxEqualsTiles;
        this.direction=direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getMaxEqualsTiles() {
        return maxEqualsTiles;
    }
    @Override
    public String toString() {
        return "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).\n" +
                "The tiles of one group can be different from those of another group.\n\n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 - 0 0 ] \n" +
                "[ 0 | = | 0 ] \n" +
                "[ 0 | = | 0 ] \n" +
                "[ 0 0 - 0 0 ] \n" +
                "[ 0 0 - 0 0 ] \n";
    }
}
