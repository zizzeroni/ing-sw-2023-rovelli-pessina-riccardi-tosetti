package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.commongoal.DiagonalEqualPattern;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiagonalEqualPatternTest {
    private DiagonalEqualPattern pattern;
    private Bookshelf bookshelf;
    private int[][] positions;

    @BeforeEach
    public void cleanGoal() {
        pattern = null;
        bookshelf = null;
        positions = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheFiveXShape_thenReturnZero() {
        this.positions = new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X matches zero times on an empty bookshelf")
    public void GivenAFullOfNullBookshelf_whenSearchingTheFiveXShape_thenReturnZero() {
        this.positions = new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X matches four times on a bookshelf completely filled with same colour tiles")
    public void GivenFullOfBlueBookshelf_whenSearchingTheFiveXShape_thenReturnFour() {
        this.positions = new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(4, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X matches two times on a bookshelf with the first 3 column of inverted color")
    public void GivenABookshelfWithTheFirstThreeColumnOfInvertedColor_whenSearchingTheFiveXShape_thenReturnTwo() {
        this.positions = new int[][]{
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(2, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }


    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal in a bookshelf with five blue element on the second diagonal matches one time")
    public void GivenABookshelfWithFiveBlueElementOnTheDiagonal_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnOne() {
        this.positions = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal in a bookshelf whit the first two rows filled of null element matches zero")
    public void GivenABookshelfWithTheFirstTwoRowsNull_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnOne() {
        this.positions = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal matches zero times on a bookshelf completely filled with nulls")
    public void GivenFullOfNullsBookshelf_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnZero() {
        this.positions = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal matches two times on a bookshelf completely filled with same colour tiles")
    public void GivenFullOfBlueBookshelf_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnFour() {
        this.positions = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(2, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal in a bookshelf whit five purple element on the first diagonal and five blue element on " +
            "the second diagonal matches two time")
    public void GivenABookshelfWithTwoTimesFiveSameElementOnTheDiagonal_whenSearchingTheFiveDiagonalElementOfTheSameColour_thenReturnTwo() {
        this.positions = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {null, null, null, null, new Tile(TileColor.PURPLE)},
                {null, null, null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(2, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element on a diagonal matches zero time on a generic bookshelf")
    public void givenGenericBookshelf_whenSearchingTheFiveDiagonalElementOfTheSameColour_returnOne() {
        this.positions = new int[][]{
                {1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0},
                {0, 0, 0, 0, 1}
        };
        pattern = new DiagonalEqualPattern(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {null, null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
}
