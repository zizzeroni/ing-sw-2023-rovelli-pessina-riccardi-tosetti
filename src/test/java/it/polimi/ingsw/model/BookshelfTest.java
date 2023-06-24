package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookshelfTest {

    private Bookshelf bookshelf;

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

        bookshelf = new Bookshelf("", bs);
        assertEquals(false, bookshelf.isRowFull(0)); // è necessario testarli tutti ?
        assertEquals(false, bookshelf.isRowFull(1));
        assertEquals(false, bookshelf.isRowFull(2));
        assertEquals(false, bookshelf.isRowFull(3));
        assertEquals(false, bookshelf.isRowFull(4));
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

        bookshelf = new Bookshelf("", bs);
        assertEquals(true, bookshelf.isRowFull(0));
        assertEquals(true, bookshelf.isRowFull(1));
        assertEquals(true, bookshelf.isRowFull(2));
        assertEquals(true, bookshelf.isRowFull(3));
        assertEquals(true, bookshelf.isRowFull(4));
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

        bookshelf = new Bookshelf("", bs);
        assertEquals(false, bookshelf.isColumnFull(0));
        assertEquals(false, bookshelf.isColumnFull(1));
        assertEquals(false, bookshelf.isColumnFull(2));
        assertEquals(false, bookshelf.isColumnFull(3));
        assertEquals(false, bookshelf.isColumnFull(4));
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

        bookshelf = new Bookshelf("", bs);
        assertEquals(true, bookshelf.isColumnFull(0));
        assertEquals(true, bookshelf.isColumnFull(1));
        assertEquals(true, bookshelf.isColumnFull(2));
        assertEquals(true, bookshelf.isColumnFull(3));
        assertEquals(true, bookshelf.isColumnFull(4));
    }
}