package it.polimi.ingsw.model.view.commongoal;

public class ConsecutiveTilesPatternGoalView {
    private final int consecutiveTiles;

    public int getConsecutiveTiles() {
        return consecutiveTiles;
    }

    public ConsecutiveTilesPatternGoalView(int consecutiveTiles) {
        this.consecutiveTiles = consecutiveTiles;
    }

    @Override
    public String toString() {
        return "Four groups each containing at least\n" +
                "4 tiles of the same type (not necessarily\n" +
                "in the depicted shape).\n" +
                "The tiles of one group can be different\n" +
                "from those of another group.\n\n" +
                "[ B ] \n" +
                "[ B ] \n" +
                "[ B ] \n" +
                "[ B ] \n" +
                "x 4 times";

//        return "Six groups each containing at least\n" +
//                "2 tiles of the same type (not necessarily\n" +
//                "in the depicted shape).\n" +
//                "The tiles of one group can be different\n" +
//                "from those of another group.\n\n" +
//                "[ B ] \n" +
//                "[ B ] \n" +
//                "x 6 times";
    }
}
