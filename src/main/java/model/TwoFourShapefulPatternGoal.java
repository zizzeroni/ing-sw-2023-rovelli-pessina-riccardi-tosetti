package model;

public class TwoFourShapefulPatternGoal extends CommonGoal{
    public TwoFourShapefulPatternGoal(String image, int patternRepetition, TileType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 2 quadrati di
           4 tiles uguali tra loro.
         */
        return false;
    }
}
