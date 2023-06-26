package it.polimi.ingsw.model.view.commongoal;

import it.polimi.ingsw.model.commongoal.TilesInPositionsPatternGoal;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;

<<<<<<< HEAD
/**
 * This class represents the View of the {@code TilesInPositionsPatternGoal}.
 * It contains the necessary components for displaying the pattern and referencing it.
 * This can be done through a method to identify the response to a given situation regarding
 * this goal pattern achievement (toString).
 * It also provides a getter to access the matrix of tiles positions, peculiar to this type of pattern.
 *
 * @see it.polimi.ingsw.model.commongoal.TilesInPositionsPatternGoal
 */
=======
import java.util.ArrayList;
import java.util.List;

>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd
public class TilesInPositionsPatternGoalView extends CommonGoalView {
    private final List<List<Integer>> positions;

    /**
     * Class constructor.
     * Assign the values as in the {@code CommonGoal} parameter, commonGoalModel.
     *
     * @param commonGoalModel the referencing class for the call of the super method in the constructor
     *                         in order to make possible the construction of the class object.
     */
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

<<<<<<< HEAD
    /**
     * Getter for identifying pattern's tiles positions.
     *
     * @return A matrix, linked to the particular class pattern, that contains 1
     *          in positions where there must be same colour tiles, otherwise 0.
     *
     * @see TilesInPositionsPatternGoalView
     */
    public int[][] getPositions() {
=======
    public List<List<Integer>> getPositions() {
>>>>>>> 859bad82d69f5d3a13cbdcd56fcc32f950648cfd
        return this.positions;
    }

    /**
     * This method is used to display the result of the user's interactions with the view
     * during the {@code Game} (those that produced a {@code TilesInPositionsPatternGoal}).
     *
     * @return a text message associated to the various combinations of tiles associated
     *          that may satisfy the conditions to verify the TilesInPositionsPatternGoal.
     *
     * @see it.polimi.ingsw.model.commongoal.TilesInPositionsPatternGoal
     */
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
