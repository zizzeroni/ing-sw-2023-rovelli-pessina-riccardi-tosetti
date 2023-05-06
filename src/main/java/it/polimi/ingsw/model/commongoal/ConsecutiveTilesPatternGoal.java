package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.ConsecutiveTilesPatternGoalView;

public class ConsecutiveTilesPatternGoal extends CommonGoal {
    //contains the number of tiles that must be consecutive for making a point of the pattern goal
    private final int consecutiveTiles;
    //Constructors
    public ConsecutiveTilesPatternGoal() {
        super();
        this.consecutiveTiles = 0;
    }
    public ConsecutiveTilesPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int consecutiveTiles) {
        super(imageID, numberOfPatternRepetitionsRequired, type);
        this.consecutiveTiles = consecutiveTiles;
    }

    public ConsecutiveTilesPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID, int consecutiveTiles) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
        this.consecutiveTiles = consecutiveTiles;
    }
    /*
    Here we search the number of pattern repetition in the bookshelf of the player by declaring a support matrix of the same dimensions of the bookshelf,
    for every not null tile we assign the number 1 in the support matrix ( 0 for the nulls).
    Start from the first not null tile, we assign in the support matrix in the position of the tile the group 2
    then we search if the nearby tiles are of the same colour and if it is true we assign the same group of the first tile.

    In the second part we count the number of different groups when the counter of the tiles in a group is
    at least the minimum number of consecutive tiles of the pattern goal

    @params bookshelf contains the bookshelf of the player
    @return generalCounter contains the number of group that have at least the minimum number of consecutive tiles
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
    //Get method
    public int getConsecutiveTiles() {
        return this.consecutiveTiles;
    }
    /*
    @return an immutable copy of the common goal
    */
    @Override
    public CommonGoalView copyImmutable() {
        return new ConsecutiveTilesPatternGoalView(this);
    }
    /*
    Redefine the equals method
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof ConsecutiveTilesPatternGoal obj) {
            return this.consecutiveTiles == obj.getConsecutiveTiles()
                    && this.getNumberOfPatternRepetitionsRequired() == obj.getNumberOfPatternRepetitionsRequired()
                    && this.getType() == obj.getType();
        }
        return false;
    }
}
