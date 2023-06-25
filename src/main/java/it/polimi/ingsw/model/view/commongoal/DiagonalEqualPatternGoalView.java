package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.DiagonalEqualPattern;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.util.ArrayList;
import java.util.List;

public class DiagonalEqualPatternGoalView extends CommonGoalView {
    private final List<List<Integer>> pattern;

    public DiagonalEqualPatternGoalView(DiagonalEqualPattern commonGoalModel) {
        super(commonGoalModel);
        this.pattern = new ArrayList<>();
        for (int row = 0; row < commonGoalModel.getPattern().size(); row++) {
            for (int column = 0; column < commonGoalModel.getPattern().get(0).size(); column++) {
                this.pattern.get(row).set(column, commonGoalModel.getPattern().get(row).get(column));
            }
        }
    }

    public List<List<Integer>> getPattern() {
        return this.pattern;
    }

    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Tiles of the same type forming this pattern:\n");

        for (int i = 0; i < this.pattern.size(); i++) {
            sendBack.append("[");
            for (int j = 0; j < this.pattern.get(0).size(); j++) {
                if (this.pattern.get(i).get(j) == 1) {
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
