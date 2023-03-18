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
    private PersonalGoal pg1;
    private PersonalGoal pg2;
    private PersonalGoal pg3;
    private PersonalGoal pg4;
    private PersonalGoal pg5;
    private PersonalGoal pg6;
    private PersonalGoal pg7;
    private PersonalGoal pg8;
    private PersonalGoal pg9;
    private PersonalGoal pg10;
    private PersonalGoal pg11;
    private PersonalGoal pg12;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        pg1 = null;
        pg2 = null;
        pg3 = null;
        pg4 = null;
        pg5 = null;
        pg6 = null;
        pg7 = null;
        pg8 = null;
        pg9 = null;
        pg10 = null;
        pg11 = null;
        pg12 = null;
        b = null;
    }

    @Test
    @DisplayName("Test personal goals on file JSON with generic bookshelf")
    public void GoalsGenericBookshelf() {
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {null, null, null, null, null},
                {null, null, new Tile(TileColor.CYAN), null, null}};
        pg1 = new PersonalGoal("",temp);
        /*Tile[][] temp2 = {
                {new Tile(TileColor.BLUE), null, new Tile(TileColor.PURPLE), null, null},
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        pg2 = new PersonalGoal("",temp2);
        */

        Tile[][] bs = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", bs);
        pg1.goalPattern(b);

        assertEquals(0,pg1.goalPattern(b));
    }
}
