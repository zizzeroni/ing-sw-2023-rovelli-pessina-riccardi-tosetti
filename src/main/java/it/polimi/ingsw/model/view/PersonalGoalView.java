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
        TileView[][] temp = new TileView[this.personalGoalModel.getNumberOfRows()][this.personalGoalModel.getNumberOfColumns()];
        for (int r = 0; r < this.personalGoalModel.getNumberOfRows(); r++) {
            for (int c = 0; c < this.personalGoalModel.getNumberOfColumns(); c++) {
                temp[r][c] = new TileView(this.personalGoalModel.getSingleTile(r, c));
            }
        }
        return temp;
    }

    public TileView getSingleTile(int x, int y) {
        return new TileView(this.personalGoalModel.getSingleTile(x, y));
    }


}
