package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.io.Serializable;

public class FourCornersPatternGoalView extends CommonGoalView {
    public FourCornersPatternGoalView(FourCornersPatternGoal commonGoalModel) {
        super(commonGoalModel);
    }
    @Override
    public String toString() {
        return "Four tiles of the same type in the four corners of the bookshelf. \n" +
                "[ B - - - B ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ B - - - B ] \n";
    }
}
