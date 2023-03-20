package model.commongoal;

import model.Bookshelf;

public class StairPatternGoal extends CommonGoal {
    public StairPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf B) {
        int column = 0;
        int NumElementInColumn = B.getNumElementInColumn(column);

        //getNumElementInColumn è il metodo per ritornare il numero di elmenti su una determinata colonna

        if(NumElementInColumn==0 || NumElementInColumn==1){
            if(NumElementInColumn+1 = B.getNumElementInColumn(column+1) &&
                    NumElementInColumn+2 = B.getNumElementInColumn(column+2) &&
                    NumElementInColumn+3 = B.getNumElementInColumn(column+3) &&
                    NumElementInColumn+4 = B.getNumElementInColumn(column+4)){
                return 1;
            }
        }

        if(NumElementInColumn==5 || NumElementInColumn==4){
            if(NumElementInColumn-1 = B.getNumElementInColumn(column-1) &&
                    NumElementInColumn-2 = B.getNumElementInColumn(column-2) &&
                    NumElementInColumn-3 = B.getNumElementInColumn(column-3) &&
                    NumElementInColumn-4 = B.getNumElementInColumn(column-4)){
                return 1;
            }
        }

        return 0;
    }
}
