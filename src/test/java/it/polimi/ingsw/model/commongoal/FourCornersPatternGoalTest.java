package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.ScoreTileView;
import it.polimi.ingsw.model.view.commongoal.FourCornersPatternGoalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FourCornersPatternGoalTest {
    private FourCornersPatternGoal pattern;
    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        pattern = new FourCornersPatternGoal();
        bookshelf = null;
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with four tiles equals to each others at the corners of the bookshelf matches zero times on the given bookshelf")
    public void givenGenericBookshelf_whenSearchingFourTilesAtTheBookshelfCorners_returnZero() {
        pattern = new FourCornersPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with four tiles equals to each others at the corners of the bookshelf matches zero times on the given bookshelf")
    public void givenRulebookBookshelf_whenSearchingFourTilesAtTheBookshelfCorners_returnZero() {
        pattern = new FourCornersPatternGoal(0, 1, CheckType.EQUALS);
        Tile[][] temp = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, pattern.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with four tiles equals to each others at the corners of the bookshelf matches zero times on an empty bookshelf")
    public void givenBookshelfFilledWithNulls_whenSearchingFourTilesAtTheBookshelfCorners_returnZero() {
        pattern = new FourCornersPatternGoal(0, 1, CheckType.EQUALS);
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
    @DisplayName("Test that the commonGoal with four tiles equals to each others at the corners of the bookshelf matches one time on the given bookshelf")
    public void givenBookshelfCompletelyFilledWithTilesOfTheSameColor_whenSearchingFourTilesAtTheBookshelfCorners_returnOne() {
        pattern = new FourCornersPatternGoal(0, 1, CheckType.EQUALS, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile())));
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
    @DisplayName("Test that the commonGoal with four tiles equals to each others at the corners of the bookshelf matches zero times on the given bookshelf")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenSearchingFourTilesAtTheBookshelfCorners_returnZero() {
        pattern = new FourCornersPatternGoal(0, 1, CheckType.EQUALS, 2);
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
    @DisplayName("Test that copyImmutable creates a view equal to the model")
    public void immutable_copy_is_equal_to_the_original_model() {
        pattern = new FourCornersPatternGoal(0, 1, CheckType.EQUALS);

        FourCornersPatternGoalView copy = this.pattern.copyImmutable();

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
        assertEquals("Four tiles of the same type in the four corners of the bookshelf. \n" +
                "[ \u001B[34mB\u001B[39m - - - \u001B[34mB\u001B[39m ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ - - - - - ] \n" +
                "[ \u001B[34mB\u001B[39m - - - \u001B[34mB\u001B[39m ] \n", copy.toString());
    }
}
