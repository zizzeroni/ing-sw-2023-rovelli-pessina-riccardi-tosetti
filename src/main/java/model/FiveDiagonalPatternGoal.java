package model;

public class FiveDiagonalPatternGoal extends CommonGoal{
    public FiveDiagonalPatternGoal(String image, int patternRepetition, TileType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 5 tiles
           in diagonale, uguali tra loro.
         */
        return false;
    }
}
