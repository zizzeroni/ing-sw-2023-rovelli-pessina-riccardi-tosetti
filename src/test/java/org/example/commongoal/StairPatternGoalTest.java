package org.example.commongoal;

import org.example.model.Bookshelf;
import org.example.model.commongoal.CheckType;
import org.example.model.commongoal.StairPatternGoal;
import org.example.model.tile.Tile;
import org.example.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StairPatternGoalTest {
    private StairPatternGoal stairPatternGoal;
    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        stairPatternGoal = null;
        bookshelf = null;
    }
    @Test
    @DisplayName("Test that the commonGoal with stair on columns in a bookshelf full of tiles matches zero times")
    public void GivenGenericBookshelf_whenSearchingTheStairPattern_thenReturnZero() {
        stairPatternGoal = new StairPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches zero times on a bookshelf completely filled with nulls")
    public void GivenFullOfNullsBookshelf_whenSearchingTheStairPattern_thenReturnZero() {
        stairPatternGoal = new StairPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf =new Bookshelf("",temp);

        assertEquals(0, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches one times on a bookshelf with one element on the first column, two on the second, three on the third" +
            " four on the fourth and five in the fifth")
    public void GivenStairBookshelfFromLeftToRightStartByOne_whenSearchingTheStairPattern_thenReturnOne() {
        stairPatternGoal = new StairPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, new Tile(TileColor.BLUE)},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf =new Bookshelf("",temp);

        assertEquals(1, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches one times on a bookshelf with five element on the first column, four on the second, three on the third" +
            " two on the fourth and one in the fifth")
    public void GivenStairBookshelfFromRightToLeftStartByOne_whenSearchingTheStairPattern_thenReturnOne() {
        stairPatternGoal = new StairPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), null, null, null, null,},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        bookshelf =new Bookshelf("",temp);

        assertEquals(1, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches one times on a bookshelf with zero element on the first column, one on the second, two on the third" +
            " three on the fourth and four in the fifth")
    public void GivenStairBookshelfFromLeftToRightStartByZero_whenSearchingTheStairPattern_thenReturnOne() {
        stairPatternGoal = new StairPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null,},
                {null, null, null, null, new Tile(TileColor.BLUE)},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf =new Bookshelf("",temp);

        assertEquals(1, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
}
