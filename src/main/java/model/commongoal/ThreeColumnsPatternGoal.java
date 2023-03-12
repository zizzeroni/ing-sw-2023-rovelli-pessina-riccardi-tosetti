package model.commongoal;

import model.CommonGoal;

public class ThreeColumnsPatternGoal extends CommonGoal {
    public ThreeColumnsPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 3 colonne,
           con un massimo di 3 tiles uguali tra loro.
         */
        return false;
    }
}
