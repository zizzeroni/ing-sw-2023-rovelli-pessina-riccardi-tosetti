package model;

public class FourFourShapelessPatternGoal extends CommonGoal{
    public FourFourShapelessPatternGoal(String image, int patternRepetition, TileType type) {
        super(image, patternRepetition, type);
    }

    //@Override
    public boolean goalPattern() {
        /* Implementa controllo del pattern con 4 gruppi di
           almeno 4 tiles ciascuno, senza una specifica forma
           e uguali tra loro.
         */
        return false;
    }
}
