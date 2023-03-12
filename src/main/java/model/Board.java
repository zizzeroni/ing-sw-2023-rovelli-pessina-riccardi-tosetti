package model;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;

public class Board {
    private String image;
    private int maxNumTiles;

    public Board() {
    }
    public Board(String image, int maxNumTiles) {
        this.image = image;
        this.maxNumTiles = maxNumTiles;
    }

    public void addTiles(ArrayList<Tile> tiles) {

    }
    public int needRefill() {
        throw new NotImplementedException();
    }
    public void removeTiles(ArrayList<Tile> tiles) {

    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public int getMaxNumTiles() {
        return maxNumTiles;
    }
    public void setMaxNumTiles(int maxNumTiles) {
        this.maxNumTiles = maxNumTiles;
    }
}
