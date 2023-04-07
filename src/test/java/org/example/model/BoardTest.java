package org.example.model;

import org.example.model.tile.Tile;
import org.example.model.tile.TileColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Player p;
    private int image;
    private Board board;
    private List<Tile> tilesList;
    private Tile[][] tilesTemp;
    private List<Tile> tilesTemp2;
    private Tile[] tilesToRemove;
    private Tile[][] tiles;
    private int[] positions = {0,0, 0,4, 4,4, 5,1, 8,7};

/*
    @Test
    @DisplayName("Test")
    public void Board() {
        tiles = new Tile[][]{


        }
    }

    @Test
    @DisplayName("Test")
    public void addTilesTestAllNullBoard() {

        tiles = new Tile[][]{

                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}};

        Board board = {image, board.getMaxNumTiles(), tiles};

        List<Tile> tilesList = this.bag.subList(0, this.board.needRefill());


        board.addTiles();
    }

    @Test
    @DisplayName("Test")
    public void addTilesTestAllEqualsBoard() {
        tiles = new Tile[][]{

                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        Board board = {image, board.getMaxNumTiles(), tiles};


        p.selectTiles(board);
    }
*/
public <T> List<T> twoDArrayToList(T[][] twoDArray) { /* correggere */
    List<T> list = new ArrayList<T>();
    for (T[] array : twoDArray) {
        list.addAll(Arrays.asList(array));
    }
    return list;
}
    @Test
    @DisplayName("Test addTiles function for a generic board initialized")
    public void addTilesTestGenericBoard() {

        tiles = new Tile[][]{

                {new Tile(TileColor.BLUE), null, null, null, new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, new Tile(TileColor.GREEN), null, null, null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, new Tile(TileColor.CYAN), null}};

        tilesTemp2 = twoDArrayToList(tiles);

        tilesList.addAll(tilesTemp2);

        board.addTiles(tilesList);  //init board

        tilesTemp = board.getTiles();

        /*if(tilesTemp[0][0].getColor()!=TileColor.BLUE){
            fail();
        }
        if(tilesTemp[0][0].getColor()!=TileColor.WHITE){
            fail();
        }
        if(tilesTemp[0][0].getColor()!=TileColor.GREEN){
            fail();
        }
        if(tilesTemp[0][0].getColor()!=TileColor.PURPLE){
            fail();
        }
        if(tilesTemp[0][0].getColor()!=TileColor.CYAN){
            fail();
        } quale dei due modi, nessuno dei due ??*/

        assertEquals(TileColor.BLUE, tilesTemp[0][0].getColor());
        assertEquals(TileColor.WHITE, tilesTemp[0][4].getColor());
        assertEquals(TileColor.GREEN, tilesTemp[4][4].getColor());
        assertEquals(TileColor.PURPLE, tilesTemp[5][1].getColor());
        assertEquals(TileColor.CYAN, tilesTemp[8][7].getColor());

    }

    @Test
    @DisplayName("Test removeTiles function for a generic board initialized")
    public void removeTilesTestGenericBoard() {

        tiles = new Tile[][]{

                {new Tile(TileColor.BLUE), null, null, null, new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, new Tile(TileColor.GREEN), null, null, null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, new Tile(TileColor.CYAN), null}};

        tilesTemp2 = twoDArrayToList(tiles);

        tilesList.addAll(tilesTemp2);

        board.addTiles(tilesList);  //init board, init array ??

        //tilesToRemove = manca init

        board.removeTiles(tilesToRemove, positions);

        assertEquals(null, tilesTemp[0][0]);
        assertEquals(null, tilesTemp[0][4]);
        assertEquals(null, tilesTemp[4][4]);
        assertEquals(null, tilesTemp[5][1]);
        assertEquals(null, tilesTemp[8][7]);

        }

    @Test
    @DisplayName("Test needRefill function for a generic board initialized")
    public void needRefillTestGenericBoard() {

        tiles = new Tile[][]{

                {new Tile(TileColor.BLUE), null, null, null, new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, new Tile(TileColor.GREEN), null, null, null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, new Tile(TileColor.CYAN), null}};

        tilesTemp2 = twoDArrayToList(tiles);

        tilesList.addAll(tilesTemp2);

        board.addTiles(tilesList);  //init board, init array ?? Element[] array = {new Element(1), new Element(2), new Element(3)};

        assertEquals(76,board.numberOfTilesToRefill()); // num tile che richiede refill 81 - 5 ?

    }
}






















