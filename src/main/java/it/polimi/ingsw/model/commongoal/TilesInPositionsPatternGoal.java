package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.TilesInPositionsPatternGoalView;

/**
 * Class to represent the goal pattern with all {@code Tile}s
 * disposed in the given positions of the {@code Board} .
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Board
 */
public class TilesInPositionsPatternGoal extends CommonGoal {
    //matrix that contains 1 in positions where there must be same colour tiles, otherwise 0
    private final int[][] positions;

    /**
     * Class constructor without parameters.
     * Builds a TilesInPositionsPatternGoal.
     */
    public TilesInPositionsPatternGoal() {
        super();
        this.positions = new int[0][0];
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
    public TilesInPositionsPatternGoal(int imageID, int patternRepetition, CheckType type, int[][] positions) {
        super(imageID, patternRepetition, type);
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
    public TilesInPositionsPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID, int[][] positions) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
        this.positions = positions;
    }
    /**
     * Counts the number of 1 in the 'positions' matrix.
     *
     * @return the total number of 1 that compares in the assigned matrix.
     */
    public int numberOfElement() {
        int numberOfElement = 0;
        for (int i = 0; i < this.positions.length; i++) {
            for (int j = 0; j < this.positions[0].length; j++) {
                if (this.positions[i][j] == 1) {
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

        for (int i = 0; i < bookshelf.getNumberOfRows(); i++) {
            for (int j = 0; j < bookshelf.getNumberOfColumns(); j++) {
                if (bookshelf.getSingleTile(i, j) == null) {
                    supportMatrix[i][j] = 0;
                } else {
                    supportMatrix[i][j] = 1;
                }
            }
        }

        int group = 1;

        for (int i = 0; i < bookshelf.getNumberOfRows(); i++) {
            for (int j = 0; j < bookshelf.getNumberOfColumns(); j++) {
                if (supportMatrix[i][j] == 1) {
                    group++;
                    searchGroup(bookshelf, supportMatrix, i, j, group, bookshelf.getSingleTile(i, j).getColor());
                }
            }
        }

        //Control if I have to do the 2x2 or the sequence of tiles

        //Here I control if there is the number of element that is required
        int counterGeneral = 0;
        int numberOfCorrispective = 0;
        int counterGroup = 0;

        for (int g = 2; g <= group; g++) {
            for (int row = 0; row < bookshelf.getNumberOfRows() - this.positions.length + 1; row++) {
                for (int column = 0; column < bookshelf.getNumberOfColumns() - this.positions[0].length + 1; column++) {
                    if (supportMatrix[row][column] == g) {
                        for (int k = 0; k < this.positions.length; k++) {
                            for (int h = 0; h < this.positions[0].length; h++) {

                                if (this.positions[k][h] == 1 && ((row + k) < bookshelf.getNumberOfRows()) && ((column + h) < bookshelf.getNumberOfColumns()) && supportMatrix[row + k][column + h] == g) {
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
    //method get
    public int[][] getPositions() {
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
    /**
     * Redefine the equals method to allow a compare based on the TilesInPositionsPatternGoal.
     *
     * @param o is the object being evaluated to be equals to another (the one that calls the method).
     * @return {@code true} if and only if the two tiles are 'equals',
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof TilesInPositionsPatternGoal obj) {
            return this.getNumberOfPatternRepetitionsRequired() == obj.getNumberOfPatternRepetitionsRequired()
                    && this.getType() == obj.getType()
                    && this.getPositions() == obj.getPositions();
        }
        return false;
    }
}


