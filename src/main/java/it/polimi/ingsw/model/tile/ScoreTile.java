package it.polimi.ingsw.model.tile;

public class ScoreTile {
    private int value;
    private int playerID; // reference to the player that owns the scoreTile
    private int commonGoalID; // reference to the common goal the scoreTile refers to

    public ScoreTile(int value, int playerID, int commonGoalID) {
        this.value = value;
        this.playerID = playerID;
        this.commonGoalID = commonGoalID;
    }

    public ScoreTile() {
        this.value = -1;
        this.playerID = -1;
        this.commonGoalID = -1;
    }

    public ScoreTile(int value) {
        this.value = value;
        this.playerID = -1;
        this.commonGoalID = -1;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getCommonGoalID() {
        return commonGoalID;
    }

    public void setCommonGoalID(int commonGoalID) {
        this.commonGoalID = commonGoalID;
    }

    public int getValue() {
        return this.value;
    }
}
