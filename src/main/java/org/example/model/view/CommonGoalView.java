package org.example.model.view;

import org.example.model.commongoal.CheckType;
import org.example.model.commongoal.CommonGoal;

public class CommonGoalView {
    private final CommonGoal commonGoalModel;

    public CommonGoalView(CommonGoal commonGoalModel) {
        this.commonGoalModel = commonGoalModel;
    }

    public GoalTileView[] getScoreTiles() {
        GoalTileView[] temp = new GoalTileView[this.commonGoalModel.getScoreTilesNumber()];
        for(int i=0;i<this.commonGoalModel.getScoreTilesNumber();i++) {
            temp[i]=new GoalTileView(this.commonGoalModel.getScoreTiles()[i]);
        }
        return temp;
    }
    public int getPatternRepetition() {
        return this.commonGoalModel.getPatternRepetition();
    }
    public CheckType getType() {
        return this.commonGoalModel.getType();
    }

    public int getImageID() {
        return this.commonGoalModel.getImageID();
    }
    public int getScoreTilesNumber() {
        return this.commonGoalModel.getScoreTilesNumber();
    }
}
