package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.FourCornersPatternGoalView;

import java.util.List;

public class FourCornersPatternGoal extends CommonGoal {
    //Constructors
    public FourCornersPatternGoal() {
    }

    public FourCornersPatternGoal(int id, int patternRepetition, CheckType type) {
        super(id, patternRepetition, type);
    }

    public FourCornersPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
    }

    public FourCornersPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
    }

    /*
    Check if there are tiles of the same color in the 4 corners of the bookshelf
    @param bookshelf contains the bookshelf of the player
    @return true if the condition is satisfied, otherwise false
     */
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        return (bookshelf.getSingleTile(0, 0) != null && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1) != null
                && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0) != null && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, bookshelf.getNumberOfColumns() - 1) != null)
                && (bookshelf.getSingleTile(0, 0).getColor().equals(bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1).getColor())
                && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0).getColor())
                && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, bookshelf.getNumberOfColumns() - 1).getColor())) ? 1 : 0;
    }

    /*
    @return an immutable copy of the common goal
    */
    @Override
    public CommonGoalView copyImmutable() {
        return new FourCornersPatternGoalView(this);
    }
}
