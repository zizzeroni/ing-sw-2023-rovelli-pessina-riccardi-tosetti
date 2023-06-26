package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.DiagonalEqualPatternGoalView;

import java.util.ArrayList;
import java.util.List;

public class DiagonalEqualPattern extends CommonGoal {
    //matrix that contains 1 in positions where there must be same colour tiles, otherwise 0
    private List<List<Integer>> pattern;

    //Constructors
    public DiagonalEqualPattern(List<List<Integer>> pattern) {
        super();
        this.pattern = pattern;
    }

    public DiagonalEqualPattern(int id, int patternRepetition, CheckType type, List<List<Integer>> pattern) {
        super(id, patternRepetition, type);
        this.pattern = pattern;
    }

    public DiagonalEqualPattern(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, List<List<Integer>> pattern) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
        this.pattern = pattern;
    }

    public DiagonalEqualPattern(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles, List<List<Integer>> pattern) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
        this.pattern = pattern;
    }

    /*
    Here we search the number of pattern repetition in the bookshelf of the player by declaring a support matrix of the same dimensions of the bookshelf,
    for every not null tile we assign the number 1 in the support matrix ( 0 for the nulls) and an alreadyChecked matrix for checking if a tiles is already checked.
    Start from the first not null tile, we assign in the support matrix in the position of the tile the group 2
    then we search if the oblique nearby tiles are of the same colour and if it is true we assign the same group of the first tile.

    In the second part we count the number of different groups when the counter of the tiles in a group is
    at least the minimum number of consecutive tiles of the pattern goal

    @params bookshelf contains the bookshelf of the player
    @return generalCounter contains the number of group that have at least the minimum number of consecutive tiles
     */
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        int[][] supportMatrix = new int[bookshelf.getNumberOfRows()][bookshelf.getNumberOfColumns()];
        int[][] alreadyChecked = new int[bookshelf.getNumberOfRows()][bookshelf.getNumberOfColumns()];

        for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                alreadyChecked[row][column] = 0;
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
                    assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, row, column, group, bookshelf.getSingleTile(row, column).getColor());
                }
            }
        }

        int repetitions = 0;
        int rotations = 0;

        do {
            for (int row = 0; row < supportMatrix.length - this.pattern.size() + 1; row++) {
                for (int column = 0; column < supportMatrix[0].length - this.pattern.get(0).size() + 1; column++) {
                    int checkGroup = 0;
                    boolean exit = false;
                    for (int patternRow = 0; patternRow < this.pattern.size() && !exit; patternRow++) {
                        for (int patternColumn = 0; patternColumn < this.pattern.get(0).size() && !exit; patternColumn++) {
                            if (this.pattern.get(patternRow).get(patternColumn) == 1) {
                                if (checkGroup == 0) {
                                    checkGroup = supportMatrix[row + patternRow][column + patternColumn];
                                }
                                if (supportMatrix[row + patternRow][column + patternColumn] == 0 || supportMatrix[row + patternRow][column + patternColumn] != checkGroup || alreadyChecked[row + patternRow][column + patternColumn] == 1) {
                                    exit = true;
                                }
                            }
                        }
                    }
                    if (!exit) {
                        for (int patternRow = 0; patternRow < this.pattern.size(); patternRow++) {
                            for (int patternColumn = 0; patternColumn < this.pattern.get(0).size(); patternColumn++) {
                                if (this.pattern.get(patternRow).get(patternColumn) == 1 && supportMatrix[row + patternRow][column + patternColumn] == checkGroup) {
                                    alreadyChecked[row + patternRow][column + patternColumn] = 1;
                                }
                            }
                        }

                        repetitions++;
                    }
                }
            }

            this.pattern = rotateMatrix(this.pattern);
            rotations++;
        } while (rotations < 4);

        return repetitions;
    }

    private void assignGroupToDiagonalEqualTiles(Bookshelf bookshelf, int[][] supportMatrix, int row, int column, int group, TileColor currentTileColor) {

        if ((supportMatrix[row][column] == 1) && currentTileColor.equals(bookshelf.getSingleTile(row, column).getColor())) {
            supportMatrix[row][column] = group;

            //up left
            if (row != 0 && column != 0) {
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, row - 1, column - 1, group, currentTileColor);
            }
            //up right
            if (row != 0 && column != bookshelf.getNumberOfColumns() - 1) {
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, row - 1, column + 1, group, currentTileColor);
            }
            //down left
            if (row != bookshelf.getNumberOfRows() - 1 && column != 0) {
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, row + 1, column - 1, group, currentTileColor);
            }
            //down right
            if (row != bookshelf.getNumberOfRows() - 1 && column != bookshelf.getNumberOfColumns() - 1) {
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, row + 1, column + 1, group, currentTileColor);
            }
        }
    }

    /*
    in this method we rotate the matrix by starting from the first element and exchanging the row and the column
    @param the matrix that we need to rotate
    @return the matrix rotated
     */
    private List<List<Integer>> rotateMatrix(List<List<Integer>> matrixToRotate) {
        List<List<Integer>> rotatedMatrix = new ArrayList<>(matrixToRotate.get(0).size());
        for (int i = 0; i < matrixToRotate.get(0).size(); i++) {
            rotatedMatrix.add(new ArrayList<>(matrixToRotate.get(0).size()));
            for (int j = 0; j < matrixToRotate.size(); j++) {
                rotatedMatrix.get(i).add(0);
            }
        }
        for (int row = 0; row < matrixToRotate.size(); row++) {
            for (int column = 0; column < matrixToRotate.get(0).size(); column++) {
                if (matrixToRotate.get(row).get(column) == 1) {
                    rotatedMatrix.get(row).set(rotatedMatrix.size() - 1 - column,1);
                }
            }
        }
        return rotatedMatrix;


    }

    // get
    public List<List<Integer>> getPattern() {
        return this.pattern;
    }

    /*
    @return an immutable copy of the common goal
    */
    @Override
    public CommonGoalView copyImmutable() {
        return new DiagonalEqualPatternGoalView(this);
    }
}
