package it.polimi.ingsw.commongoal;

import it.polimi.ingsw.model.commongoal.CheckType;
import it.polimi.ingsw.model.commongoal.EightShapelessPatternGoal;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.Bookshelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EightShapelessPatternGoalTest {
    private EightShapelessPatternGoal cg;

    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        cg = null;
        b = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on a generic bookshelf")
    public void givenGenericBookshelf_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        cg = new EightShapelessPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b = new Bookshelf("", temp);

        assertEquals(1, cg.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on the rulebook's bookshelf")
    public void givenRulebookBookshelf_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        cg = new EightShapelessPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        b=new Bookshelf("",temp);

        assertEquals(1, cg.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches zero times on a bookshelf completely filled with nulls")
    public void givenBookshelfFilledWithNulls_whenSearchingEightTilesEqualsToEachOther_returnZero() {
        cg = new EightShapelessPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        b=new Bookshelf("",temp);

        assertEquals(0, cg.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on a bookshelf completely filled with tiles fo the same color")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColor_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        cg = new EightShapelessPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(1, cg.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches zero times on a bookshelf completely filled with groups of tiles each consisting of a single tile")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingEightTilesEqualsToEachOther_returnZero() {
        cg = new EightShapelessPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        b=new Bookshelf("",temp);

        assertEquals(0, cg.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches three times on a bookshelf containing three different groups of eight or more tiles equals to each others")
    public void givenBookshelfWithThreeDifferentGroupsOfEightOrMoreTilesEqualsToEachOther_whenSearchingEightTilesEqualsToEachOther_returnThree() {
        cg = new EightShapelessPatternGoal(0,1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)}};
        b=new Bookshelf("",temp);

        assertEquals(3, cg.numberOfPatternRepetitionInBookshelf(b));
    }
}
