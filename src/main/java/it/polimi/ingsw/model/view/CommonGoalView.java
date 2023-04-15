package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalView {
    //private final CommonGoal commonGoalModel;
    private final int numberOfPatternRepetitionsRequired;
    private final CheckType type;
    private final List<ScoreTileView> scoreTiles;
    private int imageID;    //Attribute from Card class, of which we don't have a "View" class

    public CommonGoalView(CommonGoal commonGoalModel) {
        this.scoreTiles = new ArrayList<>();

        this.numberOfPatternRepetitionsRequired = commonGoalModel.getNumberOfPatternRepetitionsRequired();
        this.type = commonGoalModel.getType();
        for (ScoreTile scoreTile : commonGoalModel.getScoreTiles()) {
            this.scoreTiles.add(new ScoreTileView(scoreTile));
        }
        this.imageID = commonGoalModel.getImageID();
    }

    public List<ScoreTileView> getScoreTiles() {
        return this.scoreTiles;
    }

    public int getNumberOfPatternRepetitionsRequired() {
        return this.numberOfPatternRepetitionsRequired;
    }

    public CheckType getType() {
        return this.type;
    }

    public int getImageID() {
        return this.imageID;
    }
}
