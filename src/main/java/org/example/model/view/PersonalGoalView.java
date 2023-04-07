package org.example.model.view;

import org.example.model.PersonalGoal;

public class PersonalGoalView {
    private final PersonalGoal personalGoalModel;

    public PersonalGoalView(PersonalGoal personalGoalModel) {
        this.personalGoalModel = personalGoalModel;
    }

    public int getNumColumns() {
        return this.personalGoalModel.getNumColumns();
    }

    public int getNumRows() {
        return this.personalGoalModel.getNumRows();
    }

    public TileView[][] getPattern() {
        TileView[][] temp = new TileView[this.personalGoalModel.getNumRows()][this.personalGoalModel.getNumColumns()];
        for(int r=0;r<this.personalGoalModel.getNumRows();r++) {
            for(int c=0;c<this.personalGoalModel.getNumColumns();c++) {
                temp[r][c]=new TileView(this.personalGoalModel.getSingleTile(r,c));
            }
        }
        return temp;
    }

    public TileView getSingleTile(int x, int y) {
        return new TileView(this.personalGoalModel.getSingleTile(x,y));
    }


}
