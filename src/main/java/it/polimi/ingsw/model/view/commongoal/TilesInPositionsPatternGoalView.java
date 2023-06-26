package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.TilesInPositionsPatternGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.util.ArrayList;
import java.util.List;

public class TilesInPositionsPatternGoalView extends CommonGoalView {
    private final List<List<Integer>> positions;

    public TilesInPositionsPatternGoalView(TilesInPositionsPatternGoal commonGoalModel) {
        super(commonGoalModel);
        //TODO: Controllare se è corretto
        this.positions = new ArrayList<>(commonGoalModel.getPositions());
        /*for (int row = 0; row < commonGoalModel.getPositions().size(); row++) {
            for (int column = 0; column < commonGoalModel.getPositions().get(0).size(); column++) {
                this.positions.get(row).set(column, commonGoalModel.getPositions().get(row).get(column));
            }
        }*/
    }

    public List<List<Integer>> getPositions() {
        return this.positions;
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles\n" +
                "of one square can be different from those of the other square.\n");

        for (int i = 0; i < this.positions.size(); i++) {
            sendBack.append("[");
            for (int j = 0; j < this.positions.get(0).size(); j++) {
                if (this.positions.get(i).get(j) == 1) {
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
