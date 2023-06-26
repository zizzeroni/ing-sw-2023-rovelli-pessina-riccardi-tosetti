package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.view.CommonGoalView;

/**
 * This class represents the View of the {@code StairPatternGoal}.
 * It contains the necessary components for displaying the pattern and referencing it.
 * This can be done through a method to identify the response to a given situation regarding
 * this goal pattern achievement (toString).
 *
 * @see it.polimi.ingsw.model.commongoal.StairPatternGoal
 */
public class StairPatternGoalView extends CommonGoalView {
    /**
     * Class constructor.
     * Assign the values as in the {@code CommonGoal} parameter, commonGoalModel.
     *
     * @param commonGoalModel the referencing class for the call of the super method in the constructor
     *                        in order to make possible the construction of the class object.
     */
    public StairPatternGoalView(CommonGoal commonGoalModel) {
        super(commonGoalModel);
    }

    /**
     * This method is used to display the result of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code StairPatternGoal}).
     *
     * @return a text message associated to the various combinations of tiles associated
     * that may satisfy the conditions to verify the StairPatternGoal.
     * @see it.polimi.ingsw.model.commongoal.StairPatternGoal
     */
    @Override
    public String toString() {
        return "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, " +
                "each next column must be made of exactly one more tile. Tiles can be of any type. \n" +
                "[ 0 0 0 0 0 ] \n" +
                "[ 0 0 0 0 - ] \n" +
                "[ 0 0 0 - - ] \n" +
                "[ 0 0 - - - ] \n" +
                "[ 0 - - - - ] \n" +
                "[ - - - - - ] \n";
    }
}
