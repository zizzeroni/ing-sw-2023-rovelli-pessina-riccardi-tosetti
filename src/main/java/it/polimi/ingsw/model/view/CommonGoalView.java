package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.commongoal.CommonGoal;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalView {
    private final CommonGoal commonGoalModel;

    public CommonGoalView(CommonGoal commonGoalModel) {
        this.commonGoalModel = commonGoalModel;
    }

    public List<GoalTileView> getScoreTiles() {
        List<GoalTileView> scoreTiles = new ArrayList<>(this.commonGoalModel.getScoreTiles().size());
        for (int i = 0; i < this.commonGoalModel.getScoreTiles().size(); i++) {
            scoreTiles.add(new GoalTileView(this.commonGoalModel.getScoreTiles().get(i)));
        }
        return scoreTiles;
    }

    public int getNumberOfPatternRepetitionsRequired() {
        return this.commonGoalModel.getNumberOfPatternRepetitionsRequired();
    }

    public CheckType getType() {
        return this.commonGoalModel.getType();
    }

    public int getImageID() {
        return this.commonGoalModel.getImageID();
    }
}
