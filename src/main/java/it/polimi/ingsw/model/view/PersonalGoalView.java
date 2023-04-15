package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.PersonalGoal;

public class PersonalGoalView {
    private final PersonalGoal personalGoalModel;

    public PersonalGoalView(PersonalGoal personalGoalModel) {
        this.personalGoalModel = personalGoalModel;
    }

    public int getNumColumns() {
        return this.personalGoalModel.getNumberOfColumns();
    }

    public int getNumRows() {
        return this.personalGoalModel.getNumberOfRows();
    }

    public TileView[][] getPattern() {
        TileView[][] tileViews = new TileView[this.personalGoalModel.getNumberOfRows()][this.personalGoalModel.getNumberOfColumns()];
        for (int row = 0; row < this.personalGoalModel.getNumberOfRows(); row++) {
            for (int column = 0; column < this.personalGoalModel.getNumberOfColumns(); column++) {
                tileViews[row][column] = new TileView(this.personalGoalModel.getSingleTile(row, column));
            }
        }
        return tileViews;
    }

    public TileView getSingleTile(int row, int column) {
        return new TileView(this.personalGoalModel.getSingleTile(row, column));
    }

    @Override
    public String toString() {
        return this.personalGoalModel.toString();
    }
}
