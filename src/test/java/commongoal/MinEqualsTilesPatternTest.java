package commongoal;

import model.Bookshelf;
import model.commongoal.CheckType;
import model.commongoal.Direction;
import model.commongoal.MinEqualsTilesPattern;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinEqualsTilesPatternTest {
    private Bookshelf b;
    private MinEqualsTilesPattern horizontal3NotEquals;
    private MinEqualsTilesPattern vertical3NotEquals;
    private MinEqualsTilesPattern horizontalAllDifferent;
    private MinEqualsTilesPattern verticalAllDifferent;


    @BeforeEach
    public void cleanGoal() {
        b=null;
        horizontalAllDifferent = null;
        horizontal3NotEquals = null;
        verticalAllDifferent = null;
        vertical3NotEquals = null;
    }

    @Test
    @DisplayName("Test with generic bookshelf")
    public void GenericBookshelf() {
        horizontal3NotEquals = new MinEqualsTilesPattern("", 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern("",3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, horizontal3NotEquals.goalPattern(b));
        assertEquals(1, vertical3NotEquals.goalPattern(b));
        assertEquals(0, horizontalAllDifferent.goalPattern(b));
        assertEquals(0, verticalAllDifferent.goalPattern(b));
    }
    @Test
    @DisplayName("Test with Rulebook's example bookshelf")
    public void RulebookBookshelf() {
        horizontal3NotEquals = new MinEqualsTilesPattern("", 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern("",3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        b=new Bookshelf("",temp);

        assertEquals(1, horizontal3NotEquals.goalPattern(b));
        assertEquals(0, vertical3NotEquals.goalPattern(b));
        assertEquals(0, horizontalAllDifferent.goalPattern(b));
        assertEquals(0, verticalAllDifferent.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with all items = null")
    public void NullBookshelf() {
        horizontal3NotEquals = new MinEqualsTilesPattern("", 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern("",3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b=new Bookshelf("",temp);

        assertEquals(0, horizontal3NotEquals.goalPattern(b));
        assertEquals(0, vertical3NotEquals.goalPattern(b));
        assertEquals(0, horizontalAllDifferent.goalPattern(b));
        assertEquals(0, verticalAllDifferent.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with all items equals to each other")
    public void EqualsBookshelf() {
        horizontal3NotEquals = new MinEqualsTilesPattern("", 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern("",3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(1, horizontal3NotEquals.goalPattern(b));
        assertEquals(1, vertical3NotEquals.goalPattern(b));
        assertEquals(0, horizontalAllDifferent.goalPattern(b));
        assertEquals(0, verticalAllDifferent.goalPattern(b));
    }

    @Test
    @DisplayName("Test with bookshelf with all items different to each other")
    public void AllDifferentBookshelf() {
        horizontal3NotEquals = new MinEqualsTilesPattern("", 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern("",3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        b=new Bookshelf("",temp);

        assertEquals(0, horizontal3NotEquals.goalPattern(b));
        assertEquals(0, vertical3NotEquals.goalPattern(b));
        assertEquals(3, horizontalAllDifferent.goalPattern(b));
        assertEquals(2, verticalAllDifferent.goalPattern(b));
    }

    @Test
    @DisplayName("Test with a bigger bookshelf (9x6) with all items equals to each other")
    public void BiggerBookshelf() {
        horizontal3NotEquals = new MinEqualsTilesPattern("", 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern("",3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern("",2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        class PersonalizedBookshelf extends Bookshelf {
            int numeroRighe=9;
            int numeroColonne=6;
            public PersonalizedBookshelf(String image, Tile[][] tiles) {
                super(image, tiles);
            }
            @Override
            public int getNumColumns() {
                return numeroColonne;
            }
            @Override
            public int getNumRows() {
                return numeroRighe;
            }
        }

        b=new PersonalizedBookshelf("", temp);
        assertEquals(2, horizontal3NotEquals.goalPattern(b));
        assertEquals(2, vertical3NotEquals.goalPattern(b));
        assertEquals(0, horizontalAllDifferent.goalPattern(b));
        assertEquals(0, verticalAllDifferent.goalPattern(b));
    }


}
