package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.PersonalGoal;

import java.io.Serializable;

public class PersonalGoalView implements Serializable {
    //private final PersonalGoal personalGoalModel;
    private final int numberOfColumns;
    private final int numberOfRows;
    private final TileView[][] pattern;

    public PersonalGoalView(PersonalGoal personalGoalModel) {
        this.numberOfColumns = personalGoalModel.getNumberOfColumns();
        this.numberOfRows=personalGoalModel.getNumberOfRows();
        this.pattern = new TileView[personalGoalModel.getNumberOfRows()][personalGoalModel.getNumberOfColumns()];
        for(int row=0;row<personalGoalModel.getNumberOfRows();row++) {
            for(int column=0;column<personalGoalModel.getNumberOfColumns();column++) {
                this.pattern[row][column] = (personalGoalModel.getSingleTile(row,column)!=null ? new TileView(personalGoalModel.getSingleTile(row,column)) : null);
            }
        }
    }

    public int getNumColumns() {
        return this.numberOfColumns;
    }

    public int getNumRows() {
        return this.numberOfRows;
    }

    public TileView[][] getPattern() {
        return this.pattern;
    }

    public TileView getSingleTile(int row, int column) {
        return this.pattern[row][column];
    }

    @Override
    public String toString() {
        return "To be implemented";
    }

    public int score(BookshelfView bookshelf) {
        switch (this.numberOfPatternRepetitionInBookshelf(bookshelf)) {
            case 0 -> {
                return 0;
            }
            case 1 -> {
                return 1;
            }
            case 2 -> {
                return 2;
            }
            case 3 -> {
                return 4;
            }
            case 4 -> {
                return 6;
            }
            case 5 -> {
                return 9;
            }
            case 6 -> {
                return 12;
            }
            default -> {
                return 12;
            }
        }
    }

    private int numberOfPatternRepetitionInBookshelf(BookshelfView bookshelf) {
        int counter = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if (this.pattern[row][column] != null && bookshelf.getSingleTile(row, column) != null && bookshelf.getSingleTile(row, column).getColor().equals(this.pattern[row][column].getColor())) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
