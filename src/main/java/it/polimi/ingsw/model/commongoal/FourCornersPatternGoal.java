package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.FourCornersPatternGoalView;

import java.util.List;

/**
 * Class to represent the goal pattern with all {@code Tile}s
 * disposed at the four corners of the {@code Board} .
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Board
 */
public class FourCornersPatternGoal extends CommonGoal {

    //Constructors
    /**
     * Class constructor without parameters.
     * Builds a FourCornersPatternGoal.
     */
    public FourCornersPatternGoal() {
    }

    /**
     *
     * Class constructor with parameters.
     * Builds an FourCornersPatternGoal with a specified type, ID, ...
     *
     * @param id the identifier assigned to the card.
     * @param patternRepetition contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     *
     */
    public FourCornersPatternGoal(int id, int patternRepetition, CheckType type) {
        super(id, patternRepetition, type);
    }

    /**
     *
     * Class constructor with parameters.
     * Builds a FourCornersPatternGoal with specific type, ID ...
     * (numberOfPlayers and commonGoalID are also considered).
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param numberOfPlayers number of active players.
     * @param commonGoalID the identifier of the given common goal.
     */
    public FourCornersPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
    }

    public FourCornersPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
    }

    /**
     * Check if there are {@code Tile}s of the same color in the 4 corners of the {@code Bookshelf}.
     *
     * @param bookshelf contains the bookshelf of the {@code Player}.
     * @return {@code true} if and only if the initial condition is satisfied,
     *         {@code false} otherwise.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Player
     * @see Bookshelf
     */
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        return (bookshelf.getSingleTile(0, 0) != null && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1) != null
                && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0) != null && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, bookshelf.getNumberOfColumns() - 1) != null)
                && (bookshelf.getSingleTile(0, 0).getColor().equals(bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1).getColor())
                && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0).getColor())
                && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, bookshelf.getNumberOfColumns() - 1).getColor())) ? 1 : 0;
    }
    /**
     * This method will be redefined in each common goal and will serve to print on the terminal the current type of common goal.
     *
     * @return an immutable copy of the FourCornersPatternGoalView.
     *
     * @see CommonGoal
     */
    @Override
    public CommonGoalView copyImmutable() {
        return new FourCornersPatternGoalView(this);
    }
}
