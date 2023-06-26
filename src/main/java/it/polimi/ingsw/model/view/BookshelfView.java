package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 * This class implements the {@code BookshelfView} through serialization.
 * All the players always access only the implementation of the {@code View} of the relative {@code Bookshelf},
 * and are sensible to the inherent modifies.
 * Also, the class contains a series of getters to access the number of
 * rows/columns and to retrieve the selected tiles from the {@code Board},
 * given the current number of active players, the considered group of {@code Tile}s, ...
 *
 * @see Bookshelf
 * @see it.polimi.ingsw.model.Board
 * @see it.polimi.ingsw.model.tile.Tile
 */
public class BookshelfView implements Serializable {
    //private final Bookshelf bookshelfModel;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final String image;
    private final TileView[][] tiles;
    //private final Map<Integer, Integer> pointsForEachGroup;
    private final Map<Integer, Integer> pointsForEachGroup;

    /**
     * Class constructor.
     * Used to associate the representation of the {@code Bookshelf} in the {@code BookshelfView}
     * with the linked logic in the {@code bookshelfModel} (passed as parameter).
     *
     * @param bookshelfModel the model of the considered {@code Bookshelf}.
     *
     * @see Bookshelf
     *
     */
    public BookshelfView(Bookshelf bookshelfModel) {
        this.tiles = new TileView[bookshelfModel.getNumberOfRows()][bookshelfModel.getNumberOfColumns()];
        this.pointsForEachGroup = new HashMap<>();

        this.numberOfColumns = bookshelfModel.getNumberOfColumns();
        this.numberOfRows = bookshelfModel.getNumberOfRows();
        this.image = bookshelfModel.getImage();
        for (int row = 0; row < bookshelfModel.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelfModel.getNumberOfColumns(); column++) {
                this.tiles[row][column] = (bookshelfModel.getSingleTile(row, column) != null ? new TileView(bookshelfModel.getSingleTile(row, column)) : null);
            }
        }
        for (Integer key : bookshelfModel.getPointsForEachGroup().keySet()) {
            this.pointsForEachGroup.put(key, bookshelfModel.getPointsForEachGroup().get(key));
        }

    }

    public Map<Integer, Integer> getPointsForEachGroup() {
        return this.pointsForEachGroup;
    }

    public boolean isFull() {
        for (int column = 0; column < this.numberOfColumns; column++) {
            if (this.tiles[0][column] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the total number of empty cells in the given column.
     *
     * @param column the column to inspect.
     * @return the number of empty cells in column.
     */
    public int getNumberOfEmptyCellsInColumn(int column) {
        int counter = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] != null) {
                return counter;
            }
            counter++;
        }
        return counter;
    }

    /**
     * Getter to retrieve the {@code Bookshelf} image.
     *
     * @return the image of the Bookshelf.
     *
     * @see Bookshelf
     */
    public String getImage() {
        return this.image;
    }

    /**
     * Returns the set of tiles currently on the board.
     *
     * @return the {@code TileView} of the given {@code Board}.
     *
     * @see it.polimi.ingsw.model.Board
     * @see TileView
     */
    public TileView[][] getTiles() {
        return this.tiles;
    }

    /**
     * Returns the {@code Tile} correspondent to its coordinates,
     * passed as parameters.
     *
     * @param row the first coordinate of the tile.
     * @param column the second coordinate of the tile.
     * @return the tile identified.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public TileView getSingleTile(int row, int column) { // funzione estrazione singola Tile selezionata
        return this.tiles[row][column];
    }

    /**
     * Getter that returns the number of columns
     * in the {@code Bookshelf}.
     *
     * @return the number of columns.
     *
     * @see Bookshelf
     */
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getNumberOfTilesInColumn(int column) {
        int counter = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] != null)
                counter++;
        }
        return counter;
    }

    /**
     * Getter that returns the number of rows
     * in the {@code Bookshelf}.
     *
     * @return the number of rows.
     *
     * @see Bookshelf
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public boolean isRowFull(int row) {
        for (int column = 0; column < this.numberOfColumns; column++) {
            if (this.tiles[row][column] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the maximum number of empty {@code Bookshelf}'s cells.
     *
     * @return the maximum number of empty {@code Bookshelf}'s cells.
     *
     * @see Bookshelf
     */
    public int getMaxNumberOfCellsFreeInBookshelf() {
        int maxNumberOfCellsFreeInBookshelf = 0;
        for (int column = 0; column < this.numberOfColumns; column++) {
            int numberOfFreeCells = this.getNumberOfEmptyCellsInColumn(column);
            if (numberOfFreeCells > maxNumberOfCellsFreeInBookshelf) {
                maxNumberOfCellsFreeInBookshelf = numberOfFreeCells;
            }
        }
        return maxNumberOfCellsFreeInBookshelf;
    }

    public boolean isColumnFull(int column) {
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is used to display the result of the user's interactions
     * during the {@code Game} (those linked with the {@code Bookshelf}).
     *
     * @return the current display of the Bookshelf.
     *
     * @see it.polimi.ingsw.model.Game
     * @see Bookshelf
     */
    @Override
    public String toString() {
        String output = "    ";
        for (int column = 0; column < this.numberOfColumns; column++) {
            output += column + 1 + " ";
        }
        output += "\n";
        for (int row = 0; row < this.numberOfRows; row++) {
            output += (row + 1) + " [ ";
            for (int column = 0; column < this.numberOfColumns; column++) {
                TileView currentTile = this.tiles[row][column];
                if ((currentTile == null || currentTile.getColor() == null)) {
                    output += "0 ";
                } else {
                    output += (currentTile.getColor()) + " ";
                }
            }
            output += "] " + "\n";
        }
        return output.substring(0, output.length() - 1);
    }

    /**
     * Evaluates the score of the {@code Player}s considering whether if they have
     * accomplished an objective through the given group of tiles, a {@code CommonGoal}
     * or a {@code PersonalGoal}.
     * If the number of tiles is below the first goal available, no points are assigned.
     * If the number of tiles is over the last goal available, given points remain equal to the last goal points.
     *
     * @return the current score.
     * @throws Exception prints a message error in case Bookshelf points have not been set.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.commongoal.CommonGoal
     * @see it.polimi.ingsw.model.PersonalGoal
     */
    public int score() throws Exception {
        int score = 0;
        int[][] supportMatrix = new int[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if (this.getSingleTile(row, column) == null) {
                    supportMatrix[row][column] = 0;
                } else {
                    supportMatrix[row][column] = 1;
                }
            }
        }
        int group = 1;

        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if (supportMatrix[row][column] == 1) {
                    group++;
                    assignGroupToBookshelfEqualTiles(supportMatrix, row, column, group, this.getSingleTile(row, column).getColor());
                }
            }
        }

        for (int g = 2; g <= group; g++) {
            int numberOfTilesInGroup = 0;
            for (int row = 0; row < this.numberOfRows; row++) {
                for (int column = 0; column < this.numberOfColumns; column++) {
                    if (supportMatrix[row][column] == g) {
                        numberOfTilesInGroup++;
                    }
                }
            }
            Optional<Integer> firstGoalToGetPoint = this.pointsForEachGroup.keySet().stream().findFirst();
            if (firstGoalToGetPoint.isPresent()) {

                //if the number of tiles is below the first goal available, you don't get points
                if (numberOfTilesInGroup < firstGoalToGetPoint.get()) {
                    continue;
                    //if the number of tiles is over the last goal available, you get points equal to the last goal points
                } else if (numberOfTilesInGroup > this.pointsForEachGroup.keySet().stream().reduce((first, second) -> second).get()) {
                    score += this.pointsForEachGroup.get(this.pointsForEachGroup.keySet().stream().reduce((first, second) -> second).get());
                } else {
                    score += this.pointsForEachGroup.get(numberOfTilesInGroup);
                }
            } else {
                throw new Exception("[RESOURCE:ERROR] Bookshelf points are not setted");
            }
        }
        return score;
    }

    /**
     * This method is used to assign the {@code Tile}'s group to the {@code Bookshelf}
     * that contains the correspondent pattern of tiles.
     *
     * @param supportMatrix the matrix used during the method intermediate values.
     * @param row the selected row.
     * @param column the selected column.
     * @param group the chosen group.
     * @param currentTileColor the color of the tiles in the current set.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Bookshelf
     */
    private void assignGroupToBookshelfEqualTiles(int[][] supportMatrix, int row, int column, int group, TileColor currentTileColor) {
        if ((supportMatrix[row][column] == 1) && currentTileColor.equals(this.getSingleTile(row, column).getColor())) {
            supportMatrix[row][column] = group;

            //up
            if (row != 0) {
                assignGroupToBookshelfEqualTiles(supportMatrix, row - 1, column, group, currentTileColor);
            }
            //left
            if (column != 0) {
                assignGroupToBookshelfEqualTiles(supportMatrix, row, column - 1, group, currentTileColor);
            }
            //down
            if (row != this.getNumberOfRows() - 1) {
                assignGroupToBookshelfEqualTiles(supportMatrix, row + 1, column, group, currentTileColor);
            }
            //right
            if (column != this.getNumberOfColumns() - 1) {
                assignGroupToBookshelfEqualTiles(supportMatrix, row, column + 1, group, currentTileColor);
            }
        }
    }
}
