package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {

    private Bookshelf b;

    @Test
    @DisplayName("Test isRowFull method with bookshelf null")
    public void isRowFullTestBookshelfNull() {
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);
        assertEquals(false, b.isRowFull(0)); // è necessario testarli tutti ?
        assertEquals(false, b.isRowFull(1));
        assertEquals(false, b.isRowFull(2));
        assertEquals(false, b.isRowFull(3));
        assertEquals(false, b.isRowFull(4));
    }
    @Test
    @DisplayName("Test isRowFull method with bookshelf null")
    public void isRowFullTestBookshelfFull() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
        assertEquals(true, b.isRowFull(0));
        assertEquals(true, b.isRowFull(1));
        assertEquals(true, b.isRowFull(2));
        assertEquals(true, b.isRowFull(3));
        assertEquals(true, b.isRowFull(4));
    }
    @Test
    @DisplayName("Test isColumnFull method with bookshelf null")
    public void isColumnFullTestBookshelfNull() {
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);
        assertEquals(false, b.isColumnFull(0));
        assertEquals(false, b.isColumnFull(1));
        assertEquals(false, b.isColumnFull(2));
        assertEquals(false, b.isColumnFull(3));
        assertEquals(false, b.isColumnFull(4));
    }
    @Test
    @DisplayName("Test isColumnFull method with generic bookshelf")
    public void isColumnFullTestBookshelfFull() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
        assertEquals(true, b.isColumnFull(0));
        assertEquals(true, b.isColumnFull(1));
        assertEquals(true, b.isColumnFull(2));
        assertEquals(true, b.isColumnFull(3));
        assertEquals(true, b.isColumnFull(4));
    }
}