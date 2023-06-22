package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CommonGoal extends Card {
    //contains the number of times the personal goal must be completed to take the score tile.
    private int numberOfPatternRepetitionsRequired;
    //contains the type of check must be done.
    private CheckType type;
    //contains the list of the scoring tiles.
    private List<ScoreTile> scoreTiles;

    //Constructors with/without params
    public CommonGoal() {
        super();
        this.type = null;
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
        this.numberOfPatternRepetitionsRequired = 0;
    }

    public CommonGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type) {
        super(id);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
    }

    public CommonGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles) {
        super(id);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.scoreTiles = scoreTiles;
    }

    public CommonGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(id);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.initScoreTiles(numberOfPlayers, id);
    }

    //Set/Get methods of variables
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

    /*
    initialize the scoring tiles
    @param numberOfPlayers contains the number of players in the game
    @param commonGoalID contain the common goal used in the game
     */
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

    /*
    This method will be redefined in each common goal and will serve to print on the terminal the common goal
    @return an immutable copy of the common goal
    */
    public CommonGoalView copyImmutable() {
        return new CommonGoalView(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof CommonGoal))
            return false;
        return (this.getId() == ((CommonGoal) obj).getId());
    }
}
