package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinEqualsTilesPatternTest {
    private Bookshelf bookshelf;
    private MinEqualsTilesPattern horizontal3NotEquals;
    private MinEqualsTilesPattern vertical3NotEquals;
    private MinEqualsTilesPattern horizontalAllDifferent;
    private MinEqualsTilesPattern verticalAllDifferent;


    @BeforeEach
    public void cleanGoal() {
        bookshelf = null;
        horizontal3NotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, Direction.HORIZONTAL, 2);
        vertical3NotEquals = new MinEqualsTilesPattern(0,3,CheckType.INDIFFERENT,Direction.VERTICAL,3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.HORIZONTAL,0);
        verticalAllDifferent = new MinEqualsTilesPattern(0,2,CheckType.DIFFERENT,Direction.VERTICAL,0);
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively one time, one time, zero times and zero times on the given bookshelf")
    public void givenGenericBookshelf_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyOneOneZeroZero() {

        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(5, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(5, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively one time, zero times, zero times and zero times on the given bookshelf")
    public void givenRulebookBookshelf_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyOneZeroZeroZero() {

        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(4, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(4, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively zero times, zero times, zero times and zero times on the given bookshelf")
    public void givenBookshelfFilledWithNulls_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyZeroZeroZeroZero() {

        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively one time, one time, zero times and zero times on the given bookshelf")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColor_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyOneOneZeroZero() {

        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(6, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(5, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively zero times, zero times, three times and two times on the given bookshelf")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyZeroZeroThreeTwo() {
        
        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(3, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(2, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively two times, two times, zero times and zero times on the given bookshelf")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColorWith9x6Size_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyTwoTwoZeroZero() {

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
            int numeroRighe = 9;
            int numeroColonne = 6;

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

        bookshelf = new PersonalizedBookshelf("", temp);
        assertEquals(2, horizontal3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(2, vertical3NotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }


}
