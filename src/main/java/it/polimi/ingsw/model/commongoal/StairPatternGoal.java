package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.commongoal.StairPatternGoalView;

import java.util.List;

/**
 * Class to represent the goal pattern with all {@code Tile}s
 * disposed to form a stair-shaped figure on the {@code Board} .
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Board
 */
public class StairPatternGoal extends CommonGoal {
    /**
     * Class constructor.
     * Builds an StairPatternGoal with a specified type, ID, ...
     *
     * @param id                the identifier assigned to the card.
     * @param patternRepetition contains the number of times the personal goal must be completed to take the score tile.
     * @param type              the type of check that has to be done on the considered common goal's card.
     */
    public StairPatternGoal(int id, int patternRepetition, CheckType type) {
        super(id, patternRepetition, type);
    }

    /**
     * Class constructor.
     * Builds a StairPatternGoal with specific type, ID ...
     * (in this case numberOfPlayers is also considered).
     *
     * @param id                                 the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type                               the type of check that has to be done on the considered common goal's card.
     * @param numberOfPlayers                    number of active players.
     */
    public StairPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
    }

    /**
     * Class constructor.
     * Builds a StairPatternGoal with specific type, ID ...
     * (in this case scoreTiles is also considered).
     *
     * @param id                                 the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type                               the type of check that has to be done on the considered common goal's card.
     * @param scoreTiles                         list of current score tiles.
     */
    public StairPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
    }

    /**
     * Here we search the number of pattern repetition in the player's bookshelf.
     *
     * @param bookshelf contains the bookshelf of the player.
     * @return the number of times the pattern is achieved.
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Bookshelf#getNumberOfTilesInColumn(int)
     */
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        int column = 0;
        int numberOfTilesInColumn = bookshelf.getNumberOfTilesInColumn(column);
        //getNumberOfTilesInColumn è il metodo per ritornare il numero di elmenti su una determinata colonna

        if (numberOfTilesInColumn != 0) {
            if (numberOfTilesInColumn < bookshelf.getNumberOfRows() - 3) {
                if (numberOfTilesInColumn + 1 == bookshelf.getNumberOfTilesInColumn(column + 1) &&
                        numberOfTilesInColumn + 2 == bookshelf.getNumberOfTilesInColumn(column + 2) &&
                        numberOfTilesInColumn + 3 == bookshelf.getNumberOfTilesInColumn(column + 3) &&
                        numberOfTilesInColumn + 4 == bookshelf.getNumberOfTilesInColumn(column + 4)) {
                    return 1;
                }
            }
            if (numberOfTilesInColumn > 4) {
                if (numberOfTilesInColumn - 1 == bookshelf.getNumberOfTilesInColumn(column + 1) &&
                        numberOfTilesInColumn - 2 == bookshelf.getNumberOfTilesInColumn(column + 2) &&
                        numberOfTilesInColumn - 3 == bookshelf.getNumberOfTilesInColumn(column + 3) &&
                        numberOfTilesInColumn - 4 == bookshelf.getNumberOfTilesInColumn(column + 4)) {
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * Generates an immutable copy of the current {@code commonGoal}.
     *
     * @return an immutable copy of the FourCornersPatternGoalView.
     */
    @Override
    public StairPatternGoalView copyImmutable() {
        return new StairPatternGoalView(this);
    }
}
