package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the CommonGoal's view.
 * The class contains a series of getters to access their personal goals, their goal tiles, their {@code Bookshelf}s, chats
 * and a series of other class related relevant information.
 *
 * @see CommonGoal
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class CommonGoalView implements Serializable {
    //private final CommonGoal commonGoalModel;
    private final int numberOfPatternRepetitionsRequired;
    private final CheckType type;
    private final List<ScoreTileView> scoreTiles;
    private final int id;    //Attribute from Card class, of which we don't have a "View" class

    /**
     * Class constructor.
     * Used to associate the representation of the {@code CommonGoal} in the {@code CommonGoalView}
     * with the linked logic in the {@code commonGoalModel} (passed as parameter).
     *
     * @param commonGoalModel the model of the considered {@code CommonGoal}.
     * @see CommonGoal
     */
    public CommonGoalView(CommonGoal commonGoalModel) {
        this.numberOfPatternRepetitionsRequired = commonGoalModel.getNumberOfPatternRepetitionsRequired();
        this.type = commonGoalModel.getType();
        this.scoreTiles = new ArrayList<>();
        this.id = commonGoalModel.getId();
        for (ScoreTile scoreTile : commonGoalModel.getScoreTiles()) {
            this.getScoreTiles().add(new ScoreTileView(scoreTile));
        }
    }

    /**
     * Gets the list of score {@code Tile}s.
     *
     * @return the list of score tiles.
     * @see Tile
     */
    public List<ScoreTileView> getScoreTiles() {
        return this.scoreTiles;
    }

    /**
     * Gets the number of times the {@code CommonGoal} must be completed to take the {@code ScoreTile}.
     *
     * @return the number of times the personal goal must be completed to take the score tile.
     * @see CommonGoal
     * @see ScoreTile
     */
    public int getNumberOfPatternRepetitionsRequired() {
        return this.numberOfPatternRepetitionsRequired;
    }

    /**
     * Gets the type of check that has to be done on the considered common goal's card (inherently to the card's depicted pattern).
     *
     * @return the type's associated value.
     */
    public CheckType getType() {
        return this.type;
    }

    /**
     * Getter used to access {@code CommonGoal}'s image identifier.
     *
     * @return the {@code CommonGoal}'s id.
     * @see CommonGoal
     */
    public int getId() {
        return this.id;
    }
}
