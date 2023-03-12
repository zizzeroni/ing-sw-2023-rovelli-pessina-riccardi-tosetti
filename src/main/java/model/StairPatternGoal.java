package model;

public class StairPatternGoal extends CommonGoal{
    public StairPatternGoal(String image, int patternRepetition, TileType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern a forma di scala.
         */
        return false;
    }
}
