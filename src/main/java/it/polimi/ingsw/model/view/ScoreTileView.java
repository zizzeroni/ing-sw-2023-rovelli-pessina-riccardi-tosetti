package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.io.Serializable;

/**
 *
 * This class implements the {@code ScoreTileView} through the {@code Serializable} interface.
 * All the {@code Player}s always access only the implementation of the {@code ScoreTile}s different views,
 * and are sensible to any inherent modification.
 * Also, the class contains a series of methods to access the goals through their ID and their values
 * and a series of other related, relevant, informations.
 *
 * @see ScoreTile
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class ScoreTileView implements Serializable {
    private final int value;
    private final int playerID;
    private final int commonGoalID;

    /**
     * Class constructor.
     * Used to associate the representation of the {@code ScoreTile} in the {@code ScoreTileView}
     * with the linked logic in the {@code scoreTileModel} (passed as parameter).
     *
     * @param scoreTileModel the model of the considered {@code ScoreTile}.
     *
     * @see ScoreTile
     *
     */
    public ScoreTileView(ScoreTile scoreTileModel) {
        this.value = scoreTileModel.getValue();
        this.playerID = scoreTileModel.getPlayerID();
        this.commonGoalID = scoreTileModel.getCommonGoalID();
    }

    public int getPlayerID() {
        return this.playerID;
    }

    /**
     * Getter to retrieve the {@code CommonGoal}'s identifier.
     *
     * @return reference to the common goal the {@code ScoreTile} refers to.
     *
     * @see CommonGoal
     * @see ScoreTile
     */
    public int getCommonGoalID() {
        return this.commonGoalID;
    }

    /**
     * Gets the {@code ScoreTileView}'s value.
     *
     * @return the value assigned to the ScoreTileView.
     */
    public int getValue() {
        return this.value;
    }
}
