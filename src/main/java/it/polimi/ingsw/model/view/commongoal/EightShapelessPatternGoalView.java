package it.polimi.ingsw.model.view.commongoal;

import java.io.Serializable;

public class EightShapelessPatternGoalView implements Serializable {
    @Override
    public String toString() {
        return "At least Eight tiles of the same type. There’s no\n" +
                "restriction about the position of these tiles.\n\n" +
                "[ 0 0 0 0 B ] \n" +
                "[ 0 B 0 0 0 ] \n" +
                "[ 0 B 0 0 0 ] \n" +
                "[ 0 0 B 0 0 ] \n" +
                "[ 0 0 0 B 0 ] \n" +
                "[ B 0 B 0 B ] \n";
    }
}
