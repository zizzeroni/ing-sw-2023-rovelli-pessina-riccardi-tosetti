package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.tile.ScoreTile;

public class ScoreTileView {
    //private final ScoreTile scoreTileModel;
    private final int value;
    private final int playerID;
    private final int commonGoalID;

    public ScoreTileView(ScoreTile scoreTileModel) {
        this.value = scoreTileModel.getValue();
        this.playerID = scoreTileModel.getPlayerID();
        this.commonGoalID = scoreTileModel.getCommonGoalID();
    }

    public int getPlayerID() {
        return this.playerID;
    }

    public int getCommonGoalID() {
        return this.commonGoalID;
    }

    public int getValue() {
        return this.value;
    }
}
