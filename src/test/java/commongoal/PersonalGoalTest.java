package commongoal;

import model.Bookshelf;
import model.PersonalGoal;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalGoalTest {
    private Tile[][] pg1;
    private Tile[][] pg2;
    private Tile[][] pg3;
    private Tile[][] pg4;
    private Tile[][] pg5;
    private Tile[][] pg6;
    private Tile[][] pg7;
    private Tile[][] pg8;
    private Tile[][] pg9;
    private Tile[][] pg10;
    private Tile[][] pg11;
    private Tile[][] pg12;
    private PersonalGoal obj1;
    private PersonalGoal obj2;
    private PersonalGoal obj3;
    private PersonalGoal obj4;
    private PersonalGoal obj5;
    private PersonalGoal obj6;
    private PersonalGoal obj7;
    private PersonalGoal obj8;
    private PersonalGoal obj9;
    private PersonalGoal obj10;
    private PersonalGoal obj11;
    private PersonalGoal obj12;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        pg1 = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}};

        pg2 = new Tile[][]{
                {null, null, null, null, null},
                {null, new Tile(TileColor.CYAN), null, null, null},
                {null, null, null, null, null},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.WHITE), null, null},
                {null, null, null, null, new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.GREEN), null}};
        pg3 = new Tile[][]{
                {null, null, new Tile(TileColor.YELLOW), null, null},
                {null, null, null, null, null},
                {null, null, new Tile(TileColor.GREEN), null, null},
                {null, null, null, null, new Tile(TileColor.WHITE)},
                {null, new Tile(TileColor.CYAN), null, null, new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), null, null, null, null}};
        pg4 = new Tile[][]{
                {new Tile(TileColor.PURPLE), null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {null, null, null, null, null},
                {null, null, new Tile(TileColor.CYAN), null, null}};
        pg5 = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.YELLOW)},
                {null, null, null, null, null},
                {new Tile(TileColor.CYAN), null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null},
                {null, new Tile(TileColor.WHITE), new Tile(TileColor.GREEN), null, null},
                {null, null, null, null, null}};
        pg6 = new Tile[][]{
                {null, null, new Tile(TileColor.WHITE), null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null},
                {null, null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, new Tile(TileColor.CYAN), null},
                {null, null, null, null, new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), null, null, null, null}};
        pg7 = new Tile[][]{
                {null, null, null, null, null},
                {null, new Tile(TileColor.GREEN), null, null, null},
                {null, null, new Tile(TileColor.CYAN), null, null},
                {new Tile(TileColor.PURPLE), null, null, null, null},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, null, null, new Tile(TileColor.YELLOW), null}};
        pg8 = new Tile[][]{
                {null, null, null, null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.GREEN), null, new Tile(TileColor.YELLOW), null, null},
                {null, null, null, null, new Tile(TileColor.WHITE)},
                {null, null, null, new Tile(TileColor.CYAN), null},
                {null, null, null, null, new Tile(TileColor.BLUE)}};
        pg9 = new Tile[][]{
                {new Tile(TileColor.GREEN), null, null, null, null},
                {null, null, null, new Tile(TileColor.BLUE), null},
                {null, new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.CYAN), null, null, null, null},
                {null, null, null, null, new Tile(TileColor.YELLOW)},
                {null, null, new Tile(TileColor.WHITE), null, null}};
        pg10 = new Tile[][]{
                {null, null, new Tile(TileColor.CYAN), null, new Tile(TileColor.GREEN)},
                {null, null, null, null, null},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, null, null, null, null},
                {null, new Tile(TileColor.YELLOW), null, new Tile(TileColor.BLUE), null},
                {new Tile(TileColor.PURPLE), null, null, null, null}};
        pg11 = new Tile[][]{
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), null, null, new Tile(TileColor.YELLOW), null},
                {null, null, new Tile(TileColor.PURPLE), null, null},
                {null, new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {null, null, null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null}};
        pg12 = new Tile[][]{
                {null, null, new Tile(TileColor.PURPLE), null, null},
                {null, new Tile(TileColor.WHITE), null, null, null},
                {new Tile(TileColor.YELLOW), null, null, null, null},
                {null, null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.CYAN), null}};
        b = null;
        obj1 = new PersonalGoal("",pg1);
        obj2 = new PersonalGoal("",pg2);
        obj3 = new PersonalGoal("",pg3);
        obj4 = new PersonalGoal("",pg4);
        obj5 = new PersonalGoal("",pg5);
        obj6 = new PersonalGoal("",pg6);
        obj7 = new PersonalGoal("",pg7);
        obj8 = new PersonalGoal("",pg8);
        obj9 = new PersonalGoal("",pg9);
        obj10 = new PersonalGoal("",pg10);
        obj11 = new PersonalGoal("",pg11);
        obj12 = new PersonalGoal("",pg12);
    }

    @Test
    @DisplayName("Test personal goals on file JSON with generic bookshelf")
    public void GoalsGenericBookshelf() {
        Tile[][] bs = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
        assertEquals(1,obj1.goalPattern(b));
        assertEquals(1,obj2.goalPattern(b));
        assertEquals(1,obj3.goalPattern(b));
        assertEquals(0,obj4.goalPattern(b));
        assertEquals(1,obj5.goalPattern(b));
        assertEquals(1,obj6.goalPattern(b));
        assertEquals(1,obj7.goalPattern(b));
        assertEquals(1,obj8.goalPattern(b));
        assertEquals(1,obj9.goalPattern(b));
        assertEquals(1,obj10.goalPattern(b));
        assertEquals(1,obj11.goalPattern(b));
        assertEquals(1,obj12.goalPattern(b));
    }

    @Test
    @DisplayName("Test personal goals on file JSON with Rulebook's example bookshelf")
    public void GoalsRulebookBookshelf() {
        Tile[][] bs = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};

        b = new Bookshelf("", bs);
        assertEquals(0,obj1.goalPattern(b));

        assertEquals(1,obj2.goalPattern(b));

        assertEquals(2,obj3.goalPattern(b));

        assertEquals(3,obj4.goalPattern(b));

        assertEquals(1,obj5.goalPattern(b));

        assertEquals(1,obj6.goalPattern(b));

        assertEquals(0,obj7.goalPattern(b));

        assertEquals(2,obj8.goalPattern(b));

        assertEquals(0,obj9.goalPattern(b));

        assertEquals(0,obj10.goalPattern(b));

        assertEquals(1,obj11.goalPattern(b));

        assertEquals(1,obj12.goalPattern(b));
    }

    @Test
    @DisplayName("Test personal goals on file JSON with bookshelf with all items = null")
    public void GoalsNullBookshelf() {
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);
        assertEquals(0,obj1.goalPattern(b));

        assertEquals(0,obj2.goalPattern(b));

        assertEquals(0,obj3.goalPattern(b));

        assertEquals(0,obj4.goalPattern(b));

        assertEquals(0,obj5.goalPattern(b));

        assertEquals(0,obj6.goalPattern(b));

        assertEquals(0,obj7.goalPattern(b));

        assertEquals(0,obj8.goalPattern(b));

        assertEquals(0,obj9.goalPattern(b));

        assertEquals(0,obj10.goalPattern(b));

        assertEquals(0,obj11.goalPattern(b));

        assertEquals(0,obj12.goalPattern(b));
    }
    @Test
    @DisplayName("Test personal goals on file JSON with bookshelf with all items equals to each other")
    public void GoalsEqualsBookshelf() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
        assertEquals(1,obj1.goalPattern(b));

        assertEquals(1,obj2.goalPattern(b));

        assertEquals(1,obj3.goalPattern(b));

        assertEquals(1,obj4.goalPattern(b));

        assertEquals(1,obj5.goalPattern(b));

        assertEquals(1,obj6.goalPattern(b));

        assertEquals(0,obj7.goalPattern(b));

        assertEquals(1,obj8.goalPattern(b));

        assertEquals(1,obj9.goalPattern(b));

        assertEquals(1,obj10.goalPattern(b));

        assertEquals(1,obj11.goalPattern(b));

        assertEquals(1,obj12.goalPattern(b));
    }
    @Test
    @DisplayName("Test personal goals on file JSON with bookshelf with all items different to each other")
    public void GoalsAllDifferentBookshelf() {
        Tile[][] bs = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};

        b = new Bookshelf("", bs);
        assertEquals(3,obj1.goalPattern(b));

        assertEquals(1,obj2.goalPattern(b));

        assertEquals(1,obj3.goalPattern(b));

        assertEquals(0,obj4.goalPattern(b));

        assertEquals(1,obj5.goalPattern(b));

        assertEquals(1,obj6.goalPattern(b));

        assertEquals(1,obj7.goalPattern(b));

        assertEquals(0,obj8.goalPattern(b));

        assertEquals(2,obj9.goalPattern(b));

        assertEquals(0,obj10.goalPattern(b));

        assertEquals(0,obj11.goalPattern(b));

        assertEquals(2,obj12.goalPattern(b));
    }
}
