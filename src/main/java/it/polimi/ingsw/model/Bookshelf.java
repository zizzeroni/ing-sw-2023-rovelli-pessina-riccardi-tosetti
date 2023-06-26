package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.BookshelfListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 */
public class Bookshelf {
    private transient BookshelfListener listener;
    private final int numberOfColumns = 5;
    private final int numberOfRows = 6;
    private final Map<Integer, Integer> pointsForEachGroup = new LinkedHashMap<>(4) {{
        put(3, 2);
        put(4, 3);
        put(5, 5);
        put(6, 8);
    }};
    private String image;
    private Tile[][] tiles;


    /**
     * Used in order to register a {@code listener} in the
     * set of bookshelf's listeners.
     *
     * @param listener the listener object that is added.
     */
    public void registerListener(BookshelfListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    //Initialize the bookshelf of the single player
    public Bookshelf() {
        this.image = null;
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++)
            for (int column = 0; column < this.numberOfColumns; column++)
                this.tiles[row][column] = null;
    }

    public Bookshelf(Tile[][] tiles) {
        this.tiles = tiles;
    }

    /**
     * provides the number of points for all the different groups
     * identifiable in the Bookshelf for each player.
     *
     * @return pointsForEachGroup contains the provided score
     *  for each group of tiles in the corresponding map.
     */
    public Map<Integer, Integer> getPointsForEachGroup() {
        return pointsForEachGroup;
    }

    //If all the columns are empty return true, otherwise false
    public boolean isFull() {
        for (int column = 0; column < this.numberOfColumns; column++) {
            if (this.tiles[0][column] == null) {
                return false;
            }
        }
        return true;
    }

    /**
    * Adds the tile in the column where the player choose to insert it.
     *
    * @param tile is the type of tile selected from the player.
    * @param column is the column where the player want to insert the tile.
     */
    public void addTile(Tile tile, int column) {
        this.tiles[(this.numberOfRows - 1) - getNumberOfTilesInColumn(column)][column] = tile;
        /*if (this.listener != null) {
            this.listener.tileAddedToBookshelf(this);
        } else {
            System.err.println("Bookshelf's listener is NULL!");
        }*/
    }

    /**
     * Used to count the number of empty cells contemporary present in a {@code column}
     *
     * @param column is the column whose remaining places we want to know.
     * @return counter contains the number of the remaining places of the column.
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
     * @param image
     * @param tiles
     */
    public Bookshelf(String image, Tile[][] tiles) {
        this.image = image;
        this.tiles = tiles;
    }

    /**
     * @return
     */
    public String getImage() {
        return this.image;
    }

    /**
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
        this.listener.imageModified(this.image);
    }

    public Tile[][] getTiles() {
        return this.tiles;
    }

    public void setTiles(Tile[][] tiles) { // funzione estrazione singola Tile selezionata
        this.tiles = tiles;
    }

    public void setSingleTiles(Tile tile, int row, int column) {
        this.tiles[row][column] = tile;
    }

    public Tile getSingleTile(int row, int column) { // funzione estrazione singola Tile selezionata
        return this.tiles[row][column];
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    /**
     * Returns the number of tiles in the selected column.
     *
     * @param column is the column of which we want to know the number of tiles.
     * @return counter is the number of tiles in the column.
     */
    public int getNumberOfTilesInColumn(int column) {
        int counter = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] != null)
                counter++;
        }
        return counter;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }


    /**
     * evaluates if the current {@code row} is already been filled with {@code tiles}
     *
     * @param row is the row that we want to check.
     * @return  {@code true} if and only if the current column is not entirely
     *          occupied by tiles,
     *          {@code false} otherwise.
     */
    public boolean isRowFull(int row) {
        for (int column = 0; column < this.numberOfColumns; column++) {
            if (this.tiles[row][column] == null) {
                return false;
            }
        }
        return true;
    }

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

    /**
     * Controls if the current column still has free positions to
     * insert other tiles.
     *
     * @param column is the column that we want to check.
     * @return {@code true} if the column is full, otherwise {@code false}.
     */
    public boolean isColumnFull(int column) {
        for (int row = 0; row < this.numberOfRows; row++) {
            if (this.tiles[row][column] == null) {
                return false;
            }
        }
        return true;
    }


    /**
     * Evaluate the total points assigned to every group of tiles of the same color in the board.
     * The Bookshelf is split in the different color-groups and then by the number of element of each group
     * we assign the points to.
     * If the number of tiles is below the first goal available, the player doesn't get any points.
     * If the number of tiles is over the last goal available, the player get an amount of points
     * equals to those of the last goal.
     *
     * @return score is the number of points assigned to the group.
     *
     * @throws Exception print a message that signals that the total
     * of points for the current Bookshelf have not been set.
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
                throw new Exception("[RESOURCE:ERROR] Bookshelf points are not set");
            }
        }
        return score;
    }

    /**
     *
     * Used by the sorting method to determine if two tiles belong to the same group or a different one.
     *
     * @param supportMatrix is used to contain part of the intermediate values processed by the algorithm.
     * @param row the current row.
     * @param column the current column.
     * @param group the group individuated for insertion in the Bookshelf.
     * @param currentTileColor the tile color characterizing the current group.
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

    /**
     * Used to generate the matrix representing the Bookshelf
     *
     * @return the {@code substring} generated from the StringBuilder
     * instanced in the first part of the method, describing the current
     * state of tiles in rows/columns.
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("    ");
        for (int column = 0; column < this.numberOfColumns; column++) {
            output.append(column + 1).append(" ");
        }
        output.append("\n");
        for (int row = 0; row < this.numberOfRows; row++) {
            output.append(row + 1).append(" [ ");
            for (int column = 0; column < this.numberOfColumns; column++) {
                Tile currentTile = this.tiles[row][column];
                if (currentTile == null || currentTile.getColor() == null) {
                    output.append("0 ");
                } else {
                    output.append(currentTile.getColor()).append(" ");
                }
            }
            output.append("] \n");
        }
        return output.substring(0, output.length() - 1);
    }
}

