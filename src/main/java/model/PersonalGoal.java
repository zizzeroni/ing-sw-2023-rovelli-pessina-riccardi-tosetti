package model;

import model.*;
import model.tile.*;

public class PersonalGoal extends Card {
    private final int numColumns = 5;
    private final int numRows = 6;
    private Tile[][] pattern;


    public PersonalGoal() {
        super();
        pattern = new Tile[numRows][numColumns];
        for(int i=0;i<numRows;i++) {
            for(int j=0;j<numColumns;j++) {
                pattern[i][j]=null;
            }
        }
    }

    public PersonalGoal(String image, Tile[][] pattern) {
        super(image);
        this.pattern = pattern;
    }

    @Override
    public int goalPattern(Bookshelf b) {
        int counter=0;
        for(int i=0;i<numRows;i++) {
            for(int j=0;j<numColumns;j++) {
                if(b.getTile(i,j).equals(pattern[i][j])) {
                    counter++;
                }
            }
        }
        return counter;
    }
}
