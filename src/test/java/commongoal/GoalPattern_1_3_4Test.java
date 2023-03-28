package commongoal;

import model.Bookshelf;
import model.commongoal.CheckType;
import model.commongoal.FiveXShapePatternGoal;
import model.commongoal.GoalPattern_1_3_4;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GoalPattern_1_3_4Test {
    private GoalPattern_1_3_4 cg;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        cg = null;
        b = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        int positions[][] = {{1,1,0,0,0},
                             {1,1,0,0,0},
                             {0,0,0,0,0},
                             {0,0,0,0,0},
                             {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions);
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
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnZero() {
        int positions[][] =
                {{1,1,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
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
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int positions[][] =
                {{1,1,1,1,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0}};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(0, cg.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a full of blue element bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingTheFourElementSquare_thenReturnOne() {
        int positions[][] = {{1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, cg.goalPattern(b));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a full of blue element bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnOne() {
        int positions[][] =
                {{1,1,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, cg.goalPattern(b));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a full of blue bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnOne() {
        int positions[][] =
                {{1,1,1,1,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0}};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, cg.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a full of null element bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        int positions[][] = {
                {1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b = new Bookshelf("", temp);

        assertEquals(0, cg.goalPattern(b));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a full of null element bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnZero() {
        int positions[][] =
                {{1,1,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b = new Bookshelf("", temp);

        assertEquals(0, cg.goalPattern(b));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a full of null bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int positions[][] =
                {{1,1,1,1,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0}};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b = new Bookshelf("", temp);

        assertEquals(0, cg.goalPattern(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a generic bookshelf matches zero time")
    public void GivenAMixedBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        int positions[][] = {{1,1,0,0,0},
                {1,1,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},
                {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
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
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAMixedBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnZero() {
        int positions[][] =
                {{1,1,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
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
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAMixedElementBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int positions[][] =
                {{1,1,1,1,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0}};
        cg = new GoalPattern_1_3_4(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(0, cg.goalPattern(b));
    }
}
