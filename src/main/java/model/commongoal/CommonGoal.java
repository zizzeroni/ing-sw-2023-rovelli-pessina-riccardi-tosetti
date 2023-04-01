package model.commongoal;

import model.Card;
import model.tile.ScoreTile;

public abstract class CommonGoal extends Card {
    private final int scoreTilesNumber = 3;
    private int patternRepetition;
    private CheckType type;
    private ScoreTile[] scoreTiles;

    public CommonGoal() {
        super();
        this.type=null;
        this.scoreTiles=null;
        this.patternRepetition=0;
    }
    public CommonGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.scoreTiles = new ScoreTile[]{new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)};
    }
    public CommonGoal(int imageID, int patternRepetition, CheckType type, int numberOfPlayers) {
        super(imageID);
        this.patternRepetition = patternRepetition;
        this.type = type;
        this.initScoreTiles(numberOfPlayers);
    }



    public ScoreTile[] getScoreTiles() {
        return this.scoreTiles;
    }

    public void setScoreTiles(ScoreTile[] scoreTiles) {
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

    public int getScoreTilesNumber() {
        return scoreTilesNumber;
    }

    private void initScoreTiles(int numberOfPlayers) {
        switch(numberOfPlayers) {
            case 2 -> {
                this.scoreTiles = new ScoreTile[]{new ScoreTile(8),new ScoreTile(4)};
            }
            case 3 -> {
                this.scoreTiles = new ScoreTile[]{new ScoreTile(8),new ScoreTile(6),new ScoreTile(4)};
            }
            case 4 -> {
                this.scoreTiles = new ScoreTile[]{new ScoreTile(8),new ScoreTile(6),new ScoreTile(4),new ScoreTile(2)};
            }
            default -> {
                this.scoreTiles = null;
            }
        }
    }

}
