package model;

import model.*;
import model.commongoal.FourCornersPatternGoal;
import model.tile.*;

public class PersonalGoal extends Card {
    private final int numColumns = 5;
    private final int numRows = 6;
    private Tile[][] pattern;


    public PersonalGoal() {
        super();
        this.pattern = new Tile[this.numRows][this.numColumns];
        for(int i=0;i<this.numRows;i++) {
            for(int j=0;j<this.numColumns;j++) {
                this.pattern[i][j]=null;
            }
        }
    }

    public PersonalGoal(String image, Tile[][] pattern) {
        super(image);
        this.pattern = pattern;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getNumRows() {
        return numRows;
    }

    public Tile[][] getPattern() {
        return pattern;
    }

    public void setPattern(Tile[][] pattern) {
        this.pattern = pattern;
    }

    @Override
    public int goalPattern(Bookshelf b) {
        int counter=0;
        for(int i=0;i<this.numRows;i++) {
            for(int j=0;j<this.numColumns;j++) {
                if(this.pattern[i][j] != null && b.getSingleTile(i, j) != null && b.getSingleTile(i, j).getColor().equals(this.pattern[i][j].getColor())) {
                    counter++;
                }
            }
        }
        return counter;
    }

    public int score(Bookshelf b){
        switch (this.goalPattern(b)){
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
}
