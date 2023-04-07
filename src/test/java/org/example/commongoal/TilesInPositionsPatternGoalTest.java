package org.example.commongoal;

import org.example.model.Bookshelf;
import org.example.model.commongoal.CheckType;
import org.example.model.commongoal.TilesInPositionsPatternGoal;
import org.example.model.tile.Tile;
import org.example.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TilesInPositionsPatternGoalTest {
    private TilesInPositionsPatternGoal fourElementAsASquare;
    private Bookshelf bookshelf;
    private int[][] positions;
    @BeforeEach
    public void cleanGoal() {
        fourElementAsASquare = null;
        bookshelf = null;
        positions = null;
    }

    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        this.positions = new int[][]{
                {1, 1},
                {1, 1}
        };
        fourElementAsASquare = new TilesInPositionsPatternGoal(0,1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a full of blue element bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingTheFourElementSquare_thenReturnOne() {
        this.positions = new int[][]{
                {1, 1},
                {1, 1}
        };
        fourElementAsASquare = new TilesInPositionsPatternGoal(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(1, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a full of null element bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        this.positions = new int[][]{
                {1, 1},
                {1, 1}
        };
        fourElementAsASquare = new TilesInPositionsPatternGoal(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a generic bookshelf matches zero time")
    public void GivenAMixedBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        this.positions = new int[][]{
                {1, 1},
                {1, 1}
        };
        fourElementAsASquare = new TilesInPositionsPatternGoal(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(0, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a casual three colour bookshelf matches two time")
    public void GivenACasual3ColorBookshelf_whenSearchingTheFourElementSquare_thenReturnTwo() {
        this.positions = new int[][]{
                {1, 1},
                {1, 1}
        };
        fourElementAsASquare = new TilesInPositionsPatternGoal(0,1, CheckType.EQUALS, positions );
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        bookshelf = new Bookshelf("", temp);

        assertEquals(2, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }
}
