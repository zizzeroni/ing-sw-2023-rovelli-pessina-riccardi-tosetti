package model.commongoal;

import model.Card;
import model.tile.GoalTile;

public abstract class CommonGoal extends Card {

    private int patternRepetition;
    private CheckType type;
    private GoalTile[] scoreTiles;

    public CommonGoal() {
        super();
        this.type=null;
        this.scoreTiles=new GoalTile[]{new GoalTile(8), new GoalTile(6), new GoalTile(4), new GoalTile(2)};
        this.patternRepetition=0;
    }
    public CommonGoal(String image, int patternRepetition, CheckType type) {
        super(image);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.scoreTiles = new GoalTile[]{new GoalTile(8), new GoalTile(6), new GoalTile(4), new GoalTile(2)};
    }
    public CommonGoal(String image, int patternRepetition, CheckType type, GoalTile[] scoreTiles) {
        super(image);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.scoreTiles = scoreTiles;
    }

    public GoalTile[] getScoreTiles() {
        return this.scoreTiles;
    }

    public void setScoreTiles(GoalTile[] scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    public int getPatternRepetition() {
        return this.patternRepetition;
    }

    public CheckType getType() {
        return type;
    }

    public void setPatternRepetition(int patternRepetition) {
        this.patternRepetition = patternRepetition;
    }

    public void setType(CheckType type) {
        this.type = type;
    }


}
