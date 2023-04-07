package org.example.commongoal;

import org.example.model.Bookshelf;
import org.example.model.commongoal.CheckType;
import org.example.model.commongoal.ConsecutiveTilesPatternGoal;
import org.example.model.tile.Tile;
import org.example.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsecutiveTilesPatternGoalTest {
    private ConsecutiveTilesPatternGoal consecutiveTiles;
    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        consecutiveTiles = null;
        bookshelf = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnThree() {
        int consecutive = 2;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(3, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 4;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a full of blue element bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnOne() {
        int consecutive = 2;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a full of blue bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnOne() {
        int consecutive = 4;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a full of null element bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 2;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a full of null bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 4;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least two consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAMixedBookshelf_whenSearchingTheTwoConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 2;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(3, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with at least four consecutive element of the same colour in a generic bookshelf matches zero time")
    public void GivenAMixedElementBookshelf_whenSearchingFourConsecutiveElementPattern_thenReturnZero() {
        int consecutive = 4;
        consecutiveTiles = new ConsecutiveTilesPatternGoal(0,1, CheckType.EQUALS, consecutive );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, consecutiveTiles.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
}
