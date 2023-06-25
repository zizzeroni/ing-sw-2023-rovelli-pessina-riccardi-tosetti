package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

public class EightShapelessPatternGoalView extends CommonGoalView {
    public EightShapelessPatternGoalView(CommonGoal commonGoalModel) {
        super(commonGoalModel);
    }

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
