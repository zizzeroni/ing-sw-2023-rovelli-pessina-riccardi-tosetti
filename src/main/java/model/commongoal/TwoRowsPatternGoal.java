package model.commongoal;

import model.CommonGoal;

public class TwoRowsPatternGoal extends CommonGoal {
    public TwoRowsPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 2 righe di
           tiles tutte diverse tra loro.
         */
        return false;
    }
}
