package model.view;

import model.tile.GoalTile;

public class GoalTileView {
    private final GoalTile goalTileModel;

    public GoalTileView(GoalTile goalTileModel) {
        this.goalTileModel = goalTileModel;
    }

    public int getPlayerID() {
        return this.goalTileModel.getPlayerID();
    }

    public int getCommonGoalID() {
        return this.goalTileModel.getCommonGoalID();
    }

    public int getValue(){
        return this.goalTileModel.getValue();
    }
}
