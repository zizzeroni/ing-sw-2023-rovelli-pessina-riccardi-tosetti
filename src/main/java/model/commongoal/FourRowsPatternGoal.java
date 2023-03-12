package model.commongoal;

import model.CommonGoal;

public class FourRowsPatternGoal extends CommonGoal {
    public FourRowsPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 4 righe
           contenenti un massimo di 3 tiles diverse.
         */
        return false;
    }
}
