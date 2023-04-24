package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;

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

    public PersonalGoal(int imageID, Tile[][] pattern) {
        super(imageID);
        this.pattern = pattern;
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
}