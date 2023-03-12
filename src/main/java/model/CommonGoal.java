package model;

abstract class CommonGoal extends Card{

    private int patternRepetition;
    private TileType type;
    private GoalTile[] scoreTiles;

    public CommonGoal() {
        super();
    }

    public CommonGoal(String image, int patternRepetition, TileType type) {
        super(image);
        this.patternRepetition = patternRepetition;
        this.type = type;
        scoreTiles = {new GoalTile(8),new GoalTile(6),new GoalTile(4),new GoalTile(2)};
    }

    public GoalTile[] getScoreTiles() {
        return scoreTiles;
    }

    public void setScoreTiles(GoalTile[] scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    public int getPatternRepetition() {
        return patternRepetition;
    }

    public TileType getType() {
        return type;
    }

    public void setPatternRepetition(int patternRepetition) {
        this.patternRepetition = patternRepetition;
    }

    public void setType(TileType type) {
        this.type = type;
    }


}
