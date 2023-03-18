package model.commongoal;

import model.Bookshelf;

public class StairPatternGoal extends CommonGoal {
    public StairPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {
        /* Implementa controllo del pattern con 5 tiles
           a forma di X, uguali tra loro.
         */
        return 1;
    }
}
