package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

/**
 * This class represents the View of the {@code FourCornersPatternGoal}.
 * It contains the part of the logic for displaying the pattern and referencing it.
 * This can be done through a method to identify the response to a given situation regarding
 * this goal pattern achievement (toString).
 *
 * @see it.polimi.ingsw.model.commongoal.FourCornersPatternGoal
 */
public class FourCornersPatternGoalView extends CommonGoalView {
    /**
     * Class constructor.
     * Assign the values as in the {@code CommonGoal} parameter, commonGoalModel.
     *
     * @param commonGoalModel the referencing class for the call of the super method in the constructor
     *                         in order to make possible the construction of the class object.
     */
    public FourCornersPatternGoalView(FourCornersPatternGoal commonGoalModel) {
        super(commonGoalModel);
    }

    /**
     * This method is used to display the result of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code FourCornersPatternGoal}).
     *
     * @return a text message associated to the various combinations of tiles associated
     *          that may satisfy the conditions to verify the FourCornersPatternGoal.
     *
     * @see it.polimi.ingsw.model.commongoal.FourCornersPatternGoal
     */
    @Override
    public String toString() {
        return "Four tiles of the same type in the four corners of the bookshelf. \n" +
                "[ " + TileColor.BLUE + " - - - " + TileColor.BLUE + " ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ " + TileColor.BLUE + " - - - " + TileColor.BLUE + " ] \n";
    }
}
