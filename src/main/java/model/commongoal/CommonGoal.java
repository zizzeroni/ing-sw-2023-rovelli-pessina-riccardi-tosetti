package model.commongoal;

import model.Card;
import model.tile.GoalTile;

public abstract class CommonGoal extends Card {
    private int patternRepetition;
    private CheckType type;
    private GoalTile[] scoreTiles;
    private int[][] positions;
    private int consecutive;

    public CommonGoal() {
        super();
        this.type=null;
        this.scoreTiles=new GoalTile[]{new GoalTile(8), new GoalTile(6), new GoalTile(4), new GoalTile(2)};
        this.patternRepetition=0;
    }
    public CommonGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.scoreTiles = new GoalTile[]{new GoalTile(8), new GoalTile(6), new GoalTile(4), new GoalTile(2)};
    }
    public CommonGoal(int imageID, int patternRepetition, CheckType type, GoalTile[] scoreTiles) {
        super(imageID);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.scoreTiles = scoreTiles;
    }

    public CommonGoal(int imageID, int patternRepetition, CheckType type, int[][] positions) {
        super(imageID);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.positions = positions;
    }
    public CommonGoal(int imageID, int patternRepetition, CheckType type, int consecutive) {
        super(imageID);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.consecutive = consecutive;
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
