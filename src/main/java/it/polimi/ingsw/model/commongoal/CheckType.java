package it.polimi.ingsw.model.commongoal;

/**
 * Enumeration that defines the type of check that must be done on {@code CommonGoal} pattern.
 * <p>
 * It indicates whether the tiles in the line must be of the same, different or a randomized color.
 *
 * @see CommonGoal
 */
public enum CheckType {
    //Whether the tiles in the line must be of the same color.
    EQUALS,
    //Whether the tiles in the line must be different.
    DIFFERENT,
    //Whether the tiles in the line can be random.
    INDIFFERENT,
}
