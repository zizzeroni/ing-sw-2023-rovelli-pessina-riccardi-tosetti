package commongoal;

import model.Bookshelf;
import model.commongoal.CheckType;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FiveXShapePatternGoalTest {
    private FiveXShapePatternGoal cg;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        cg = null;
        b = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheFiveXShape_thenReturnZero() {
        cg = new FiveXShapePatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(0, cg.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X matches zero times on a bookshelf completely filled with nulls")
    public void GivenAFullOfNullBookshelf_whenSearchingTheFiveXShape_thenReturnZero() {
        cg = new FiveXShapePatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b=new Bookshelf("",temp);

        assertEquals(0, cg.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X matches twelve times on a bookshelf completely filled with same colour tiles")
    public void GivenFullOfBlueBookshelf_whenSearchingTheFiveXShape_thenReturnTwelve() {
        cg = new FiveXShapePatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(12, cg.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with five element making an X matches four times on a bookshelf with the first 3 column of inverted color")
    public void GivenABookshelfWithTheFirstThreeColumnOfInvertedColor_whenSearchingTheFiveXShape_thenReturnFour() {
        cg = new FiveXShapePatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        b=new Bookshelf("",temp);

        assertEquals(4, cg.goalPattern(b));
    }
}
