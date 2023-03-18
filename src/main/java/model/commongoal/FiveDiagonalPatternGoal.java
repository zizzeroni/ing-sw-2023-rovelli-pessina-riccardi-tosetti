package model.commongoal;

import model.Bookshelf;

public class FiveDiagonalPatternGoal extends CommonGoal {
    public FiveDiagonalPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {
        /* Implementa controllo del pattern con 5 tiles
           in diagonale, uguali tra loro.
         */
        return 1;
    }
}
