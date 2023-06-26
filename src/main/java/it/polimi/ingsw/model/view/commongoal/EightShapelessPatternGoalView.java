package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

/**
 * This class represents the View of the {@code EightShapelessPatternGoal}.
 * It contains the necessary components for displaying the pattern and referencing it.
 * This can be done through a method to identify the response to a given situation regarding
 * this goal pattern achievement (toString).
 *
 * @see it.polimi.ingsw.model.commongoal.EightShapelessPatternGoal
 */
public class EightShapelessPatternGoalView extends CommonGoalView {
    /**
     * Class constructor.
     * Assign the values as in the {@code CommonGoal} parameter, commonGoalModel.
     *
     * @param commonGoalModel the referencing class for the call of the super method in the constructor
     *                         in order to make possible the construction of the class object.
     */
    public EightShapelessPatternGoalView(CommonGoal commonGoalModel) {
        super(commonGoalModel);
    }

    /**
     * This method is used to display the result of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code EightShapelessPatternGoal}).
     *
     * @return a text message associated to the various combinations of tiles associated
     *          that may satisfy the conditions to verify the EightShapelessPatternGoal
     *
     * @see it.polimi.ingsw.model.commongoal.EightShapelessPatternGoal
     */
    @Override
    public String toString() {
        return "At least Eight tiles of the same type. There’s no restriction about the position of these tiles.\n" +
                "[ - - - - " + TileColor.BLUE + " ] \n" +
                "[ - " + TileColor.BLUE + " - - - ] \n" +
                "[ - " + TileColor.BLUE + " - - - ] \n" +
                "[ - - " + TileColor.BLUE + " - - ] \n" +
                "[ - - - " + TileColor.BLUE + " - ] \n" +
                "[ " + TileColor.BLUE + " - " + TileColor.BLUE + " - " + TileColor.BLUE + " ] \n";
    }
}
