package model.commongoal;

import model.Bookshelf;
import model.tile.TileColor;

public class DiagonalEqualPattern extends CommonGoal{
    private final int[][] positions;

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

        int[][] supportMatrix = new int[bookshelf.getNumRows()][bookshelf.getNumColumns()];
        int[][] alreadyChecked = new int[bookshelf.getNumRows()][bookshelf.getNumColumns()];

        for (int i = 0; i < bookshelf.getNumRows(); i++) {
            for (int j = 0; j < bookshelf.getNumColumns(); j++) {
                alreadyChecked[i][j] = 0;
                if (bookshelf.getSingleTile(i, j) == null) {
                    supportMatrix[i][j] = 0;
                } else {
                    supportMatrix[i][j] = 1;
                }
            }
        }

        int group = 1;

        for (int i = 0; i < bookshelf.getNumRows(); i++) {
            for (int j = 0; j < bookshelf.getNumColumns(); j++) {
                if (supportMatrix[i][j] == 1) {
                    group++;
                    assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, i, j, group, bookshelf.getSingleTile(i, j).getColor());
                }
            }
        }

        int repetitions = 0;

        for(int i = 0; i < supportMatrix.length - this.positions.length + 1; i++){
            for(int j = 0; j < supportMatrix[0].length - this.positions[0].length + 1; j++){
                int checkGroup = 0;
                boolean exit = false;
                for(int k = 0; k < this.positions.length && !exit; k++){
                    for(int h = 0; h < this.positions[0].length && !exit; h++){
                        if(this.positions[k][h] == 1) {
                            if(checkGroup == 0){
                                checkGroup = supportMatrix[i+k][j+h];
                            }
                            if(supportMatrix[i+k][j+h] == 0 || supportMatrix[i+k][j+h] != checkGroup || alreadyChecked[i+k][j+h] == 1){
                                exit = true;
                            }
                        }
                    }
                }
                if(!exit) {
                    for(int k = 0; k < this.positions.length; k++){
                        for(int h = 0; h < this.positions[0].length; h++){
                            if(supportMatrix[i+k][j+h] == checkGroup) {
                                alreadyChecked[i+k][j+h] = 1;
                            }
                        }
                    }

                    repetitions++;
                }
            }
        }

       return repetitions;
    }

    private void assignGroupToDiagonalEqualTiles(Bookshelf bookshelf, int[][] supportMatrix, int r, int c, int group, TileColor currentTileColor) {

        if ((supportMatrix[r][c] == 1) && currentTileColor.equals(bookshelf.getSingleTile(r,c).getColor())) {
            supportMatrix[r][c] = group;

            //up left
            if(r!=0 && c!=0){
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, r-1, c-1, group, currentTileColor);
            }
            //up right
            if(r != 0 && c!=bookshelf.getNumColumns()-1){
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, r-1, c+1, group, currentTileColor);
            }
            //down left
            if(r!=bookshelf.getNumRows()-1 && c!=0){
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, r+1, c-1, group, currentTileColor);
            }
            //down right
            if(r!=bookshelf.getNumRows()-1 && c!=bookshelf.getNumColumns()-1){
                assignGroupToDiagonalEqualTiles(bookshelf, supportMatrix, r+1, c+1, group, currentTileColor);
            }
        }
    }

}
