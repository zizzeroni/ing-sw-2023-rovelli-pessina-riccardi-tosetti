package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is used to represent the common goal cards that are split
 * between the active {@code Player}s in the lobby, when the {@code Game} is created.
 * Every goal is represented through its type, image and number of pattern repetitions
 * necessary to achieve it.
 * It contains a series of getters/setters to retrieve the different values associated
 * to the common goal's card.
 *
 * @see it.polimi.ingsw.model.Game
 * @see it.polimi.ingsw.model.Player
 */
public abstract class CommonGoal extends Card {
    //contains the number of times the personal goal must be completed to take the score tile.
    private int numberOfPatternRepetitionsRequired;
    //contains the type of check must be done.
    private CheckType type;
    //contains the list of the scoring tiles.
    private List<ScoreTile> scoreTiles;

    /**
     * Class constructor without parameters.
     * Builds a CommonGoal with no type, ID, ...
     */

    public CommonGoal() {
        super();
        this.type = null;
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
        this.numberOfPatternRepetitionsRequired = 0;
    }

    /**
     * Class constructor with parameters.
     * Builds a CommonGoal with a specific type, ID, ...
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     */
    public CommonGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type) {
        super(id);
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
        this.type = type;
        this.scoreTiles = new ArrayList<>(Arrays.asList(new ScoreTile(8), new ScoreTile(6), new ScoreTile(4), new ScoreTile(2)));
    }

    /**
     * Class constructor with parameters.
     * Builds a CommonGoal with specific type, ID ...
     * (numberOfPlayers and commonGoalID are also considered).
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param numberOfPlayers number of active players.
     * @param commonGoalID the identifier of the given common goal.
     */
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

    /**
     * Gets the list of score tiles.
     *
     * @return the list of score tiles.
     */
    public List<ScoreTile> getScoreTiles() {
        return this.scoreTiles;
    }

    public void setScoreTiles(List<ScoreTile> scoreTiles) {
        this.scoreTiles = scoreTiles;
    }

    /**
     * Gets the number of times the {@code PersonalGoal} must be completed to take the {@code ScoreTile}.
     *
     * @return the number of times the personal goal must be completed to take the score tile.
     *
     * @see it.polimi.ingsw.model.PersonalGoal
     * @see ScoreTile
     */
    public int getNumberOfPatternRepetitionsRequired() {
        return this.numberOfPatternRepetitionsRequired;
    }

    /**
     * Gets the type of check that has to be done on the considered common goal's card (inherently to the card's depicted pattern).
     *
     * @return the type's associated value.
     *
     */
    public CheckType getType() {
        return this.type;
    }

    public void setNumberOfPatternRepetitionsRequired(int numberOfPatternRepetitionsRequired) {
        this.numberOfPatternRepetitionsRequired = numberOfPatternRepetitionsRequired;
    }

    /**
     * Setter user to assign the type of the considered {@code CommonGoal}.
     *
     * @param type the given type value.
     *
     * @see CommonGoal
     */
    public void setType(CheckType type) {
        this.type = type;
    }

    /**
     * Initialize the different {@code ScoreTile}s
     * with their respective values basing assignment on
     * the number of {@code Player}s for the current {@code Game}.
     *
     * @param numberOfPlayers contains the number of players in the game.
     * @param commonGoalID represents the common goal used in the game.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.Game
     * @see ScoreTile
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

    /**
     * This method will be redefined in each common goal and will serve to print on the terminal the common goal.
     *
     * @return an immutable copy of the common goal.
     *
     * @see CommonGoal
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
