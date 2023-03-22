package model.commongoal;

import model.Bookshelf;

public class StairPatternGoal extends CommonGoal {
    public StairPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {
        int column = 0;
        int NumElColumn = b.getNumElemColumn(column);
        //getNumElementInColumn è il metodo per ritornare il numero di elmenti su una determinata colonna

        if(NumElColumn < b.getNumRows()-3){
            if(NumElColumn+1 == b.getNumElemColumn(column+1) &&
                    NumElColumn+2 == b.getNumElemColumn(column+2) &&
                    NumElColumn+3 == b.getNumElemColumn(column+3) &&
                    NumElColumn+4 == b.getNumElemColumn(column+4)){
                return 1;
            }
        }
        if(NumElColumn > 3){
            if(NumElColumn-1 == b.getNumElemColumn(column+1) &&
                    NumElColumn-2 == b.getNumElemColumn(column+2) &&
                    NumElColumn-3 == b.getNumElemColumn(column+3) &&
                    NumElColumn-4 == b.getNumElemColumn(column+4)){
                return 1;
            }
        }

        return 0;
    }
}
