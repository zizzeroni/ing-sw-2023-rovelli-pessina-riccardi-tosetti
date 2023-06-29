package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

        bookshelf = new Bookshelf(bs);
        assertFalse(bookshelf.isRowFull(0)); // è necessario testarli tutti ?
        assertFalse(bookshelf.isRowFull(1));
        assertFalse(bookshelf.isRowFull(2));
        assertFalse(bookshelf.isRowFull(3));
        assertFalse(bookshelf.isRowFull(4));
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

        bookshelf = new Bookshelf(bs);
        assertTrue(bookshelf.isRowFull(0));
        assertTrue(bookshelf.isRowFull(1));
        assertTrue(bookshelf.isRowFull(2));
        assertTrue(bookshelf.isRowFull(3));
        assertTrue(bookshelf.isRowFull(4));
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

        bookshelf = new Bookshelf(bs);
        assertFalse(bookshelf.isColumnFull(0));
        assertFalse(bookshelf.isColumnFull(1));
        assertFalse(bookshelf.isColumnFull(2));
        assertFalse(bookshelf.isColumnFull(3));
        assertFalse(bookshelf.isColumnFull(4));
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

        bookshelf = new Bookshelf(bs);
        assertTrue(bookshelf.isColumnFull(0));
        assertTrue(bookshelf.isColumnFull(1));
        assertTrue(bookshelf.isColumnFull(2));
        assertTrue(bookshelf.isColumnFull(3));
        assertTrue(bookshelf.isColumnFull(4));
    }
}