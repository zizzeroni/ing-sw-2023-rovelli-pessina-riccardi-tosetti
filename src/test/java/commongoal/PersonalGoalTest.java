package commongoal;

import model.Bookshelf;
import model.PersonalGoal;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonalGoalTest {
    private Tile[][] patternPersonalGoal1;
    private PersonalGoal personalGoal1;
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
        patternPersonalGoal1 = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}};
        b = null;
        personalGoal1 = new PersonalGoal("",patternPersonalGoal1);
    }

    @Test
    @DisplayName("Test that personal goal on the given bookshelf matches one time")
    public void givenGenericBookshelf_whenCountingMatches_thenReturnOne() {
        Tile[][] bs = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
        assertEquals(1,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal on the rulebook's bookshelf matches 0 times")
    public void givenRulebookBookshelf_whenCountingMatches_thenReturnZero() {
        Tile[][] bs = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};

        b = new Bookshelf("", bs);
        assertEquals(0,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches zero times on a bookshelf filled with nulls")
    public void givenBookshelfFilledWithNulls_whenCountingMatches_thenReturnZero() {
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);
        assertEquals(0,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches three times on a bookshelf with tiles groups consisting of a single tile")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenCountingMatches_thenReturnThree() {
        Tile[][] bs = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};

        b = new Bookshelf("", bs);
        assertEquals(3,personalGoal1.goalPattern(b));
    }


/*
    @ParameterizedTest
    @ArgumentsSource(testProvaProvider.class)
    public void testProva(Tile[][] input) {
        b = new Bookshelf("", input);
        assertEquals(1,personalGoal1.goalPattern(b));
    }*/

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf zero times")
    public void givenRandomBookshelf_whenThereAreNoMatches_thenReturnZero() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, null},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), null, null, null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(0,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf one time")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnOne() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(1,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf two times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnTwo() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(2,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf three times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnThree() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.PURPLE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(3,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf four times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnFour() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.YELLOW), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(4,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf five times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnFive() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(5,personalGoal1.goalPattern(b));
    }

    @Test
    @DisplayName("Test that personal goal matches on the given bookshelf six times")
    public void givenRandomBookshelf_whenCountingMatches_thenReturnSix() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.WHITE), null, null, new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), null, new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), null},
                {new Tile(TileColor.GREEN), new Tile(TileColor.CYAN), new Tile(TileColor.WHITE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)}};

        b = new Bookshelf("", bs);
        assertEquals(6,personalGoal1.goalPattern(b));
    }


}
