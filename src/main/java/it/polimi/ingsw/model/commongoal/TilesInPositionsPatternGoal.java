package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.TilesInPositionsPatternGoalView;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the goal pattern with all {@code Tile}s
 * disposed in the given positions of the {@code Board} .
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Board
 */
public class TilesInPositionsPatternGoal extends CommonGoal {
    //matrix that contains 1 in positions where there must be same colour tiles, otherwise 0
    private final List<List<Integer>> positions;

    /**
     * Class constructor without parameters.
     * Builds a TilesInPositionsPatternGoal.
     */
    public TilesInPositionsPatternGoal() {
        super();
        this.positions = new ArrayList<>();
    }

    /**
     * Class constructor with parameters.
     * Builds an TilesInPositionsPatternGoal with a specified type, ID, ...
     *
     * @param imageID the image assigned to the card.
     * @param patternRepetition contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param positions the matrix that contains 1 in positions where there must be same colour tiles, otherwise 0.
     */
    public TilesInPositionsPatternGoal(int id, int patternRepetition, CheckType type, List<List<Integer>> positions) {
        super(id, patternRepetition, type);
        this.positions = positions;
    }

    /**
     * Class constructor with parameters.
     * Builds an TilesInPositionsPatternGoal with a specified type, ID, ...
     * (numberOfPlayers and commonGoalID are also considered in this version).
     *
     * @param imageID the image assigned to the card.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param positions the matrix that contains 1 in positions where there must be same colour tiles, otherwise 0.
     * @param numberOfPlayers number of active players.
     * @param commonGoalID the identifier of the given common goal.
     */
    public TilesInPositionsPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, List<List<Integer>> positions) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
        this.positions = positions;
    }


    /**
     * Counts the number of 1 in the 'positions' matrix.
     *
     * @return the total number of 1 that compares in the assigned matrix.
     */
    private int numberOfElement() {
        int numberOfElement = 0;
        for (int i = 0; i < this.positions.size(); i++) {
            for (int j = 0; j < this.positions.get(0).size(); j++) {
                if (this.positions.get(i).get(j) == 1) {
                    numberOfElement++;
                }
            }
        }
        return numberOfElement;
    }

    /**
     * Searches the number of pattern repetitions in the {@code Bookshelf} of the {@code Player} by declaring a support matrix of the same dimensions of the bookshelf,
     * for every not null tile it assigns a 1 in the support matrix ( 0 for the nulls).
     * Starting from the first not null {@code Tile}, assigns in the support matrix in the position of the tile the group 2
     * then searches if the nearby tiles are of the same colour and, if it results true, assigns the same group of the first tile.
     *
     * In the second part we count the number of different groups that have at least 1 correspondence with the one's in the matrix positions.
     *
     * @params bookshelf contains the bookshelf of the player.
     * @return generalCounter contains the number of different groups that have at least 1 correspondence with the one's in the matrix positions.
     * @param bookshelf is the selected {@code Bookshelf}.
     * @return the number of repetitions of the TilesInPositionsPatternGoal.
     *
     * @see Bookshelf
     * @see it.polimi.ingsw.model.view.PlayerView
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        int[][] supportMatrix = new int[bookshelf.getNumberOfRows()][bookshelf.getNumberOfColumns()];

        for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                if (bookshelf.getSingleTile(row, column) == null) {
                    supportMatrix[row][column] = 0;
                } else {
                    supportMatrix[row][column] = 1;
                }
            }
        }

        int group = 1;

        for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                if (supportMatrix[row][column] == 1) {
                    group++;
                    searchGroup(bookshelf, supportMatrix, row, column, group, bookshelf.getSingleTile(row, column).getColor());
                }
            }
        }

        //Control if I have to do the 2x2 or the sequence of tiles

        //Here I control if there is the number of element that is required
        int counterGeneral = 0;
        int numberOfCorrispective = 0;
        int counterGroup = 0;

        for (int g = 2; g <= group; g++) {
            for (int row = 0; row < bookshelf.getNumberOfRows() - this.positions.size() + 1; row++) {
                for (int column = 0; column < bookshelf.getNumberOfColumns() - this.positions.get(0).size() + 1; column++) {
                    if (supportMatrix[row][column] == g) {
                        for (int k = 0; k < this.positions.size(); k++) {
                            for (int h = 0; h < this.positions.get(0).size(); h++) {

                                if (this.positions.get(k).get(h) == 1 && ((row + k) < bookshelf.getNumberOfRows()) && ((column + h) < bookshelf.getNumberOfColumns()) && supportMatrix[row + k][column + h] == g) {
                                    numberOfCorrispective++;
                                }
                            }
                        }
                        if (numberOfCorrispective == numberOfElement()) {
                            counterGroup++;
                        }
                        numberOfCorrispective = 0;
                    }
                }
            }
            if (counterGroup > 0) {
                counterGeneral++;
            }
            counterGroup = 0;
        }
        return counterGeneral;
    }

    /**
     * Applies the search pattern algorithm on the {@code Player}'s {@code Bookshelf},
     * confronting the currentTileColor on adjacent rows and lines.
     * Also, a support matrix is used when necessary to keep track of the current group of tiles,
     * each group is distinguished for both number of tiles and color.
     *
     * @param bookshelf the bookshelf of the current active player.
     * @param supportMatrix the matrix used as a support during the algorithm's unfolding.
     * @param row the current row.
     * @param column the current column.
     * @param group the group assigned to the current set of tiles.
     * @param currentTileColor the color of the actual group of tiles.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Bookshelf
     */
    private void searchGroup(Bookshelf bookshelf, int[][] supportMatrix, int row, int column, int group, TileColor currentTileColor) {
        if ((supportMatrix[row][column] == 1) && currentTileColor.equals(bookshelf.getSingleTile(row, column).getColor())) {
            supportMatrix[row][column] = group;

            //Control superior Tile
            if (row != 0) {
                searchGroup(bookshelf, supportMatrix, row - 1, column, group, currentTileColor);
            }

            if (column != 0) {
                searchGroup(bookshelf, supportMatrix, row, column - 1, group, currentTileColor);
            }

            if (row != bookshelf.getNumberOfRows() - 1) {
                searchGroup(bookshelf, supportMatrix, row + 1, column, group, currentTileColor);
            }

            if (column != bookshelf.getNumberOfColumns() - 1) {
                searchGroup(bookshelf, supportMatrix, row, column + 1, group, currentTileColor);
            }
        }
    }

    /**
     * Getter for identifying pattern's tiles positions.
     *
     * @return A matrix that contains 1 in positions where there must be same colour tiles, otherwise 0.
     */
    public List<List<Integer>> getPositions() {
        return this.positions;
    }

    /**
     * This method will be redefined in each common goal and will serve to print on the terminal the current type of common goal.
     *
     * @return an immutable copy of the TilesInPositionsPatternGoalView.
     *
     * @see CommonGoal
     */
    @Override
    public CommonGoalView copyImmutable() {
        return new TilesInPositionsPatternGoalView(this);
    }
}


