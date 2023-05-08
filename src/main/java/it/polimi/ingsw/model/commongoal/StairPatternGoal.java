package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.StairPatternGoalView;

public class StairPatternGoal extends CommonGoal {
    //Constructors
    public StairPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

    public StairPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
    }
    /*
    Start from the first column, if there are minimum 1 and at least the number of rows minus 3 tiles, control if the bookshelf have a stair (one more tile in the consecutive column) x4,
    then if there are minimum 5 tiles on the first column and haven't found a stair yet control if the bookshelf have a stair (one less tile in the consecutive column) x4
    @param bookshelf contains the bookshelf of the player
    @return 1 if found a stair, otherwise 0
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
    /*
    @return an immutable copy of the common goal
    */
    @Override
    public CommonGoalView copyImmutable() {
        return new StairPatternGoalView(this);
    }
    /*
    Redefine the equals method
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof StairPatternGoal obj) {
            return this.getNumberOfPatternRepetitionsRequired() == obj.getNumberOfPatternRepetitionsRequired()
                    && this.getType() == obj.getType();
        }
        return false;
    }
}
