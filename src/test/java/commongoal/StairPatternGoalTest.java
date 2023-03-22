package commongoal;

import model.Bookshelf;
import model.commongoal.CheckType;
import model.commongoal.FiveXShapePatternGoal;
import model.commongoal.StairPatternGoal;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StairPatternGoalTest {
    private StairPatternGoal sp;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        sp = null;
        b = null;
    }

    @Test
    @DisplayName("Test with generic bookshelf")
    public void GenericBookshelf() {
        sp = new StairPatternGoal("",1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(0, sp.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with all items = null")
    public void NullBookshelf() {
        sp = new StairPatternGoal("",1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b=new Bookshelf("",temp);

        assertEquals(0, sp.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with the stair start from 1 to 5")
    public void FromOneToFive() {
        sp = new StairPatternGoal("",1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, new Tile(TileColor.BLUE)},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(1, sp.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with the stair start from 5 to 1")
    public void FromFiveToOne() {
        sp = new StairPatternGoal("",1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), null, null, null, null,},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        b=new Bookshelf("",temp);

        assertEquals(1, sp.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with the stair start from 0 to 4")
    public void FromZeroToFour() {
        sp = new StairPatternGoal("",1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null,},
                {null, null, null, null, new Tile(TileColor.BLUE)},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(1, sp.goalPattern(b));
    }
}
