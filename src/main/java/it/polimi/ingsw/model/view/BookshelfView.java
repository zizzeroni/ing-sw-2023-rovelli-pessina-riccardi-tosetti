package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BookshelfView implements Serializable {
    //private final Bookshelf bookshelfModel;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final String image;
    private final TileView[][] tiles;
    //private final Map<Integer, Integer> pointsForEachGroup;
    private final Map<Integer, Integer> pointsForEachGroup;

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
        return pointsForEachGroup;
    }

    public boolean isFull() {
        for (int column = 0; column < this.numberOfColumns; column++) {
            if (this.tiles[0][column] == null) {
                return false;
            }
        }
        return true;
    }

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

    public String getImage() {
        return this.image;
    }

    public TileView[][] getTiles() {
        return this.tiles;
    }

    public TileView getSingleTile(int row, int column) { // funzione estrazione singola Tile selezionata
        return this.tiles[row][column];
    }

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
                output = ((currentTile == null || currentTile.getColor() == null) ? output + "0 " : output + currentTile.getColor() + " ");
            }
            output += "] " + "\n";
        }
        return output.substring(0, output.length() - 1);
    }

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
