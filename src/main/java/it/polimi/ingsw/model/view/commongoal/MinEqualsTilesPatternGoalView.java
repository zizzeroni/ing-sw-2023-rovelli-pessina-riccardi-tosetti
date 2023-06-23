package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.commongoal.MinEqualsTilesPattern;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

public class MinEqualsTilesPatternGoalView extends CommonGoalView {
    private final Direction direction;
    private final int maxEqualsTiles;

    public MinEqualsTilesPatternGoalView(MinEqualsTilesPattern commonGoalModel) {
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
                                "[ "+ TileColor.BLUE +" "+ TileColor.BLUE +" "+ TileColor.BLUE +" "+ TileColor.BLUE +"" +
                                " "+ TileColor.BLUE +" ] \n";
                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 different types of tiles. " +
                                "One line can show the same or a different combination of the other line. \n" +
                                "[ "+ TileColor.BLUE +" "+ TileColor.PURPLE +" "+ TileColor.GREEN +" "+ TileColor.YELLOW +"" +
                                " "+ TileColor.PURPLE +" ] \n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 tiles of maximum three different types. One \n" +
                                "line can show the same or a different combination of another line. \n" +
                                "[ "+ TileColor.BLUE +" "+ TileColor.BLUE +" "+ TileColor.GREEN +" "+ TileColor.GREEN +"" +
                                " "+ TileColor.YELLOW +" ] \n";
                    }
                }
            }
            case VERTICAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 same types of tiles. " +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.BLUE +" ] \n";

                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 different types of tiles. \n" +
                                "One column can show the same or a different combination of another column. \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.YELLOW +" ] \n" +
                                "[ "+ TileColor.GREEN +" ] \n" +
                                "[ "+ TileColor.CYAN +" ] \n" +
                                "[ "+ TileColor.WHITE +" ] \n" +
                                "[ "+ TileColor.PURPLE +" ] \n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 5 tiles of maximum three different types." +
                                "One column can show the same or a different combination of another column. \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.BLUE +" ] \n" +
                                "[ "+ TileColor.GREEN +" ] \n" +
                                "[ "+ TileColor.YELLOW +" ] \n" +
                                "[ "+ TileColor.YELLOW +" ] \n" +
                                "[ "+ TileColor.YELLOW +" ] \n";

                    }
                }
            }
        }
        return "Pattern don't found";
    }
}
