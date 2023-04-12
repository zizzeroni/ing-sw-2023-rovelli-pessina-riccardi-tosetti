package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;

public class DiagonalEqualPattern extends CommonGoal {
    private int[][] positions;

    public DiagonalEqualPattern(int[][] positions) {
        super();
        this.positions = positions;
    }

    public DiagonalEqualPattern(int imageID, int patternRepetition, CheckType type, int[][] positions) {
        super(imageID, patternRepetition, type);
        this.positions = positions;
    }

    public DiagonalEqualPattern(int imageID, int patternRepetition, CheckType type, int numberOfPlayers, int[][] positions) {
        super(imageID, patternRepetition, type, numberOfPlayers);
        this.positions = positions;
    }

    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        int[][] supportMatrix = new int[bookshelf.getNumberOfRows()][bookshelf.getNumberOfColumns()];
        int[][] alreadyChecked = new int[bookshelf.getNumberOfRows()][bookshelf.getNumberOfColumns()];

        for (int i = 0; i < bookshelf.getNumberOfRows(); i++) {
            for (int j = 0; j < bookshelf.getNumberOfColumns(); j++) {
                alreadyChecked[i][j] = 0;
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
                    assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, i, j, group, bookshelf.getSingleTile(i, j).getColor());
                }
            }
        }

        int repetitions = 0;
        int rotations = 0;

        do {
            for (int i = 0; i < supportMatrix.length - this.positions.length + 1; i++) {
                for (int j = 0; j < supportMatrix[0].length - this.positions[0].length + 1; j++) {
                    int checkGroup = 0;
                    boolean exit = false;
                    for (int k = 0; k < this.positions.length && !exit; k++) {
                        for (int h = 0; h < this.positions[0].length && !exit; h++) {
                            if (this.positions[k][h] == 1) {
                                if (checkGroup == 0) {
                                    checkGroup = supportMatrix[i + k][j + h];
                                }
                                if (supportMatrix[i + k][j + h] == 0 || supportMatrix[i + k][j + h] != checkGroup || alreadyChecked[i + k][j + h] == 1) {
                                    exit = true;
                                }
                            }
                        }
                    }
                    if (!exit) {
                        for (int k = 0; k < this.positions.length; k++) {
                            for (int h = 0; h < this.positions[0].length; h++) {
                                if (supportMatrix[i + k][j + h] == checkGroup) {
                                    alreadyChecked[i + k][j + h] = 1;
                                }
                            }
                        }

                        repetitions++;
                    }
                }
            }

            this.positions = rotateMatrix(this.positions);
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

    private int[][] rotateMatrix(int[][] positions) {
        int[][] rotatedMatrix = new int[positions[0].length][positions.length];
        for (int i = 0; i < positions.length; i++) {
            for (int j = 0; j < positions[0].length; i++) {
                if (positions[i][j] == 1) {
                    rotatedMatrix[j][i] = 1;
                }
            }
        }
        return rotatedMatrix;
    }

}
