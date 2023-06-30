package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.BookshelfView;
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
        this.bookshelf.registerListener(null);
        this.bookshelf.removeListener();

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

        assertEquals("    1 2 3 4 5 \n" +
                "1 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "2 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "3 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "4 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "5 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "6 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] ", this.bookshelf.toString());

        assertTrue(this.bookshelf.isColumnFull(0));
        assertTrue(this.bookshelf.isColumnFull(1));
        assertTrue(this.bookshelf.isColumnFull(2));
        assertTrue(this.bookshelf.isColumnFull(3));
        assertTrue(this.bookshelf.isColumnFull(4));
    }

    @Test
    @DisplayName("Test that max number of empty cells in bookshelf is always less or equal than the size of the bookshelf")
    public void max_number_of_empty_cells_is_always_less_or_equal_total_size() {
        Tile[][] bookshelf = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}
        };

        this.bookshelf = new Bookshelf();
        assertEquals(6, this.bookshelf.getMaxNumberOfCellsFreeInBookshelf());

        this.bookshelf.setTiles(bookshelf);
        assertEquals(0, this.bookshelf.getMaxNumberOfCellsFreeInBookshelf());

        this.bookshelf.setSingleTiles(null, 0, 0);
        assertEquals(1, this.bookshelf.getMaxNumberOfCellsFreeInBookshelf());
    }


    @Test
    @DisplayName("Test view version of the bookshelf")
    public void test_view_version_of_the_bookshelf() {
        Tile[][] bookshelf = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}
        };

        this.bookshelf = new Bookshelf(bookshelf);

        BookshelfView view = new BookshelfView(this.bookshelf);

        for (int i = 3; i <= 6; i++) {
            assertEquals(view.getPointsForEachGroup().get(i), this.bookshelf.getPointsForEachGroup().get(i));
        }
        assertTrue(view.isFull());
        assertEquals(0, view.getMaxNumberOfCellsFreeInBookshelf());

        for (int column = 0; column < view.getNumberOfColumns(); column++) {
            assertEquals(0, view.getNumberOfEmptyCellsInColumn(column));
            assertEquals(6, view.getNumberOfTilesInColumn(column));
            assertTrue(view.isColumnFull(column));

            for (int row = 0; row < view.getNumberOfRows(); row++) {
                assertEquals(this.bookshelf.getSingleTile(row, column).getId(), view.getSingleTile(row, column).getId());
                assertEquals(this.bookshelf.getSingleTile(row, column).getColor(), view.getSingleTile(row, column).getColor());
                assertTrue(view.isRowFull(row));
            }
        }

        assertEquals("    1 2 3 4 5 \n" +
                "1 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "2 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "3 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "4 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "5 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] \n" +
                "6 [ \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m \u001B[34mB\u001B[39m ] ", view.toString());
        try {
            assertEquals(8, view.score());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}