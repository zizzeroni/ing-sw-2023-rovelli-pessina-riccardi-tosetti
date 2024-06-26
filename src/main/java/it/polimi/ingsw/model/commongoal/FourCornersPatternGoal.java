package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
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
    /**
     * Class constructor without parameters.
     * Builds a FourCornersPatternGoal.
     */
    public FourCornersPatternGoal() {
    }

    /**
     * Class constructor with parameters.
     * Builds an FourCornersPatternGoal with a specified type, ID, ...
     *
     * @param id                the identifier assigned to the commonGoal card.
     * @param patternRepetition contains the number of times the personal goal must be completed to take the score tile.
     * @param type              the type of check that has to be done on the considered common goal's card.
     */
    public FourCornersPatternGoal(int id, int patternRepetition, CheckType type) {
        super(id, patternRepetition, type);
    }

    /**
     * Class constructor with parameters.
     * Builds a FourCornersPatternGoal with specific type, ID ...
     * (numberOfPlayers and commonGoalID are also considered).
     *
     * @param id                                 the identifier assigned to the commonGoal card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type                               the type of check that has to be done on the considered common goal's card.
     * @param numberOfPlayers                    number of active players.
     */
    public FourCornersPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
    }

    /**
     * Class constructor with parameters.
     * Builds a FourCornersPatternGoal with specific type, ID ...
     * (scoreTiles parameter is also considered).
     *
     * @param id                                 the identifier assigned to the commonGoal card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type                               the type of check that has to be done on the considered common goal's card.
     * @param scoreTiles                         list of current score tiles.
     */
    public FourCornersPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
    }

    /**
     * Check if there are {@code Tile}s of the same color in the 4 corners of the {@code Bookshelf}.
     *
     * @param bookshelf contains the bookshelf of the {@code Player}.
     * @return the number of times the pattern is achieved.
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
     * Generates an immutable copy of the current {@code commonGoal}.
     *
     * @return an immutable copy of the FourCornersPatternGoalView.
     */
    @Override
    public FourCornersPatternGoalView copyImmutable() {
        return new FourCornersPatternGoalView(this);
    }
}
