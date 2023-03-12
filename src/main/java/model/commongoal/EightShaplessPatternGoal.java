package model.commongoal;

import model.CommonGoal;

public class EightShaplessPatternGoal extends CommonGoal {
    public EightShaplessPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 8 tiles,
           uguali tra loro e in qualsiasi posizione.
         */
        return false;
    }
}
