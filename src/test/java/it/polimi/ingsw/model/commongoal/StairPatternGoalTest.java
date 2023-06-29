package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.ScoreTileView;
import it.polimi.ingsw.model.view.commongoal.StairPatternGoalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StairPatternGoalTest {
    private StairPatternGoal stairPatternGoal;
    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        stairPatternGoal = null;
        bookshelf = null;
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with stair on columns in a bookshelf full of tiles matches zero times")
    public void GivenGenericBookshelf_whenSearchingTheStairPattern_thenReturnZero() {
        stairPatternGoal = new StairPatternGoal(0, 1, CheckType.EQUALS, 2);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches zero times on a bookshelf completely filled with nulls")
    public void GivenFullOfNullsBookshelf_whenSearchingTheStairPattern_thenReturnZero() {
        stairPatternGoal = new StairPatternGoal(0, 1, CheckType.EQUALS, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile())));
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches one times on a bookshelf with one element on the first column, two on the second, three on the third" +
            " four on the fourth and five in the fifth")
    public void GivenStairBookshelfFromLeftToRightStartByOne_whenSearchingTheStairPattern_thenReturnOne() {
        stairPatternGoal = new StairPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, new Tile(TileColor.BLUE)},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches one times on a bookshelf with five element on the first column, four on the second, three on the third" +
            " two on the fourth and one in the fifth")
    public void GivenStairBookshelfFromRightToLeftStartByOne_whenSearchingTheStairPattern_thenReturnOne() {
        stairPatternGoal = new StairPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), null, null, null, null,},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with stair on columns matches zero times on a bookshelf with zero element on the first column, one on the second, two on the third" +
            " three on the fourth and four in the fifth")
    public void GivenStairBookshelfFromLeftToRightStartByZero_whenSearchingTheStairPattern_thenReturnOne() {
        stairPatternGoal = new StairPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null,},
                {null, null, null, null, new Tile(TileColor.BLUE)},
                {null, null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, stairPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that copyImmutable creates a view equal to the model")
    public void immutable_copy_is_equal_to_the_original_model() {
        this.stairPatternGoal = new StairPatternGoal(0, 1, CheckType.EQUALS);

        StairPatternGoalView copy = this.stairPatternGoal.copyImmutable();

        List<ScoreTileView> copyScoreTilesAsViews = new ArrayList<>();

        this.stairPatternGoal.getScoreTiles().forEach(scoreTile -> copyScoreTilesAsViews.add(new ScoreTileView(scoreTile)));

        for (int i = 0; i < copyScoreTilesAsViews.size(); i++) {
            assertEquals(this.stairPatternGoal.getScoreTiles().get(i).getValue(), copyScoreTilesAsViews.get(i).getValue());
            assertEquals(this.stairPatternGoal.getScoreTiles().get(i).getPlayerID(), copyScoreTilesAsViews.get(i).getPlayerID());
            assertEquals(this.stairPatternGoal.getScoreTiles().get(i).getCommonGoalID(), copyScoreTilesAsViews.get(i).getCommonGoalID());
        }

        assertEquals(this.stairPatternGoal.getNumberOfPatternRepetitionsRequired(), copy.getNumberOfPatternRepetitionsRequired());
        assertEquals(this.stairPatternGoal.getType(), copy.getType());
        assertEquals(this.stairPatternGoal.getId(), copy.getId());
    }
}
