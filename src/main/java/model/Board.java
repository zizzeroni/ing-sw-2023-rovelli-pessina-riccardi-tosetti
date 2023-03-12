package model;

import model.tile.Tile;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {
    private String image;
    private int maxNumTiles;

    private Tile [][] tiles;

    public Board() {
        image = null;
        maxNumTiles = 0;
        tiles = new Tile[9][9];
        for(int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                tiles[i][j] = null;
    }
    public Board(int numPlayer){
        tiles = new Tile[9][9];
        switch (numPlayer) {
            case 2: maxNumTiles = 29;
                break;
            case 3: maxNumTiles = 37;
                break;
            case 4: maxNumTiles = 45;
                break;

        }
        switch(numPlayer) {
            case 4:
                tiles[0][4] = new Tile();
                tiles[1][5] = new Tile();
                tiles[3][1] = new Tile();
                tiles[4][0] = new Tile();
                tiles[4][8] = new Tile();
                tiles[5][7] = new Tile();
                tiles[7][3] = new Tile();
                tiles[8][4] = new Tile();
            case 3:
                tiles[0][3] = new Tile();
                tiles[2][2] = new Tile();
                tiles[2][6] = new Tile();
                tiles[5][0] = new Tile();
                tiles[6][2] = new Tile();
                tiles[6][6] = new Tile();
                tiles[8][5] = new Tile();
            case 2:
                tiles[1][3] = new Tile();
                tiles[1][4] = new Tile();
                tiles[2][3] = new Tile();
                tiles[2][4] = new Tile();
                tiles[2][5] = new Tile();
                tiles[3][2] = new Tile();
                tiles[3][3] = new Tile();
                tiles[3][4] = new Tile();
                tiles[3][5] = new Tile();
                tiles[3][6] = new Tile();
                tiles[4][1] = new Tile();
                tiles[4][2] = new Tile();
                tiles[4][3] = new Tile();
                tiles[4][4] = new Tile();
                tiles[4][5] = new Tile();
                tiles[4][6] = new Tile();
                tiles[4][7] = new Tile();
                tiles[5][2] = new Tile();
                tiles[5][3] = new Tile();
                tiles[5][4] = new Tile();
                tiles[5][5] = new Tile();
                tiles[5][6] = new Tile();
                tiles[6][3] = new Tile();
                tiles[6][4] = new Tile();
                tiles[6][5] = new Tile();
                tiles[7][4] = new Tile();
                tiles[7][5] = new Tile();
                break;
            default:
                break;
        }
    }
    public Board(String image, int maxNumTiles, Tile[][] tiles) {
        this.image = image;
        this.maxNumTiles = maxNumTiles;
        this.tiles = tiles;
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

    public Tile[][] getTiles() {
        return tiles;
    }
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
