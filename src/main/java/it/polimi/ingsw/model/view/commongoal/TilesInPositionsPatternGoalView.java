package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.commongoal.TilesInPositionsPatternGoal;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.io.Serializable;

public class TilesInPositionsPatternGoalView extends CommonGoalView {
    private final int[][] positions;

    public TilesInPositionsPatternGoalView(TilesInPositionsPatternGoal commonGoalModel) {
        super(commonGoalModel);
        this.positions = commonGoalModel.getPositions();
    }

    public int[][] getPositions() {
        return positions;
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles\n" +
                "of one square can be different from those of the other square.\n");

        for (int i = 0; i < positions.length; i++) {
            sendBack.append("[");
            for (int j = 0; j<positions[0].length; j++) {
                if(positions[i][j]==1){
                    sendBack.append(" B");
                }else{
                    sendBack.append(" -");
                }
            }
            sendBack.append(" ]\n");
        }

        sendBack.append("x 2 times \n");
        return sendBack.toString();
    }

}
