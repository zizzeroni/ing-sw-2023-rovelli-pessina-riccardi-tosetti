package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.io.Serializable;

public class EightShapelessPatternGoalView extends CommonGoalView {
    public EightShapelessPatternGoalView(CommonGoal commonGoalModel) {
        super(commonGoalModel);
    }
    @Override
    public String toString() {
        return "At least Eight tiles of the same type. There’s no restriction about the position of these tiles.\n" +
                "[ - - - - B ] \n" +
                "[ - B - - - ] \n" +
                "[ - B - - - ] \n" +
                "[ - - B - - ] \n" +
                "[ - - - B - ] \n" +
                "[ B - B - B ] \n";
    }
}
