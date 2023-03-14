package model;

import model.tile.Tile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Bookshelf {
    private int numColumns;
    private int numRows;
    private ArrayList<Player> players; // eredità di player
    private ArrayList<Tile> shelfs; // bookshelf = composizione di tiles?

    public boolean isFull(){
        throw new NotImplementedException();
    }
    public void addTile(Tile tile, int i){
       // ...
    }
    public int emptyCellsInColumn(int i){
        throw new NotImplementedException();
    }
}
