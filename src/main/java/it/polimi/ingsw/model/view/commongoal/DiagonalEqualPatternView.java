package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.DiagonalEqualPattern;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.io.Serializable;

public class DiagonalEqualPatternView extends CommonGoalView {
    private final int[][] pattern;

    public DiagonalEqualPatternView(DiagonalEqualPattern commonGoalModel) {
        super(commonGoalModel);
        this.pattern = commonGoalModel.getPattern();
    }

    public int[][] getPattern() {
        return pattern;
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Tiles of the same type forming this pattern:\n\n");

        for (int i = 0; i < pattern.length; i++) {
            sendBack.append("[");
            for (int j = 0; j<pattern[0].length; j++) {
                if(pattern[i][j]==1){
                    sendBack.append(" B");
                }
                else{
                    sendBack.append(" -");
                }
            }
            sendBack.append(" ]\n");
        }

        sendBack.append("x 1 time \n\n");
        return sendBack.toString();
    }
}
