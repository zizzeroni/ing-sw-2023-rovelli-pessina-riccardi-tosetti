package it.polimi.ingsw.model.tile;

/**
 * This class represents a {@code ScoreTile}.
 * Provides a series of methods to access and modify the ScoreTile's value
 * and the linked {@code Player}'s and {@code CommonGoal}'s identifiers.
 *
 * @see it.polimi.ingsw.model.Player
 * @see it.polimi.ingsw.model.commongoal.CommonGoal
 */
public class ScoreTile {
    private int value;
    private int playerID; // reference to the player that owns the scoreTile
    private int commonGoalID; // reference to the common goal the scoreTile refers to

    /**
     * Builder of the {@code ScoreTile}'s class.
     * Constructs a ScoreTile considering its assigned value
     * and also current player's and common goal's id.
     *
     * @param value the value associated to the given ScoreTile.
     * @param playerID
     * @param commonGoalID
     */
    public ScoreTile(int value, int playerID, int commonGoalID) {
        this.value = value;
        this.playerID = playerID;
        this.commonGoalID = commonGoalID;
    }

    /**
     * Builder of the {@code ScoreTile}'s class.
     * Constructs a ScoreTile.
     */
    public ScoreTile() {
        this.value = 0;
        this.playerID = -1;
        this.commonGoalID = -1;
    }

    /**
     * Builder of the {@code ScoreTile}'s class.
     * Constructs a ScoreTile considering its assigned value.
     *
     * @param value the value associated to the given ScoreTile.
     */
    public ScoreTile(int value) {
        this.value = value;
        this.playerID = -1;
        this.commonGoalID = -1;
    }

    /**
     * Sets the {@code ScoreTile}'s value.
     *
     * @param value is the value assigned to the ScoreTile.
     *
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Getter to retrieve the {@code Player}'s identifier.
     *
     * @return the {@code Player}'s identifier.
     */
    public int getPlayerID() {
        return this.playerID;
    }

    /**
     * Sets the {@code Player}'s identifier.
     *
     * @param playerID is the reference to the player that owns the scoreTile.
     */
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Getter to retrieve the {@code CommonGoal}'s identifier.
     *
     * @return reference to the common goal the scoreTile refers to.
     */
    public int getCommonGoalID() {
        return this.commonGoalID;
    }

    /**
     * Setter to assign the {@code CommonGoal}'s identifier.
     *
     * @return the reference to the common goal the scoreTile refers to.
     */
    public void setCommonGoalID(int commonGoalID) {
        this.commonGoalID = commonGoalID;
    }

    /**
     * Gets the {@code ScoreTile}'s value.
     *
     * @return the value assigned to the ScoreTile.
     */
    public int getValue() {
        return this.value;
    }
}
