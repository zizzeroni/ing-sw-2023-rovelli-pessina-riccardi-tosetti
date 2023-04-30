package it.polimi.ingsw.model.view.commongoal;

import java.io.Serializable;

public class StairPatternGoalView implements Serializable {
    @Override
    public String toString() {
        return "Five columns of increasing or decreasing\n" +
                "height. Starting from the first column on\n" +
                "the left or on the right, each next column\n" +
                "must be made of exactly one more tile.\n" +
                "Tiles can be of any type. \n\n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 0 0 - ] \n" +
                "[ 0 0 0 - - ] \n" +
                "[ 0 0 - - - ] \n" +
                "[ 0 - - - - ] \n" +
                "[ - - - - - ] \n";
    }
}
