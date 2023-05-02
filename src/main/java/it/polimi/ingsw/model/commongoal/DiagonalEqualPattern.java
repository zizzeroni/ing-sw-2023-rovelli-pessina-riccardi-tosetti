package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.ConsecutiveTilesPatternGoalView;
import it.polimi.ingsw.model.view.commongoal.DiagonalEqualPatternView;

public class DiagonalEqualPattern extends CommonGoal {
    private int[][] pattern;

    public DiagonalEqualPattern(int[][] pattern) {
        super();
        this.pattern = pattern;
    }

    public int[][] getPattern() {
        return pattern;
    }

    public DiagonalEqualPattern(int imageID, int patternRepetition, CheckType type, int[][] pattern) {
        super(imageID, patternRepetition, type);
        this.pattern = pattern;
    }

    public DiagonalEqualPattern(int imageID, int patternRepetition, CheckType type, int numberOfPlayers, int[][] pattern) {
        super(imageID, patternRepetition, type, numberOfPlayers);
        this.pattern = pattern;
    }

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
            for (int row = 0; row < supportMatrix.length - this.pattern.length + 1; row++) {
                for (int column = 0; column < supportMatrix[0].length - this.pattern[0].length + 1; column++) {
                    int checkGroup = 0;
                    boolean exit = false;
                    for (int patternRow = 0; patternRow < this.pattern.length && !exit; patternRow++) {
                        for (int patternColumn = 0; patternColumn < this.pattern[0].length && !exit; patternColumn++) {
                            if (this.pattern[patternRow][patternColumn] == 1) {
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
                        for (int patternRow = 0; patternRow < this.pattern.length; patternRow++) {
                            for (int patternColumn = 0; patternColumn < this.pattern[0].length; patternColumn++) {
                                if (supportMatrix[row + patternRow][column + patternColumn] == checkGroup) {
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

    private int[][] rotateMatrix(int[][] matrixToRotate) {
        int[][] rotatedMatrix = new int[matrixToRotate[0].length][matrixToRotate.length];
        for (int row = 0; row < matrixToRotate.length; row++) {
            for (int column = 0; column < matrixToRotate[0].length; row++) {
                if (matrixToRotate[row][column] == 1) {
                    rotatedMatrix[column][row] = 1;
                }
            }
        }
        return rotatedMatrix;
    }
    @Override
    public CommonGoalView copyImmutable() {
        return new DiagonalEqualPatternView(this);
    }
}
