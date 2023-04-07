package org.example.model.view;

import org.example.model.tile.ScoreTile;

public class GoalTileView {
    private final ScoreTile scoreTileModel;

    public GoalTileView(ScoreTile scoreTileModel) {
        this.scoreTileModel = scoreTileModel;
    }

    public int getPlayerID() {
        return this.scoreTileModel.getPlayerID();
    }

    public int getCommonGoalID() {
        return this.scoreTileModel.getCommonGoalID();
    }

    public int getValue(){
        return this.scoreTileModel.getValue();
    }
}
