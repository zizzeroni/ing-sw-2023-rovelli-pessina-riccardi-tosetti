package org.example.commongoal;

import org.example.model.Bookshelf;
import org.example.model.commongoal.CheckType;
import org.example.model.commongoal.Direction;
import org.example.model.commongoal.MinEqualsTilesPattern;
import org.example.model.tile.Tile;
import org.example.model.tile.TileColor;
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
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively one time, one time, zero times and zero times on the given bookshelf")
    public void givenGenericBookshelf_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyOneOneZeroZero() {
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
    }
    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively one time, zero times, zero times and zero times on the given bookshelf")
    public void givenRulebookBookshelf_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyOneZeroZeroZero() {
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        b=new Bookshelf("",temp);

        assertEquals(1, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively zero times, zero times, zero times and zero times on the given bookshelf")
    public void givenBookshelfFilledWithNulls_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyZeroZeroZeroZero() {
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b=new Bookshelf("",temp);

        assertEquals(0, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively one time, one time, zero times and zero times on the given bookshelf")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColor_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyOneOneZeroZero() {
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(1, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively zero times, zero times, three times and two times on the given bookshelf")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyZeroZeroThreeTwo() {
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        b=new Bookshelf("",temp);

        assertEquals(0, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(3, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(2, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively two times, two times, zero times and zero times on the given bookshelf")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColorWith9x6Size_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyTwoTwoZeroZero() {
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
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
            public int getNumberOfColumns() {
                return numeroColonne;
            }
            @Override
            public int getNumberOfRows() {
                return numeroRighe;
            }
        }

        b=new PersonalizedBookshelf("", temp);
        assertEquals(2, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(2, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(b));
    }


}
