package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.ScoreTile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommonGoalView implements Serializable {
    //private final CommonGoal commonGoalModel;
    private final int numberOfPatternRepetitionsRequired;
    private final CheckType type;
    private final List<ScoreTileView> scoreTiles;
    private final int imageID;    //Attribute from Card class, of which we don't have a "View" class

    public CommonGoalView(CommonGoal commonGoalModel) {
        this.numberOfPatternRepetitionsRequired = commonGoalModel.getNumberOfPatternRepetitionsRequired();
        this.type = commonGoalModel.getType();
        this.scoreTiles = new ArrayList<>();
        this.imageID = commonGoalModel.getImageID();
        for (ScoreTile scoreTile : commonGoalModel.getScoreTiles()) {
            scoreTiles.add(new ScoreTileView(scoreTile));
        }
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
