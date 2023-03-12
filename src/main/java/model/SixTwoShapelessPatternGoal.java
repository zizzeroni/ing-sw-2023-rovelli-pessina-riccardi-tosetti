package model;

public class SixTwoShapelessPatternGoal extends CommonGoal{
    public SixTwoShapelessPatternGoal(String image, int patternRepetition, TileType type) {
        super(image, patternRepetition, type);
    }

    public boolean goalPattern() {
        /* Implementa controllo del pattern con 6 gruppi di
           almeno 2 tiles ciascuno, senza una specifica forma
           e uguali tra loro.
         */
        return false;
    }
}
