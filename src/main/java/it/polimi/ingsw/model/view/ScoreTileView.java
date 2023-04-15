package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.tile.ScoreTile;

public class ScoreTileView {
    private final ScoreTile scoreTileModel;

    public ScoreTileView(ScoreTile scoreTileModel) {
        this.scoreTileModel = scoreTileModel;
    }

    public int getPlayerID() {
        return this.scoreTileModel.getPlayerID();
    }

    public int getCommonGoalID() {
        return this.scoreTileModel.getCommonGoalID();
    }

    public int getValue() {
        return this.scoreTileModel.getValue();
    }
}
