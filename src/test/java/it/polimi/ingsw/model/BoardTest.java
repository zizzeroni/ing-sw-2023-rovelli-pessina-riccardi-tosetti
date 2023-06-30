package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;

    @BeforeEach
    public void cleanBoard() {
        this.board = new Board(45, new Tile[][]{
                {new Tile(), new Tile(), new Tile(), null, null, new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), null, null, new Tile(), new Tile(), new Tile()},
        });
    }

    @Test
    @DisplayName("Test that the added tiles are actually added to the board in the right order")
    public void board_tiles_are_the_same_that_have_been_given_to_the_method_to_be_added() {

        List<Tile> tilesToAdd = new ArrayList<>();
        TileColor[][] emptyPositionsExpectedColor = new TileColor[this.board.getNumberOfRows()][this.board.getNumberOfColumns()];

        this.board.registerListener(null);

        this.board.setNumberOfUsableTiles(3);
        assertEquals(3, this.board.getNumberOfUsableTiles());

        for (int row = 0; row < this.board.getNumberOfRows(); row++) {
            for (int column = 0; column < this.board.getNumberOfColumns(); column++) {
                if (this.board.getSingleTile(row, column) == null) {
                    Tile tileToAdd = new Tile(TileColor.values()[(row + column) % 6]);
                    emptyPositionsExpectedColor[row][column] = tileToAdd.getColor();
                    tilesToAdd.add(tileToAdd);
                } else {
                    emptyPositionsExpectedColor[row][column] = null;
                }
            }
        }

        this.board.addTiles(tilesToAdd);

        for (int row = 0; row < this.board.getNumberOfRows(); row++) {
            for (int column = 0; column < this.board.getNumberOfColumns(); column++) {
                if (this.board.getSingleTile(row, column).getColor() != emptyPositionsExpectedColor[row][column]) {
                    fail("Board's tile's color should've been the same as the inserted one");
                }
            }
        }
    }

    @Test
    @DisplayName("Test that the number of the tiles to refill is correct on an empty board")
    public void number_of_tiles_to_refill_is_the_correct_one_with_empty_board() {
        assertEquals(45, this.board.numberOfTilesToRefill());
    }

    @Test
    @DisplayName("Test that the number of the tiles to refill is correct on a board with more than two adjacent tiles")
    public void number_of_tiles_to_refill_is_the_correct_one_when_two_or_more_cells_are_adjacent() {
        this.board = new Board(45, new Tile[][]{
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.GREEN), new Tile(TileColor.WHITE), new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
                {new Tile(), null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile()},
                {new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), new Tile(), new Tile(), new Tile()},
        });

        this.board.removeListener();

        assertEquals(0, this.board.numberOfTilesToRefill());
    }

    @Test
    @DisplayName("Test that the number of the tiles to refill is correct on a board with few non adjacent tiles")
    public void number_of_tiles_to_refill_is_the_correct_one_when_few_tiles_are_present_but_none_is_adjacent() {
        this.board.setTiles(new Tile[][]{
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), null, new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), null, new Tile(TileColor.PURPLE), null, null, null, null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null, new Tile(TileColor.PURPLE), null, null, new Tile(TileColor.PURPLE)},
                {null, null, null, null, null, null, new Tile(TileColor.PURPLE), null, new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), null, new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
        });

        assertEquals(38, this.board.numberOfTilesToRefill());
    }

    @Test
    @DisplayName("Test that existing tiles are actually removed from the board")
    public void existing_removed_tiles_are_set_to_null_in_the_board() {
        this.board.setTiles(new Tile[][]{
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), null, new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), null, new Tile(TileColor.PURPLE), null, null, null, null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null, new Tile(TileColor.PURPLE), null, null, new Tile(TileColor.PURPLE)},
                {null, null, null, null, null, null, new Tile(TileColor.PURPLE), null, new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), null, new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
        });

        List<Coordinates> coordinates = new ArrayList<>(Arrays.asList(new Coordinates(0, 4), new Coordinates(3, 2)));

        this.board.removeTiles(coordinates);

        assertNull(this.board.getSingleTile(0, 4));
        assertNull(this.board.getSingleTile(3, 2));
    }

    @Test
    @DisplayName("Test that removing tiles that have already been removed don't cause any error")
    public void removed_tiles_that_have_been_already_removed_do_not_cause_error() {
        this.board.setTiles(new Tile[][]{
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), null, new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), null, new Tile(TileColor.PURPLE), null, null, null, null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null, new Tile(TileColor.PURPLE), null, null, new Tile(TileColor.PURPLE)},
                {null, null, null, null, null, null, new Tile(TileColor.PURPLE), null, new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), null, new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
        });

        List<Coordinates> coordinates = new ArrayList<>(Arrays.asList(new Coordinates(1, 5), new Coordinates(2, 4)));

        this.board.removeTiles(coordinates);

        assertNull(this.board.getSingleTile(1, 5));
        assertNull(this.board.getSingleTile(2, 4));
    }

    @Test
    @DisplayName("Test that the given pattern is set correctly")
    public void given_pattern_is_set_correctly() {

        int[][] pattern = new int[][]{
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0}
        };

        this.board.setPattern(new JsonBoardPattern(3, pattern));

        for (int row = 0; row < this.board.getNumberOfRows(); row++) {
            for (int column = 0; column < this.board.getNumberOfColumns(); column++) {
                if (pattern[row][column] == 1) {
                    assertNull(this.board.getSingleTile(row, column));
                } else {
                    assertInstanceOf(Tile.class, this.board.getSingleTile(row, column));
                    assertNull(this.board.getSingleTile(row, column).getColor());
                }
            }
        }
    }

    @Test
    @DisplayName("Test that to string method generates the right string")
    public void to_string_generates_the_right_string() {

        this.board.setTiles(new Tile[][]{
                {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), null, new Tile(), new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), null, new Tile(TileColor.PURPLE), null, null, null, null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null, new Tile(TileColor.PURPLE), null, null, new Tile(TileColor.PURPLE)},
                {null, null, null, null, null, null, new Tile(TileColor.PURPLE), null, new Tile()},
                {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
                {new Tile(), new Tile(), new Tile(), new Tile(), null, new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
        });

        this.board.setSingleTile(0, 4, new Tile(TileColor.GREEN));

        assertEquals("    1 2 3 4 5 6 7 8 9 \n" +
                "1 [ 0 0 0 \u001B[35mP\u001B[39m \u001B[32mG\u001B[39m 0 0 0 0 ] \n" +
                "2 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "3 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "4 [ 0 0 \u001B[35mP\u001B[39m 0 0 0 0 0 0 ] \n" +
                "5 [ 0 0 0 \u001B[35mP\u001B[39m 0 \u001B[35mP\u001B[39m 0 0 \u001B[35mP\u001B[39m ] \n" +
                "6 [ 0 0 0 0 0 0 \u001B[35mP\u001B[39m 0 0 ] \n" +
                "7 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "8 [ 0 0 0 0 0 0 0 0 0 ] \n" +
                "9 [ 0 0 0 0 0 \u001B[35mP\u001B[39m 0 0 0 ] ", this.board.toString());

    }
}
