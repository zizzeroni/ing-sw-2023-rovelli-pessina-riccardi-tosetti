package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.DiagonalEqualPatternView;
import it.polimi.ingsw.model.view.commongoal.StairPatternGoalView;

public class StairPatternGoal extends CommonGoal {
    public StairPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

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
    @Override
    public CommonGoalView copyImmutable() {
        return new StairPatternGoalView(this);
    }
}
