package model.tile;

import model.Player;
import model.commongoal.CommonGoal;

import java.util.ArrayList;

public class ScoreTile {
    private int value;
    private ArrayList<Player> players; // eredità di player
    private ArrayList<CommonGoal> commonGoals; // eredità di commonGoal


    public ScoreTile(int value) {
        this.value = value;
    }

    public int getValue(){return this.value;}
}
