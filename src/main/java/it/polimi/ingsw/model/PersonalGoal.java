package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.utils.OptionsValues;

public class PersonalGoal extends Card {
    private final int numberOfColumns = 5;
    private final int numberOfRows = 6;
    private Tile[][] pattern;

    public PersonalGoal() {
        super();
        this.pattern = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                this.pattern[row][column] = null;
            }
        }
    }

    public PersonalGoal(int id, Tile[][] pattern) {
        super(id);
        this.pattern = pattern;
    }

    public PersonalGoal(PersonalGoal personalGoal) {
        this.pattern = personalGoal.getPattern();
        this.setId(personalGoal.getId());
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    public Tile[][] getPattern() {
        return this.pattern;
    }

    public void setPattern(Tile[][] pattern) {
        this.pattern = pattern;
    }

    public Tile getSingleTile(int row, int column) {
        return this.pattern[row][column];
    }

    @Override
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
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

    public int score(Bookshelf bookshelf) {
        switch (this.numberOfPatternRepetitionInBookshelf(bookshelf)) {
            case 0 -> {
                return OptionsValues.PERSONAL_GOAL_ZERO_TILE_SCORE;
            }
            case 1 -> {
                return OptionsValues.PERSONAL_GOAL_ONE_TILE_SCORE;
            }
            case 2 -> {
                return OptionsValues.PERSONAL_GOAL_TWO_TILE_SCORE;
            }
            case 3 -> {
                return OptionsValues.PERSONAL_GOAL_THREE_TILE_SCORE;
            }
            case 4 -> {
                return OptionsValues.PERSONAL_GOAL_FOUR_TILE_SCORE;
            }
            case 5 -> {
                return OptionsValues.PERSONAL_GOAL_FIVE_TILE_SCORE;
            }
            default -> {
                return OptionsValues.PERSONAL_GOAL_SIX_TILE_SCORE;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < this.numberOfRows; row++) {
            output.append("[ ");
            for (int column = 0; column < this.numberOfColumns; column++) {
                Tile currentTile = this.pattern[row][column];
                if (currentTile == null || currentTile.getColor() == null) {
                    output.append("0 ");
                } else {
                    output.append(currentTile.getColor()).append(" ");
                }
            }
            output.append("]\n");
        }
        return output.substring(0, output.length() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PersonalGoal))
            return false;

        return (this.getId() == ((PersonalGoal) obj).getId());
    }
}