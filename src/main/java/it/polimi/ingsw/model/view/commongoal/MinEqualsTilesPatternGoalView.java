package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.Direction;
import it.polimi.ingsw.model.commongoal.MinEqualsTilesPattern;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

/**
 * This class represents the View of the MinEqualsTilesPattern.
 * Contains the part of the logic for displaying the MinEqualsTilesPattern and referencing it.
 * This can be done through a series of methods to access direction, maxEqualsTiles value,
 * and identifies the response to a given situation regarding the MinEqualsTilesPattern's goal achievement (toString).
 *
 * @see MinEqualsTilesPattern
 */
public class MinEqualsTilesPatternGoalView extends CommonGoalView {
    private final Direction direction;
    private final int maxEqualsTiles;

    /**
     * Class constructor.
     *
     * @param commonGoalModel is the model linked to the commonGoal associated to the MinEqualsTilesPattern.
     */
    public MinEqualsTilesPatternGoalView(MinEqualsTilesPattern commonGoalModel) {
        super(commonGoalModel);
        this.maxEqualsTiles = commonGoalModel.getMaxEqualsTiles();
        this.direction = commonGoalModel.getDirection();
    }

    /**
     * Gets the directions that is used to retrieve the pattern.
     *
     * @return contains the directions that is used for the pattern representation.
     *
     */
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * Used to access maxEqualsTiles value.
     *
     * @return contains the maximum number of tiles that can be the same in a column/row.
     *
     * @see MinEqualsTilesPattern
     */
    public int getMaxEqualsTiles() {
        return this.maxEqualsTiles;
    }

    /**
     * This method is used to display the possible, different, results of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code MinEqualsTilesPattern} goal).
     *
     * @return a text message associated to the various combinations of tiles associated
     *          that may satisfy the conditions to verify the MinEqualsTilesPattern
     *          (or a text message to signal that the pattern has not been found).
     *
     * @see MinEqualsTilesPattern
     *
     */
    @Override
    public String toString() {
        switch (this.direction) {
            case HORIZONTAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 same types of tiles. " +
                                "[ " + TileColor.BLUE + " " + TileColor.BLUE + " " + TileColor.BLUE + " " + TileColor.BLUE + "" +
                                " " + TileColor.BLUE + " ] \n";
                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 different types of tiles. " +
                                "One line can show the same or a different combination of the other line. \n" +
                                "[ " + TileColor.BLUE + " " + TileColor.PURPLE + " " + TileColor.GREEN + " " + TileColor.YELLOW + "" +
                                " " + TileColor.PURPLE + " ] \n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " lines each formed by 5 tiles of maximum three different types. One \n" +
                                "line can show the same or a different combination of another line. \n" +
                                "[ " + TileColor.BLUE + " " + TileColor.BLUE + " " + TileColor.GREEN + " " + TileColor.GREEN + "" +
                                " " + TileColor.YELLOW + " ] \n";
                    }
                }
            }
            case VERTICAL -> {
                switch (getType()) {
                    case EQUALS -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 same types of tiles. " +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.BLUE + " ] \n";

                    }
                    case DIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 6 different types of tiles. \n" +
                                "One column can show the same or a different combination of another column. \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.YELLOW + " ] \n" +
                                "[ " + TileColor.GREEN + " ] \n" +
                                "[ " + TileColor.CYAN + " ] \n" +
                                "[ " + TileColor.WHITE + " ] \n" +
                                "[ " + TileColor.PURPLE + " ] \n";
                    }
                    case INDIFFERENT -> {
                        return getNumberOfPatternRepetitionsRequired() + " columns each formed by 5 tiles of maximum three different types." +
                                "One column can show the same or a different combination of another column. \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.BLUE + " ] \n" +
                                "[ " + TileColor.GREEN + " ] \n" +
                                "[ " + TileColor.YELLOW + " ] \n" +
                                "[ " + TileColor.YELLOW + " ] \n" +
                                "[ " + TileColor.YELLOW + " ] \n";

                    }
                }
            }
        }
        return "Pattern don't found";
    }
}
