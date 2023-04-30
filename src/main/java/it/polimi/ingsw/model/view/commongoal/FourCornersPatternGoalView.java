package it.polimi.ingsw.model.view.commongoal;

import java.io.Serializable;

public class FourCornersPatternGoalView implements Serializable {
    @Override
    public String toString() {
        return "Four tiles of the same type in the four\n" +
                "corners of the bookshelf. \n" +
                "In this example B->BLUE\n\n" +
                "[ B 0 0 0 B ] \n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ B 0 0 0 B ] \n";
    }
}
