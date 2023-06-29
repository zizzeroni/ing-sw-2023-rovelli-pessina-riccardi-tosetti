package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.ScoreTileView;
import it.polimi.ingsw.model.view.commongoal.ConsecutiveTilesPatternGoalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsecutiveTilesPatternGoalTest {
    private ConsecutiveTilesPatternGoal consecutiveTilesPatternGoal;
    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal();
        bookshelf = null;
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a generic bookshelf matches three time")
    public void GivenAGenericBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnThree() {
        int consecutive = 2;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(0, 1, CheckType.EQUALS, consecutive);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(3, consecutiveTilesPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 4;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(0, 1, CheckType.EQUALS, 2, consecutive);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, consecutiveTilesPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a bookshelf filled with elements of the same type matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnOne() {
        int consecutive = 2;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(0, 1, CheckType.EQUALS, consecutive);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, consecutiveTilesPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a bookshelf filled with elements of the same type matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnOne() {
        int consecutive = 4;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(0, 1, CheckType.EQUALS, consecutive);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, consecutiveTilesPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in an empty bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 2;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(0, 1, CheckType.EQUALS, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile(1, 1, 1))), consecutive);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, consecutiveTilesPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in an empty bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 4;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(0, 1, CheckType.EQUALS, consecutive);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, consecutiveTilesPatternGoal.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that copyImmutable creates a view equal to the model")
    public void immutable_copy_is_equal_to_the_original_model() {
        int consecutive = 4;
        consecutiveTilesPatternGoal = new ConsecutiveTilesPatternGoal(3, 1, CheckType.EQUALS, consecutive);

        ConsecutiveTilesPatternGoalView copy = this.consecutiveTilesPatternGoal.copyImmutable();

        List<ScoreTileView> copyScoreTilesAsViews = new ArrayList<>();

        this.consecutiveTilesPatternGoal.getScoreTiles().forEach(scoreTile -> copyScoreTilesAsViews.add(new ScoreTileView(scoreTile)));

        for (int i = 0; i < copyScoreTilesAsViews.size(); i++) {
            assertEquals(this.consecutiveTilesPatternGoal.getScoreTiles().get(i).getValue(), copyScoreTilesAsViews.get(i).getValue());
            assertEquals(this.consecutiveTilesPatternGoal.getScoreTiles().get(i).getPlayerID(), copyScoreTilesAsViews.get(i).getPlayerID());
            assertEquals(this.consecutiveTilesPatternGoal.getScoreTiles().get(i).getCommonGoalID(), copyScoreTilesAsViews.get(i).getCommonGoalID());
        }

        assertEquals(this.consecutiveTilesPatternGoal.getConsecutiveTiles(), copy.getConsecutiveTiles());
        assertEquals(this.consecutiveTilesPatternGoal.getNumberOfPatternRepetitionsRequired(), copy.getNumberOfPatternRepetitionsRequired());
        assertEquals(this.consecutiveTilesPatternGoal.getType(), copy.getType());
        assertEquals(this.consecutiveTilesPatternGoal.getId(), copy.getId());
    }
}
