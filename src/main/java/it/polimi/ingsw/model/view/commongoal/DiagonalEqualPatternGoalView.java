package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.DiagonalEqualPattern;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the View of the {@code DiagonalEqualPattern}.
 * It contains the necessary components for displaying the pattern and referencing it.
 * This can be done through a method to identify the response to a given situation regarding
 * this goal pattern achievement (toString).
 * It also provides a method to directly access the class pattern which is linked to other
 * usages in different classes.
 *
 * @see it.polimi.ingsw.model.commongoal.DiagonalEqualPattern
 */
public class DiagonalEqualPatternGoalView extends CommonGoalView {
    private final List<List<Integer>> pattern;

    /**
     * Class constructor.
     * Assign the values as in the {@code CommonGoal} parameter, commonGoalModel.
     *
     * @param commonGoalModel the referencing class for the call of the super method in the constructor
     *                         in order to make possible the construction of the class object.
     */
    public DiagonalEqualPatternGoalView(DiagonalEqualPattern commonGoalModel) {
        super(commonGoalModel);
        this.pattern = new ArrayList<>(commonGoalModel.getPattern());
    }

    /**
     * Getter to access the diagonal pattern.
     *
     * @return the diagonal pattern.
     */
    public List<List<Integer>> getPattern() {
        return this.pattern;
    }

    /**
     * This method is used to display the result of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code DiagonalEqualPattern}).
     *
     * @return a text message associated to the various combinations of tiles associated
     *          that may satisfy the conditions to verify the DiagonalEqualPattern.
     *
     * @see it.polimi.ingsw.model.commongoal.DiagonalEqualPattern
     */
    @Override
    public String toString() {
        StringBuilder sendBack = new StringBuilder("Tiles of the same type forming this pattern:\n");

        for (int i = 0; i < this.pattern.size(); i++) {
            sendBack.append("[");
            for (int j = 0; j < this.pattern.get(0).size(); j++) {
                if (this.pattern.get(i).get(j) == 1) {
                    sendBack.append(" " + TileColor.BLUE + " ");
                } else {
                    sendBack.append("-");
                }
            }
            sendBack.append(" ]\n");
        }

        sendBack.append("x 1 time \n");
        return sendBack.toString();
    }
}
