package it.polimi.ingsw.model.view.commongoal;

import java.io.Serializable;

public class DiagonalEqualPatternView implements Serializable {
    private final int[][] pattern;

    public DiagonalEqualPatternView(int[][] pattern) {
        this.pattern = pattern;
    }

    public int[][] getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        return "Five tiles of the same type forming a diagonal.\n\n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 0 0 - ] \n" +
                "[ 0 0 0 - 0 ] \n" +
                "[ 0 0 - 0 0 ] \n" +
                "[ 0 - 0 0 0 ] \n" +
                "[ - 0 0 0 0 ] \n";

//        return "Five tiles of the same type forming an X\n\n" +
//                "[ 0 0 0 0 0 ] \n" +
//                "[ 0 0 0 0 0 ] \n" +
//                "[ 0 0 0 0 0 ] \n" +
//                "[ - 0 - 0 0 ] \n" +
//                "[ 0 - 0 0 0 ] \n" +
//                "[ - 0 - 0 0 ] \n";
    }
}
