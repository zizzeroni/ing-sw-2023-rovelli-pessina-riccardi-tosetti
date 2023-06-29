package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.ScoreTileView;
import it.polimi.ingsw.model.view.commongoal.TilesInPositionsPatternGoalView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TilesInPositionsPatternGoalTest {
    private TilesInPositionsPatternGoal fourElementAsASquare;
    private Bookshelf bookshelf;
    private List<List<Integer>> positions;

    @BeforeEach
    public void cleanGoal() {
        fourElementAsASquare = new TilesInPositionsPatternGoal();
        bookshelf = null;
        positions = null;
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a generic bookshelf matches zero time")
    public void GivenAGenericBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        this.positions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 1)),
                new ArrayList<>(Arrays.asList(1, 1))
        ));
        fourElementAsASquare = new TilesInPositionsPatternGoal(0, 1, CheckType.EQUALS, 2, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a full of blue element bookshelf matches one time")
    public void GivenAFullOfBlueBookshelf_whenSearchingTheFourElementSquare_thenReturnOne() {
        this.positions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 1)),
                new ArrayList<>(Arrays.asList(1, 1))
        ));
        fourElementAsASquare = new TilesInPositionsPatternGoal(0, 1, CheckType.EQUALS, new ArrayList<>(Arrays.asList(new ScoreTile(), new ScoreTile())), positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(1, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a full of null element bookshelf matches zero time")
    public void GivenAFullOfNullBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        this.positions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 1)),
                new ArrayList<>(Arrays.asList(1, 1))
        ));
        fourElementAsASquare = new TilesInPositionsPatternGoal(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a generic bookshelf matches zero time")
    public void GivenAMixedBookshelf_whenSearchingTheFourElementSquare_thenReturnZero() {
        this.positions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 1)),
                new ArrayList<>(Arrays.asList(1, 1))
        ));
        fourElementAsASquare = new TilesInPositionsPatternGoal(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.BLUE)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(0, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that the commonGoal with four element as a square in a casual three colour bookshelf matches two time")
    public void GivenACasual3ColorBookshelf_whenSearchingTheFourElementSquare_thenReturnTwo() {
        this.positions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 1)),
                new ArrayList<>(Arrays.asList(1, 1))
        ));
        fourElementAsASquare = new TilesInPositionsPatternGoal(0, 1, CheckType.EQUALS, positions);
        Tile[][] temp = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
        bookshelf = new Bookshelf(temp);

        assertEquals(2, fourElementAsASquare.numberOfPatternRepetitionInBookshelf(bookshelf));
    }

    /**
     *
     */
    @Test
    @DisplayName("Test that copyImmutable creates a view equal to the model")
    public void immutable_copy_is_equal_to_the_original_model() {
        this.positions = new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList(1, 1)),
                new ArrayList<>(Arrays.asList(1, 1))
        ));

        this.fourElementAsASquare = new TilesInPositionsPatternGoal(0, 1, CheckType.EQUALS, positions);

        TilesInPositionsPatternGoalView copy = this.fourElementAsASquare.copyImmutable();

        List<ScoreTileView> copyScoreTilesAsViews = new ArrayList<>();

        this.fourElementAsASquare.getScoreTiles().forEach(scoreTile -> copyScoreTilesAsViews.add(new ScoreTileView(scoreTile)));

        for (int i = 0; i < copyScoreTilesAsViews.size(); i++) {
            assertEquals(this.fourElementAsASquare.getScoreTiles().get(i).getValue(), copyScoreTilesAsViews.get(i).getValue());
            assertEquals(this.fourElementAsASquare.getScoreTiles().get(i).getPlayerID(), copyScoreTilesAsViews.get(i).getPlayerID());
            assertEquals(this.fourElementAsASquare.getScoreTiles().get(i).getCommonGoalID(), copyScoreTilesAsViews.get(i).getCommonGoalID());
        }

        for (int i = 0; i < this.fourElementAsASquare.getPositions().size(); i++) {
            for (int j = 0; j < this.fourElementAsASquare.getPositions().get(0).size(); j++) {
                assertEquals(this.fourElementAsASquare.getPositions().get(i).get(j), copy.getPositions().get(i).get(j));
            }
        }

        assertEquals(this.fourElementAsASquare.getNumberOfPatternRepetitionsRequired(), copy.getNumberOfPatternRepetitionsRequired());
        assertEquals(this.fourElementAsASquare.getType(), copy.getType());
        assertEquals(this.fourElementAsASquare.getId(), copy.getId());
    }
}
