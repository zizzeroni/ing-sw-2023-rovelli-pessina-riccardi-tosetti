package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

public class FourCornersPatternGoalView extends CommonGoalView {
    public FourCornersPatternGoalView(FourCornersPatternGoal commonGoalModel) {
        super(commonGoalModel);
    }

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
