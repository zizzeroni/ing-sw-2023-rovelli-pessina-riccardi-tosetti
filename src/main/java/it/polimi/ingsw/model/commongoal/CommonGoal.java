package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.CommonGoalView;

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
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
        this.numberOfPatternRepetitionsRequired = 0;
    }

    public CommonGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type) {
        super(imageID);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
    }

    public CommonGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID) {
        super(imageID);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.initScoreTiles(numberOfPlayers, commonGoalID);
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
        return this.type;
    }

    public void setNumberOfPatternRepetitionsRequired(int numberOfPatternRepetitionsRequired) {
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
    }

    public void setType(CheckType type) {
        this.type = type;
    }

    private void initScoreTiles(int numberOfPlayers, int commonGoalID) {
        switch (numberOfPlayers) {
            case 2 -> {
                this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8, 0, commonGoalID), new ScoreTile(4, 0, commonGoalID)));
            }
            case 3 -> {
                this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8, 0, commonGoalID), new ScoreTile(6, 0, commonGoalID), new ScoreTile(4, 0, commonGoalID)));
            }
            case 4 -> {
                this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8, 0, commonGoalID), new ScoreTile(6, 0, commonGoalID), new ScoreTile(4, 0, commonGoalID), new ScoreTile(2, 0, commonGoalID)));
            }
            default -> {
                this.scoreTiles = null;
            }
        }
    }

    public CommonGoalView copyImmutable() {
        return new CommonGoalView(this);
    }

}
