package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommonGoal extends Card {
    private int numberOfPatternRepetitionsRequired;
    private CheckType type;
    private List<ScoreTile> scoreTiles;

    public CommonGoal() {
        super();
        this.type = null;
        this.scoreTiles = null;
        this.numberOfPatternRepetitionsRequired = 0;
    }

    public CommonGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type) {
        super(imageID);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
    }

    public CommonGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(imageID);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.initScoreTiles(numberOfPlayers);
    }

    public List<ScoreTile> getScoreTiles() {
        return this.scoreTiles;
    }

    public void setScoreTiles(List<ScoreTile> scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    public int getNumberOfPatternRepetitionsRequired() {
        return this.numberOfPatternRepetitionsRequired;
    }

    public CheckType getType() {
        return type;
    }

    public void setNumberOfPatternRepetitionsRequired(int numberOfPatternRepetitionsRequired) {
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
    }

    public void setType(CheckType type) {
        this.type = type;
    }

    private void initScoreTiles(int numberOfPlayers) {
        switch (numberOfPlayers) {
            case 2 -> {
                this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(4)));
            }
            case 3 -> {
                this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4)));
            }
            case 4 -> {
                this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
            }
            default -> {
                this.scoreTiles = null;
            }
        }
    }

}
