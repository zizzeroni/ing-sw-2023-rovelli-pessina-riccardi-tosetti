package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.commongoal.MinEqualsTilesPattern;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.io.Serializable;

public class MinEqualsTilesPatternView extends CommonGoalView {
    private final Direction direction;
    private final int maxEqualsTiles;

    public MinEqualsTilesPatternView(MinEqualsTilesPattern commonGoalModel) {
        super(commonGoalModel);
        this.maxEqualsTiles = commonGoalModel.getMaxEqualsTiles();
        this.direction= commonGoalModel.getDirection();
    }
    public Direction getDirection() {
        return direction;
    }

    public int getMaxEqualsTiles() {
        return maxEqualsTiles;
    }
    @Override
    public String toString() {
        switch (direction) {
            case HORIZONTAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 tiles of maximum three different types. One \n" +
                                "line can show the same or a different combination of another line. \n\n"+
                                "[ B B B B B ] \n\n";
                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 different types of tiles. " +
                                "One line can show the same or a different combination of the other line. \n\n"+
                                "[ B P Y G W ] \n\n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 casual types of tiles. " +
                                "One line can show the same or a different combination of the other line. \n\n"+
                                "[ - - - - - ] \n\n";
                    }
                }
            }
            case VERTICAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 5 tiles of maximum three different types." +
                                "One column can show the same or a different combination of another column. \n\n"+
                                "[ B ] \n" +
                                "[ B ] \n" +
                                "[ Y ] \n" +
                                "[ Y ] \n" +
                                "[ G ] \n" +
                                "[ G ] \n\n" ;
                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 different types of tiles."+
                                "[ B ] \n" +
                                "[ Y ] \n" +
                                "[ G ] \n" +
                                "[ P ] \n" +
                                "[ W ] \n" +
                                "[ C ] \n\n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 casual types of tiles. " +
                                "One column can show the same or a different combination of the other column. \n\n"+
                                "[ - ] \n" +
                                "[ - ] \n" +
                                "[ - ] \n" +
                                "[ - ] \n" +
                                "[ - ] \n" +
                                "[ - ] \n\n" ;
                    }
                }
            }
        }
        return "Pattern don't found";
    }
}
