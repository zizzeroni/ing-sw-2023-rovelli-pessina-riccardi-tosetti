package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.ScoreTileView;
import it.polimi.ingsw.model.view.commongoal.EightShapelessPatternGoalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EightShapelessPatternGoalTest {
    private EightShapelessPatternGoal pattern;

    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        pattern = new EightShapelessPatternGoal();
        bookshelf = null;
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on a generic bookshelf")
    public void givenGenericBookshelf_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        this.pattern.setNumberOfPatternRepetitionsRequired(1);
        this.pattern.setType(CheckType.EQUALS);

        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
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
        bookshelf = new Bookshelf(temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
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
        bookshelf = new Bookshelf(temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches one time on a bookshelf completely filled with tiles fo the same color")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColor_whenSearchingEightTilesEqualsToEachOther_returnOne() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS, 1);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches zero times on a bookshelf completely filled with groups of tiles each consisting of a single tile")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingEightTilesEqualsToEachOther_returnZero() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile())));
        Tile[][] temp = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with eight tiles equals to each others matches three times on a bookshelf containing three different groups of eight or more tiles equals to each others")
    public void givenBookshelfWithThreeDifferentGroupsOfEightOrMoreTilesEqualsToEachOther_whenSearchingEightTilesEqualsToEachOther_returnThree() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS, 2);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(3, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that copyImmutable creates a view equal to the model")
    public void immutable_copy_is_equal_to_the_original_model() {
        pattern = new EightShapelessPatternGoal(0, 1, CheckType.EQUALS);

        EightShapelessPatternGoalView copy = this.pattern.copyImmutable();

        List<ScoreTileView> copyScoreTilesAsViews = new ArrayList<>();

        this.pattern.getScoreTiles().forEach(scoreTile -> copyScoreTilesAsViews.add(new ScoreTileView(scoreTile)));

        for (int i = 0; i < copyScoreTilesAsViews.size(); i++) {
            assertEquals(this.pattern.getScoreTiles().get(i).getValue(), copyScoreTilesAsViews.get(i).getValue());
            assertEquals(this.pattern.getScoreTiles().get(i).getPlayerID(), copyScoreTilesAsViews.get(i).getPlayerID());
            assertEquals(this.pattern.getScoreTiles().get(i).getCommonGoalID(), copyScoreTilesAsViews.get(i).getCommonGoalID());
        }

        assertEquals(this.pattern.getNumberOfPatternRepetitionsRequired(), copy.getNumberOfPatternRepetitionsRequired());
        assertEquals(this.pattern.getType(), copy.getType());
        assertEquals(this.pattern.getId(), copy.getId());
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that two personal goal are not the same")
    public void different_personal_goals_are_not_the_same() {
        this.pattern = new EightShapelessPatternGoal(1, 1, CheckType.EQUALS);
        FourCornersPatternGoal comparedCommonGoal = new FourCornersPatternGoal();

        assertNotEquals(this.pattern, comparedCommonGoal);
        assertNotEquals(comparedCommonGoal, this.pattern);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that a common goal is not equal to an object of another class")
    public void different_classes_are_not_equal_to_a_common_goal() {
        assertNotEquals(this.pattern, this.bookshelf);
        assertNotEquals(this.bookshelf, this.pattern);
    }
}
