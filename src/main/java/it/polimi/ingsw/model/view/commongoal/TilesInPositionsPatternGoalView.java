package it.polimi.ingsw.model.view.commongoal;

import java.io.Serializable;

public class TilesInPositionsPatternGoalView implements Serializable {
    private final int[][] positions;

    public TilesInPositionsPatternGoalView(int[][] positions) {
        this.positions = positions;
    }
    public int[][] getPositions() {
        return positions;
    }
    @Override
    public String toString() {
        return "Two groups each containing 4 tiles of\n" +
                "the same type in a 2x2 square. The tiles\n" +
                "of one square can be different from\n" +
                "those of the other square.\n\n" +
                "[ B ] [ B ]\n" +
                "[ B ] [ B ]\n" +
                "x 2 times";
    }
}
