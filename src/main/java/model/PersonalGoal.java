package model;

import model.*;
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
                if(b.getSingleTile(i,j).equals(this.pattern[i][j])) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
