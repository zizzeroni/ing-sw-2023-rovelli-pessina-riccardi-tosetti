package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.DiagonalEqualPattern;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

public class DiagonalEqualPatternView extends CommonGoalView {
    private final int[][] pattern;

    public DiagonalEqualPatternView(DiagonalEqualPattern commonGoalModel) {
        super(commonGoalModel);
        this.pattern = commonGoalModel.getPattern();
    }

    public int[][] getPattern() {
        return this.pattern;
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Tiles of the same type forming this pattern:\n");

        for (int i = 0; i < this.pattern.length; i++) {
            sendBack.append("[");
            for (int j = 0; j < this.pattern[0].length; j++) {
                if (this.pattern[i][j] == 1) {
                    sendBack.append(" "+ TileColor.BLUE+" ");
                } else {
                    sendBack.append(" -");
                }
            }
            sendBack.append(" ]\n");
        }

        sendBack.append("x 1 time \n");
        return sendBack.toString();
    }
}
