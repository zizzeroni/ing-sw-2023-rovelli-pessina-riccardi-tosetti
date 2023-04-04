package model;

import model.tile.Tile;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int maxNumTiles;

    private final int numColumns = 9;

    private final int numRows = 9;

    private Tile [][] tiles;

    public Board() {
        this.maxNumTiles = 0;
        this.tiles = new Tile[this.numRows][this.numColumns];
        for(int i = 0; i < this.numRows; i++)
            for (int j = 0; j < this.numColumns; j++)
                this.tiles[i][j] = null;
    }
    public Board(int numPlayer){
        this.tiles = new Tile[this.numRows][this.numColumns];
        switch (numPlayer) {
            case 2: this.maxNumTiles = 29;
                break;
            case 3: this.maxNumTiles = 37;
                break;
            case 4: this.maxNumTiles = 45;
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
        this.maxNumTiles = maxNumTiles;
        this.tiles = tiles;
    }
// da integrare con json
    public void addTiles(List<Tile> tilesSet) {
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++) {
                if (this.tiles[i][j] == null) {
                    this.tiles[i][j] = tilesSet.get(i);
                }
            }
        }
    }
    public int needRefill() { //numero tiles che richiedono refill o indice tile che need refill ?

        int counter = 0;
        for (int i = 0; i < this.numRows; i++) {
            for (int j = 0; j < this.numColumns; j++){
                if(this.tiles[i][j] != null && (this.tiles[i][j+1] != null || this.tiles[i+1][j] != null)) {
                    return 0;
                }
                if(this.tiles[i][j] != null){
                    counter++;
                }
            }
        }
        return this.maxNumTiles - counter;
    }
    public void removeTiles(Tile[] tilesToRemove, int[] positions) {
        int i = 0;
        for (Tile tile : tilesToRemove) {
            this.tiles[positions[i]][positions[i+1]] = null;
            i += 2;
        }
    }
    public int getMaxNumTiles() {
        return this.maxNumTiles;
    }
    public void setMaxNumTiles(int maxNumTiles) {
        this.maxNumTiles = maxNumTiles;
    }
    public Tile[][] getTiles() {
        return this.tiles;
    }
    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }
}
