package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.view.CommonGoalView;

/**
 *
 */
public class StairPatternGoalView extends CommonGoalView {
    public StairPatternGoalView(CommonGoal commonGoalModel) {
        super(commonGoalModel);
    }

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
