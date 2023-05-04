package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.commongoal.MinEqualsTilesPattern;
import it.polimi.ingsw.model.view.CommonGoalView;

public class MinEqualsTilesPatternView extends CommonGoalView {
    private final Direction direction;
    private final int maxEqualsTiles;

    public MinEqualsTilesPatternView(MinEqualsTilesPattern commonGoalModel) {
        super(commonGoalModel);
        this.maxEqualsTiles = commonGoalModel.getMaxEqualsTiles();
        this.direction = commonGoalModel.getDirection();
    }

    public Direction getDirection() {
        return this.direction;
    }

    public int getMaxEqualsTiles() {
        return this.maxEqualsTiles;
    }

    @Override
    public String toString() {
        switch (this.direction) {
            case HORIZONTAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 same types of tiles. " +
                                "[ B B B B B ] \n";
                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 different types of tiles. " +
                                "One line can show the same or a different combination of the other line. \n" +
                                "[ B P Y G W ] \n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 tiles of maximum three different types. One \n" +
                                "line can show the same or a different combination of another line. \n" +
                                "[ B B C C Y ] \n";
                    }
                }
            }
            case VERTICAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 same types of tiles. " +
                                "[ B ] \n" +
                                "[ B ] \n" +
                                "[ B ] \n" +
                                "[ B ] \n" +
                                "[ B ] \n" +
                                "[ B ] \n";

                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 different types of tiles. \n" +
                                "One column can show the same or a different combination of another column. \n" +
                                "[ B ] \n" +
                                "[ Y ] \n" +
                                "[ G ] \n" +
                                "[ P ] \n" +
                                "[ W ] \n" +
                                "[ C ] \n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 5 tiles of maximum three different types." +
                                "One column can show the same or a different combination of another column. \n" +
                                "[ B ] \n" +
                                "[ B ] \n" +
                                "[ Y ] \n" +
                                "[ Y ] \n" +
                                "[ G ] \n" +
                                "[ G ] \n";

                    }
                }
            }
        }
        return "Pattern don't found";
    }
}
