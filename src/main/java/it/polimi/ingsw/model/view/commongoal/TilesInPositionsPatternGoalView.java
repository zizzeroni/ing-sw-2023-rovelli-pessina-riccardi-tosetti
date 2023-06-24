package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.TilesInPositionsPatternGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

public class TilesInPositionsPatternGoalView extends CommonGoalView {
    private final int[][] positions;

    public TilesInPositionsPatternGoalView(TilesInPositionsPatternGoal commonGoalModel) {
        super(commonGoalModel);
        this.positions = new int[commonGoalModel.getPositions().length][commonGoalModel.getPositions()[0].length];
        for (int row = 0; row < this.positions.length; row++) {
            for (int column = 0; column < this.positions[0].length; column++) {
                this.positions[row][column] = commonGoalModel.getPositions()[row][column];
            }
        }
    }

    public int[][] getPositions() {
        return this.positions;
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles\n" +
                "of one square can be different from those of the other square.\n");

        for (int i = 0; i < this.positions.length; i++) {
            sendBack.append("[");
            for (int j = 0; j < this.positions[0].length; j++) {
                if (this.positions[i][j] == 1) {
                    sendBack.append(" ").append(TileColor.BLUE);
                } else {
                    sendBack.append(" -");
                }
            }
            sendBack.append(" ]\n");
        }

        sendBack.append("x 2 times \n");
        return sendBack.toString();
    }

}
