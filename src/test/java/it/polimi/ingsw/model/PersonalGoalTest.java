package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.utils.OptionsValues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class PersonalGoalTest {
    private PersonalGoal personalGoal;
    private Bookshelf bookshelf;

    @BeforeEach
    public void cleanGoal() {
        Tile[][] personalGoalTiles = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}};

        this.bookshelf = null;
        this.personalGoal = new PersonalGoal(1, personalGoalTiles);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal on the given this.bookshelf matches one time")
    public void givenGenericBookshelf_whenCountingMatches_thenReturnOne() {
        Tile[][] tiles = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(1, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal on the rulebook's this.bookshelf matches 0 times")
    public void givenRulebookBookshelf_whenCountingMatches_thenReturnZero() {
        Tile[][] tiles = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(0, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches zero times on a this.bookshelf filled with nulls")
    public void givenBookshelfFilledWithNulls_whenCountingMatches_thenReturnZero() {
        Tile[][] tiles = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(0, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches three times on a this.bookshelf with tiles groups consisting of a single tile")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenCountingMatches_thenReturnThree() {
        Tile[][] tiles = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(3, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf zero times")
    public void givenRandomBookshelf_whenThereAreNoMatches_thenReturnZero() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, null},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(0, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_ZERO_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf one time")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnOne() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(1, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_ONE_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf two times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnTwo() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(2, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_TWO_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf three times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnThree() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(3, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_THREE_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf four times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnFour() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(4, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_FOUR_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf five times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnFive() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(5, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_FIVE_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that personal goal matches on the given this.bookshelf six times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnSix() {
        Tile[][] tiles = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)}};

        this.bookshelf = new Bookshelf(tiles);
        assertEquals(6, this.personalGoal.numberOfPatternRepetitionInBookshelf(this.bookshelf));
        assertEquals(OptionsValues.PERSONAL_GOAL_SIX_TILE_SCORE, this.personalGoal.score(this.bookshelf));
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that to string method return the expected value for null personal goal")
    public void to_string_return_expected_value_for_null_personal_goal() {
        this.personalGoal = new PersonalGoal();
        assertEquals(
                "[ 0 0 0 0 0 ]\n" +
                        "[ 0 0 0 0 0 ]\n" +
                        "[ 0 0 0 0 0 ]\n" +
                        "[ 0 0 0 0 0 ]\n" +
                        "[ 0 0 0 0 0 ]\n" +
                        "[ 0 0 0 0 0 ]", this.personalGoal.toString()
        );
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that to string method return the expected value for a generic personal goal")
    public void to_string_return_expected_value_for_generic_personal_goal() {
        Tile[][] personalGoalTiles = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}};

        PersonalGoal parameterPersonalGoal = new PersonalGoal(1, personalGoalTiles);

        this.personalGoal = new PersonalGoal(parameterPersonalGoal);

        assertEquals(
                "[ 0 0 0 0 \u001B[36mC\u001B[39m ]\n" +
                        "[ 0 \u001B[33mY\u001B[39m 0 0 0 ]\n" +
                        "[ \u001B[37mW\u001B[39m 0 0 0 0 ]\n" +
                        "[ 0 0 0 \u001B[32mG\u001B[39m 0 ]\n" +
                        "[ 0 \u001B[34mB\u001B[39m 0 0 0 ]\n" +
                        "[ 0 0 0 \u001B[35mP\u001B[39m 0 ]", this.personalGoal.toString()
        );
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that two personal goal are not the same")
    public void different_personal_goals_are_not_the_same() {
        Tile[][] personalGoalTiles = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}};

        PersonalGoal parameterPersonalGoal = new PersonalGoal();
        parameterPersonalGoal.setPattern(personalGoalTiles);

        assertNotEquals(parameterPersonalGoal, this.personalGoal);
        assertNotEquals(this.personalGoal, parameterPersonalGoal);
    }

    /**
     * Test class
     */
    @Test
    @DisplayName("Test that a personal goal is not equal to an object of another class")
    public void different_classes_are_not_equal_to_a_personal_goal() {
        assertNotEquals(this.personalGoal, this.bookshelf);
        assertNotEquals(this.bookshelf, this.personalGoal);
    }
}
