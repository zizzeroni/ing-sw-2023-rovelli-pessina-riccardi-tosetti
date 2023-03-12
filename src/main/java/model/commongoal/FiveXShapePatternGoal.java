package model.commongoal;

import model.CommonGoal;

public class FiveXShapePatternGoal extends CommonGoal {
    public FiveXShapePatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }
    public boolean goalPattern() {
        /* Implementa controllo del pattern con 5 tiles
           a forma di X, uguali tra loro.
         */
        return false;
    }


}
