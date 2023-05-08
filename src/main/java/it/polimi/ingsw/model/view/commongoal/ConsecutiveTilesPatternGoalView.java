package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.ConsecutiveTilesPatternGoal;
import it.polimi.ingsw.model.view.CommonGoalView;

public class ConsecutiveTilesPatternGoalView extends CommonGoalView {
    private final int consecutiveTiles;

    public int getConsecutiveTiles() {
        return this.consecutiveTiles;
    }

    public ConsecutiveTilesPatternGoalView(ConsecutiveTilesPatternGoal commonGoalModel) {
        super(commonGoalModel);
        this.consecutiveTiles = commonGoalModel.getConsecutiveTiles();
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder(getNumberOfPatternRepetitionsRequired() + " groups each containing at least " +
                getConsecutiveTiles() + " tiles of the same type (not necessarily in the depicted shape). \n" +
                "The tiles of one group can be different from those of another group. \n");

        for (int i = 0; i < getConsecutiveTiles(); i++) {
            sendBack.append("[ B ]\n");
        }
        return sendBack.toString();
    }
}
