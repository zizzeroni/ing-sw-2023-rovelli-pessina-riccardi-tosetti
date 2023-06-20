package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.ConsecutiveTilesPatternGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

/**
 * This class represents the View of the {@code ConsecutiveTilesPatternGoal}.
 * It contains the part of the logic for displaying the pattern and referencing it.
 * This can be done through a method to identify the response to a given situation regarding
 * this goal pattern achievement (toString).
 * It also provides a getter to access the consecutiveTiles attribute's value, peculiar to this type of pattern.
 *
 * @see it.polimi.ingsw.model.commongoal.ConsecutiveTilesPatternGoal
 */
public class ConsecutiveTilesPatternGoalView extends CommonGoalView {
    private final int consecutiveTiles;

    /**
     * Getter method to access the consecutiveTiles attribute's value in the view's pattern.
     *
     * @return the value associated to consecutiveTiles.
     */
    public int getConsecutiveTiles() {
        return this.consecutiveTiles;
    }

    /**
     * Class constructor.
     * Assign the values as in the {@code CommonGoal} parameter, commonGoalModel.
     *
     * @param commonGoalModel the referencing class for the call of the super method in the constructor
     *                         in order to make possible the construction of the class object.
     */
    public ConsecutiveTilesPatternGoalView(ConsecutiveTilesPatternGoal commonGoalModel) {
        super(commonGoalModel);
        this.consecutiveTiles = commonGoalModel.getConsecutiveTiles();
    }

    /**
     * This method is used to display the of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code ConsecutiveTilesPatternGoal}).
     *
     * @return a text message associated to the various combinations of tiles associated
     *          that may satisfy the conditions to verify the ConsecutiveTilesPatternGoal.
     *
     * @see it.polimi.ingsw.model.commongoal.ConsecutiveTilesPatternGoal
     */
    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder(getNumberOfPatternRepetitionsRequired() + " groups each containing at least " +
                getConsecutiveTiles() + " tiles of the same type (not necessarily in the depicted shape). \n" +
                "The tiles of one group can be different from those of another group. \n");

        for (int i = 0; i < getConsecutiveTiles(); i++) {
            sendBack.append("[ "+ TileColor.BLUE+ " ]\n");

        }
        return sendBack.toString();
    }
}
