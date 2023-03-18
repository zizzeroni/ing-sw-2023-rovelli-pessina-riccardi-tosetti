package model.tile;

import model.Player;
import model.commongoal.CommonGoal;

import java.util.ArrayList;
import java.util.List;

public class GoalTile {
    private int value;
    private ArrayList<Player> players; // eredità di player
    private ArrayList<CommonGoal> commonGoals; // eredità di commonGoal


    public GoalTile(int value) {
        this.value = value;
    }

    public int getValue(){return this.value;}
}
