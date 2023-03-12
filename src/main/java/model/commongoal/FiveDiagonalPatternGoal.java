package model.commongoal;

import model.CommonGoal;

public class FiveDiagonalPatternGoal extends CommonGoal {
    public FiveDiagonalPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 5 tiles
           in diagonale, uguali tra loro.
         */
        return false;
    }
}
