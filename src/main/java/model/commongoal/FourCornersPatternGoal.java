package model.commongoal;

import model.CommonGoal;

public class FourCornersPatternGoal extends CommonGoal {
    public FourCornersPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 4 tiles agli
           angoli, uguali tra loro
         */
        return false;
    }
}
