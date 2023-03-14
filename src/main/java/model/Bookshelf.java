package model;

import java.util.ArrayList;

public class Bookshelf {
    private int numColumns;
    private int numRows;
    private ArrayList<Player> players; // eredità di player
    private ArrayList<Tile> shelfs; // bookshelf = composizione di tiles?
    public Bookshelf{
        //...
    }
    public boolean isFull(...){
        //...
    }
    public void addTile(Tile tile, int i){
       // ...
    }
    public int emptyCellsInColumn(int i){
       // ...
        return i;
    }
}
