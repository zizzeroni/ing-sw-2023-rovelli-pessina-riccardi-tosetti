package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.DiagonalEqualPattern;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

public class DiagonalEqualPatternGoalView extends CommonGoalView {
    private final int[][] pattern;

    public DiagonalEqualPatternGoalView(DiagonalEqualPattern commonGoalModel) {
        super(commonGoalModel);
        this.pattern = new int[commonGoalModel.getPattern().length][commonGoalModel.getPattern()[0].length];
        for (int row = 0; row < this.pattern.length; row++) {
            for (int column = 0; column < this.pattern[0].length; column++) {
                this.pattern[row][column] = commonGoalModel.getPattern()[row][column];
            }
        }
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
                    sendBack.append(" " + TileColor.BLUE + " ");
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
