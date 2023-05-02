package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.DiagonalEqualPatternView;
import it.polimi.ingsw.model.view.commongoal.FourCornersPatternGoalView;

public class FourCornersPatternGoal extends CommonGoal {
    public FourCornersPatternGoal() {
    }

    public FourCornersPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

    public FourCornersPatternGoal(int imageID, int patternRepetition, CheckType type, int numberOfPlayers) {
        super(imageID, patternRepetition, type, numberOfPlayers);
    }

    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        return (bookshelf.getSingleTile(0, 0) != null && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1) != null
                && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0) != null && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, bookshelf.getNumberOfColumns() - 1) != null)

                && (bookshelf.getSingleTile(0, 0).getColor().equals(bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1).getColor())
                && bookshelf.getSingleTile(0, bookshelf.getNumberOfColumns() - 1).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0).getColor())
                && bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, 0).getColor().equals(bookshelf.getSingleTile(bookshelf.getNumberOfRows() - 1, bookshelf.getNumberOfColumns() - 1).getColor())) ? 1 : 0;
    }
    @Override
    public CommonGoalView copyImmutable() {
        return new FourCornersPatternGoalView(this);
    }
}
