package model.commongoal;

import model.CommonGoal;

public class StairPatternGoal extends CommonGoal {
    public StairPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern a forma di scala.
         */
        return false;
    }
}
