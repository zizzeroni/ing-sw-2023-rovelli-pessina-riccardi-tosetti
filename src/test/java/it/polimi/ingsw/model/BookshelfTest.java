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
        Tile[][] bookshelf = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        this.bookshelf = new Bookshelf(bookshelf);
        assertFalse(this.bookshelf.isRowFull(0));
        assertFalse(this.bookshelf.isRowFull(1));
        assertFalse(this.bookshelf.isRowFull(2));
        assertFalse(this.bookshelf.isRowFull(3));
        assertFalse(this.bookshelf.isRowFull(4));
    }

    @Test
    @DisplayName("Test isRowFull method with bookshelf null")
    public void isRowFullTestBookshelfFull() {
        Tile[][] bookshelf = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        this.bookshelf = new Bookshelf(bookshelf);
        assertTrue(this.bookshelf.isRowFull(0));
        assertTrue(this.bookshelf.isRowFull(1));
        assertTrue(this.bookshelf.isRowFull(2));
        assertTrue(this.bookshelf.isRowFull(3));
        assertTrue(this.bookshelf.isRowFull(4));
    }

    @Test
    @DisplayName("Test isColumnFull method with bookshelf null")
    public void isColumnFullTestBookshelfNull() {
        Tile[][] bookshelf = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        this.bookshelf = new Bookshelf(bookshelf);
        assertFalse(this.bookshelf.isColumnFull(0));
        assertFalse(this.bookshelf.isColumnFull(1));
        assertFalse(this.bookshelf.isColumnFull(2));
        assertFalse(this.bookshelf.isColumnFull(3));
        assertFalse(this.bookshelf.isColumnFull(4));
    }

    @Test
    @DisplayName("Test isColumnFull method with generic bookshelf")
    public void isColumnFullTestBookshelfFull() {
        Tile[][] bookshelf = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}
        };

        this.bookshelf = new Bookshelf(bookshelf);
        assertTrue(this.bookshelf.isColumnFull(0));
        assertTrue(this.bookshelf.isColumnFull(1));
        assertTrue(this.bookshelf.isColumnFull(2));
        assertTrue(this.bookshelf.isColumnFull(3));
        assertTrue(this.bookshelf.isColumnFull(4));
    }
}