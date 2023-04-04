package commongoal;

import model.Bookshelf;
import model.commongoal.CheckType;
import model.commongoal.FiveDiagonalPatternGoal;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiveDiagonalPatternGoalTest {
    private FiveDiagonalPatternGoal fd;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        fd = null;
        b = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal in a bookshelf whit five blue element on the first diagonal matches one time")
    public void GivenABookshelfWithFiveBlueElementOnTheDiagonal_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnOne() {
        fd = new FiveDiagonalPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, fd.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal in a bookshelf whit the first two rows filled of null element matches zero")
    public void GivenABookshelfWithTheFirstTwoRowsNull_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnOne() {
        fd = new FiveDiagonalPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        b=new Bookshelf("",temp);

        assertEquals(0, fd.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal matches zero times on a bookshelf completely filled with nulls")
    public void GivenFullOfNullsBookshelf_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnZero() {
        fd = new FiveDiagonalPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b=new Bookshelf("",temp);

        assertEquals(0, fd.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal matches four times on a bookshelf completely filled with same colour tiles")
    public void GivenFullOfBlueBookshelf_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnFour() {
        fd = new FiveDiagonalPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(4, fd.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal in a bookshelf whit five purple element on the first diagonal and five blue element on " +
            "the second diagonal matches two time")
    public void GivenABookshelfWithTwoTimesFiveSameElementOnTheDiagonal_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnTwo() {
        fd = new FiveDiagonalPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, new Tile(TileColor.PURPLE)},
                {null, null, null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        b=new Bookshelf("",temp);

        assertEquals(2, fd.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal matches zero time on a generic bookshelf")
    public void givenGenericBookshelf_whenSearchingTheFiveDiagonalElementOfTheSameColour_returnOne() {
        fd = new FiveDiagonalPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {null, null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        b=new Bookshelf("",temp);

        assertEquals(0, fd.goalPattern(b));
    }
}
