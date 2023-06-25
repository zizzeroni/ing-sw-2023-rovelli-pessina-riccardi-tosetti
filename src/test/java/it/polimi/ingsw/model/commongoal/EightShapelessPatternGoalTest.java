package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EightShapelessPatternGoalTest {
    private EightShapelessPatternGoal pattern;

    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        pattern = null;
        bookshelf = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on a generic bookshelf")
    public void givenGenericBookshelf_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on the rulebook's bookshelf")
    public void givenRulebookBookshelf_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches zero times on an empty bookshelf")
    public void givenBookshelfFilledWithNulls_whenSearchingEightTilesEqualsToEachOther_returnZero() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);
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
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on a bookshelf completely filled with tiles fo the same color")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColor_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches zero times on a bookshelf completely filled with groups of tiles each consisting of a single tile")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingEightTilesEqualsToEachOther_returnZero() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches three times on a bookshelf containing three different groups of eight or more tiles equals to each others")
    public void givenBookshelfWithThreeDifferentGroupsOfEightOrMoreTilesEqualsToEachOther_whenSearchingEightTilesEqualsToEachOther_returnThree() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(3, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
}
