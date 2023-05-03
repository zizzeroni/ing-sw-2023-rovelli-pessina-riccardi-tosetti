package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.ConsecutiveTilesPatternGoalView;

public class ConsecutiveTilesPatternGoal extends CommonGoal {
    private final int consecutiveTiles;

    public ConsecutiveTilesPatternGoal() {
        super();
        this.consecutiveTiles = 0;
    }

    public int getConsecutiveTiles() {
        return consecutiveTiles;
    }

    public ConsecutiveTilesPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int consecutiveTiles) {
        super(imageID, numberOfPatternRepetitionsRequired, type);
        this.consecutiveTiles = consecutiveTiles;
    }

    public ConsecutiveTilesPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID, int consecutiveTiles) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
        this.consecutiveTiles = consecutiveTiles;
    }

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
        int groupCounter = 0;
        int generalCounter = 0;
        for (int g = 2; g <= group; g++) {
            for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
                for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                    if (supportMatrix[row][column] == g) {
                        groupCounter++;
                    }
                }
            }
            if (groupCounter >= this.consecutiveTiles) {
                generalCounter++;
            }
            groupCounter = 0;
        }
        return generalCounter;
    }

    private void searchGroup(Bookshelf bookshelf, int[][] supportMatrix, int row, int column, int group, TileColor currentTileColor) {
        if ((supportMatrix[row][column] == 1) && currentTileColor.equals(bookshelf.getSingleTile(row, column).getColor())) {
            supportMatrix[row][column] = group;

            //up
            if (row != 0) {
                searchGroup(bookshelf, supportMatrix, row - 1, column, group, currentTileColor);
            }
            //left
            if (column != 0) {
                searchGroup(bookshelf, supportMatrix, row, column - 1, group, currentTileColor);
            }
            //down
            if (row != bookshelf.getNumberOfRows() - 1) {
                searchGroup(bookshelf, supportMatrix, row + 1, column, group, currentTileColor);
            }
            //right
            if (column != bookshelf.getNumberOfColumns() - 1) {
                searchGroup(bookshelf, supportMatrix, row, column + 1, group, currentTileColor);
            }
        }
    }
    @Override
    public CommonGoalView copyImmutable() {
        return new ConsecutiveTilesPatternGoalView(this);
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof ConsecutiveTilesPatternGoal){
            ConsecutiveTilesPatternGoal obj = (ConsecutiveTilesPatternGoal) o;
            if(consecutiveTiles==obj.getConsecutiveTiles()
                    && this.getNumberOfPatternRepetitionsRequired() == obj.getNumberOfPatternRepetitionsRequired()
                    && this.getType() == obj.getType()){
                return true;
            }
        }
        return false;
    }
}
