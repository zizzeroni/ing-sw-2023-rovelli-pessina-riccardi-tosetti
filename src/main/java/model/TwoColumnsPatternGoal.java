package model;

public class TwoColumnsPatternGoal extends CommonGoal{
    public TwoColumnsPatternGoal(String image, int patternRepetition, TileType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 5 tiles
           a forma di X, uguali tra loro.
         */
        return false;
    }
}
