package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.ScoreTileView;
import it.polimi.ingsw.model.view.commongoal.MinEqualsTilesPatternGoalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MinEqualsTilesPatternTest {
    private Bookshelf bookshelf;
    private MinEqualsTilesPattern horizontalMaxThreeNotEquals;
    private MinEqualsTilesPattern verticalMaxThreeNotEquals;
    private MinEqualsTilesPattern horizontalAllDifferent;
    private MinEqualsTilesPattern verticalAllDifferent;


    @BeforeEach
    public void cleanGoal() {
        bookshelf = null;
        horizontalMaxThreeNotEquals = new MinEqualsTilesPattern(0, 4, CheckType.INDIFFERENT, 2, Direction.HORIZONTAL, 2);
        verticalMaxThreeNotEquals = new MinEqualsTilesPattern(0, 3, CheckType.INDIFFERENT, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile())), Direction.VERTICAL, 3);
        horizontalAllDifferent = new MinEqualsTilesPattern(0, 2, CheckType.DIFFERENT, Direction.HORIZONTAL, 0);
        verticalAllDifferent = new MinEqualsTilesPattern(0, 2, CheckType.DIFFERENT, Direction.VERTICAL, 0);
    }

    /**
     *
     */
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
        bookshelf = new Bookshelf(temp);

        assertEquals(5, horizontalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(4, verticalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
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
        bookshelf = new Bookshelf(temp);

        assertEquals(4, horizontalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(1, verticalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
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
        bookshelf = new Bookshelf(temp);

        assertEquals(0, horizontalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
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
        bookshelf = new Bookshelf(temp);

        assertEquals(6, horizontalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(5, verticalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively zero times, zero times, six times and five times on the given bookshelf")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyZeroZeroSixFive() {

        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, horizontalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(6, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(5, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the vertical and horizontal commonGoals, with the parameterized number of max tiles equals to each other, match respectively nine times, six times, zero times and zero times on the given bookshelf")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColorWith9x6Size_whenSearchingForRowsAndColumnsWithMaxThreeNotEqualsTilesAndRowsAndColumnsWithAllTileDifferentToEachOther_returnRespectivelyNineSixZeroZero() {

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
            final int numberOfRows = 9;
            final int numberOfColumns = 6;

            public PersonalizedBookshelf(Tile[][] tiles) {
                super(tiles);
            }

            @Override
            public int getNumberOfColumns() {
                return numberOfColumns;
            }

            @Override
            public int getNumberOfRows() {
                return numberOfRows;
            }
        }

        bookshelf = new PersonalizedBookshelf(temp);

        assertEquals(9, horizontalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(6, verticalMaxThreeNotEquals.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, horizontalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
        assertEquals(0, verticalAllDifferent.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that copyImmutable creates a view equal to the model")
    public void immutable_copy_is_equal_to_the_original_model() {
        MinEqualsTilesPattern minEqualsTilesPattern = new MinEqualsTilesPattern();
        minEqualsTilesPattern.setDirection(Direction.VERTICAL);
        minEqualsTilesPattern.setMaxEqualsTiles(3);

        MinEqualsTilesPatternGoalView copy = minEqualsTilesPattern.copyImmutable();

        List<ScoreTileView> copyScoreTilesAsViews = new ArrayList<>();

        minEqualsTilesPattern.getScoreTiles().forEach(scoreTile -> copyScoreTilesAsViews.add(new ScoreTileView(scoreTile)));

        for (int i = 0; i < copyScoreTilesAsViews.size(); i++) {
            assertEquals(minEqualsTilesPattern.getScoreTiles().get(i).getValue(), copyScoreTilesAsViews.get(i).getValue());
            assertEquals(minEqualsTilesPattern.getScoreTiles().get(i).getPlayerID(), copyScoreTilesAsViews.get(i).getPlayerID());
            assertEquals(minEqualsTilesPattern.getScoreTiles().get(i).getCommonGoalID(), copyScoreTilesAsViews.get(i).getCommonGoalID());
        }

        assertEquals(minEqualsTilesPattern.getDirection(), copy.getDirection());
        assertEquals(minEqualsTilesPattern.getMaxEqualsTiles(), copy.getMaxEqualsTiles());
        assertEquals(minEqualsTilesPattern.getNumberOfPatternRepetitionsRequired(), copy.getNumberOfPatternRepetitionsRequired());
        assertEquals(minEqualsTilesPattern.getType(), copy.getType());
        assertEquals(minEqualsTilesPattern.getId(), copy.getId());
    }
}
