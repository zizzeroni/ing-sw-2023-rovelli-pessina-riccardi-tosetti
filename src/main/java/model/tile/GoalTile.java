package model.tile;

import model.Player;
import model.commongoal.CommonGoal;

import java.util.ArrayList;
import java.util.List;

public class GoalTile {
    private int value;
    private int playerID; // eredità di player
    private int commonGoalID; // eredità di commonGoal

    public GoalTile(int value, int playerID, int commonGoalID) {
        this.value = value;
        this.playerID = playerID;
        this.commonGoalID = commonGoalID;
    }

    public GoalTile() {
        this.value = 0;
        this.playerID = 0;
        this.commonGoalID = 0;
    }

    public GoalTile(int value) {
        this.value = value;
        this.playerID = 0;
        this.commonGoalID = 0;
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

    public int getValue(){
        return this.value;
    }
}
