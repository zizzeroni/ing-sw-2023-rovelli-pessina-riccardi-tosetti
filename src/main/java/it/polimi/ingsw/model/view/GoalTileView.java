package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.tile.ScoreTile;

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
