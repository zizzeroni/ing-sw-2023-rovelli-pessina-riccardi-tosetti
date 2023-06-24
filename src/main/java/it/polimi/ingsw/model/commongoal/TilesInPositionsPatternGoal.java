package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.TilesInPositionsPatternGoalView;

public class TilesInPositionsPatternGoal extends CommonGoal {
    //matrix that contains 1 in positions where there must be same colour tiles, otherwise 0
    private final int[][] positions;

    public TilesInPositionsPatternGoal() {
        super();
        this.positions = new int[0][0];
    }

    public TilesInPositionsPatternGoal(int imageID, int patternRepetition, CheckType type, int[][] positions) {
        super(imageID, patternRepetition, type);
        this.positions = positions;
    }

    public TilesInPositionsPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID, int[][] positions) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
        this.positions = positions;
    }

    /*
    Count the number of 1 in the positions matrix
    @return number of 1
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

    /*
    Here we search the number of pattern repetition in the bookshelf of the player by declaring a support matrix of the same dimensions of the bookshelf,
    for every not null tile we assign the number 1 in the support matrix ( 0 for the nulls).
    Start from the first not null tile, we assign in the support matrix in the position of the tile the group 2
    then we search if the nearby tiles are of the same colour and if it is true we assign the same group of the first tile.

    In the second part we count the number of different groups that have at least 1 correspondence with the one's in the matrix positions

    @params bookshelf contains the bookshelf of the player
    @return generalCounter contains the number of different groups that have at least 1 correspondence with the one's in the matrix positions
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

    //method get
    public int[][] getPositions() {
        return this.positions;
    }

    /*
    @return an immutable copy of the common goal
    */
    @Override
    public CommonGoalView copyImmutable() {
        return new TilesInPositionsPatternGoalView(this);
    }

    /*
    Redefine the equals method
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


