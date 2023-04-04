package model.commongoal;

import model.Bookshelf;

public class StairPatternGoal extends CommonGoal {
    public StairPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        int column = 0;
        int numberOfTilesInColumn = bookshelf.getNumberOfTilesInColumn(column);
        //getNumElementInColumn è il metodo per ritornare il numero di elmenti su una determinata colonna

        if(numberOfTilesInColumn != 0) {
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

//    public class StairPatternGoal extends CommonGoal {
//        public StairPatternGoal(int imageID, int patternRepetition, CheckType type) {
//            super(imageID, patternRepetition, type);
//        }
//
//        public int goalPattern(Bookshelf b) {
//            int column = 0;
//            int numberOfTilesInColumn = b.getNumberOfTilesInColumn(column);
//            //getNumElementInColumn è il metodo per ritornare il numero di elmenti su una determinata colonna
//
//            if(numberOfTilesInColumn == 0) {
//                return 0;
//            }
//
//            if(numberOfTilesInColumn < 3){
//                for(int i = 0; i < b.getNumColumns(); i++) {
//                    if (++numberOfTilesInColumn != b.getNumberOfTilesInColumn(++column)) {
//                        return 0;
//                    }
//                }
//            }
//
//            column = 0;
//            numberOfTilesInColumn = b.getNumberOfTilesInColumn(column);
//
//            if(numberOfTilesInColumn > 4){
//                for(int i = 0; i < b.getNumColumns(); i++) {
//                    if(--numberOfTilesInColumn != b.getNumberOfTilesInColumn(++column)) {
//                        return 0;
//                    }
//                }
//            }
//
//            return 1;
//        }

}
