package it.polimi.ingsw.model;

import it.polimi.ingsw.model.listeners.BookshelfListener;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Optional;

public class Bookshelf {
    private BookshelfListener listener;
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

    public void registerListener(BookshelfListener listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    //REMINDER: For testing purposes only
    public Bookshelf(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Bookshelf() {
        this.image = null;
        this.tiles = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++)
            for (int column = 0; column < this.numberOfColumns; column++)
                this.tiles[row][column] = null;
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

    public void addTile(Tile tile, int column) {
        this.tiles[(this.numberOfRows - 1) - getNumberOfTilesInColumn(column)][column] = tile;
        if (this.listener != null) {
            this.listener.tileAddedToBookshelf(this);
        }
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

    public Bookshelf(String image, Tile[][] tiles) {
        this.image = image;
        this.tiles = tiles;
    }

    public String getImage() {
        return this.image;
    }

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
        int numberOfTilesInGroup = 0;
        for (int g = 2; g <= group; g++) {
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

