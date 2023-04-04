package commongoal;

import model.Bookshelf;
import model.PersonalGoal;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalGoalTest {
<<<<<<< Updated upstream
    private Tile[][] patternPersonalGoal1;
    private PersonalGoal personalGoal1;
=======
    private Tile[][] pg1;
    private Tile[][] pg2;
    private Tile[][] pg3;
    private Tile[][] pg4;
    private Tile[][] pg5;
    private Tile[][] pg6;
    private Tile[][] pg7;
    private Tile[][] pg8;
    private Tile[][] pg9;
    private Tile[][] pg10;
    private Tile[][] pg11;
    private Tile[][] pg12;
    private PersonalGoal obj1;
    private PersonalGoal obj2;
    private PersonalGoal obj3;
    private PersonalGoal obj4;
    private PersonalGoal obj5;
    private PersonalGoal obj6;
    private PersonalGoal obj7;
    private PersonalGoal obj8;
    private PersonalGoal obj9;
    private PersonalGoal obj10;
    private PersonalGoal obj11;
    private PersonalGoal obj12;
>>>>>>> Stashed changes
    private Bookshelf b;

    @BeforeEach
    public void cleanGoal() {
<<<<<<< Updated upstream
        patternPersonalGoal1 = new Tile[][]{
=======
        pg1 = new Tile[][]{
>>>>>>> Stashed changes
                {null, null, null, null, new Tile(TileColor.CYAN)},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null},
                {null, null, null, new Tile(TileColor.GREEN), null},
                {null, new Tile(TileColor.BLUE), null, null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null}};
<<<<<<< Updated upstream
        b = null;
        personalGoal1 = new PersonalGoal(1,patternPersonalGoal1);
    }

    @Test
    @DisplayName("Test that personal goal on the given bookshelf matches one time")
    public void givenGenericBookshelf_whenCountingMatches_thenReturnOne() {
=======

        pg2 = new Tile[][]{
                {null, null, null, null, null},
                {null, new Tile(TileColor.CYAN), null, null, null},
                {null, null, null, null, null},
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.WHITE), null, null},
                {null, null, null, null, new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.YELLOW), null, null, new Tile(TileColor.GREEN), null}};
        pg3 = new Tile[][]{
                {null, null, new Tile(TileColor.YELLOW), null, null},
                {null, null, null, null, null},
                {null, null, new Tile(TileColor.GREEN), null, null},
                {null, null, null, null, new Tile(TileColor.WHITE)},
                {null, new Tile(TileColor.CYAN), null, null, new Tile(TileColor.PURPLE)},
                {new Tile(TileColor.BLUE), null, null, null, null}};
        pg4 = new Tile[][]{
                {new Tile(TileColor.PURPLE), null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, new Tile(TileColor.YELLOW), null, null, null},
                {null, null, null, null, null},
                {null, null, new Tile(TileColor.CYAN), null, null}};
        pg5 = new Tile[][]{
                {null, null, null, null, new Tile(TileColor.YELLOW)},
                {null, null, null, null, null},
                {new Tile(TileColor.CYAN), null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, new Tile(TileColor.PURPLE), null},
                {null, new Tile(TileColor.WHITE), new Tile(TileColor.GREEN), null, null},
                {null, null, null, null, null}};
        pg6 = new Tile[][]{
                {null, null, new Tile(TileColor.WHITE), null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null},
                {null, null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, new Tile(TileColor.CYAN), null},
                {null, null, null, null, new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.GREEN), null, null, null, null}};
        pg7 = new Tile[][]{
                {null, null, null, null, null},
                {null, new Tile(TileColor.GREEN), null, null, null},
                {null, null, new Tile(TileColor.CYAN), null, null},
                {new Tile(TileColor.PURPLE), null, null, null, null},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, null, null, new Tile(TileColor.YELLOW), null}};
        pg8 = new Tile[][]{
                {null, null, null, null, null},
                {null, new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.GREEN), null, new Tile(TileColor.YELLOW), null, null},
                {null, null, null, null, new Tile(TileColor.WHITE)},
                {null, null, null, new Tile(TileColor.CYAN), null},
                {null, null, null, null, new Tile(TileColor.BLUE)}};
        pg9 = new Tile[][]{
                {new Tile(TileColor.GREEN), null, null, null, null},
                {null, null, null, new Tile(TileColor.BLUE), null},
                {null, new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.CYAN), null, null, null, null},
                {null, null, null, null, new Tile(TileColor.YELLOW)},
                {null, null, new Tile(TileColor.WHITE), null, null}};
        pg10 = new Tile[][]{
                {null, null, new Tile(TileColor.CYAN), null, new Tile(TileColor.GREEN)},
                {null, null, null, null, null},
                {null, null, null, new Tile(TileColor.WHITE), null},
                {null, null, null, null, null},
                {null, new Tile(TileColor.YELLOW), null, new Tile(TileColor.BLUE), null},
                {new Tile(TileColor.PURPLE), null, null, null, null}};
        pg11 = new Tile[][]{
                {null, null, null, null, null},
                {new Tile(TileColor.BLUE), null, null, new Tile(TileColor.YELLOW), null},
                {null, null, new Tile(TileColor.PURPLE), null, null},
                {null, new Tile(TileColor.GREEN), null, null, new Tile(TileColor.CYAN)},
                {null, null, null, null, null},
                {new Tile(TileColor.WHITE), null, null, null, null}};
        pg12 = new Tile[][]{
                {null, null, new Tile(TileColor.PURPLE), null, null},
                {null, new Tile(TileColor.WHITE), null, null, null},
                {new Tile(TileColor.YELLOW), null, null, null, null},
                {null, null, new Tile(TileColor.BLUE), null, null},
                {null, null, null, null, new Tile(TileColor.GREEN)},
                {null, null, null, new Tile(TileColor.CYAN), null}};
        b = null;
        obj1 = new PersonalGoal("",pg1);
        obj2 = new PersonalGoal("",pg2);
        obj3 = new PersonalGoal("",pg3);
        obj4 = new PersonalGoal("",pg4);
        obj5 = new PersonalGoal("",pg5);
        obj6 = new PersonalGoal("",pg6);
        obj7 = new PersonalGoal("",pg7);
        obj8 = new PersonalGoal("",pg8);
        obj9 = new PersonalGoal("",pg9);
        obj10 = new PersonalGoal("",pg10);
        obj11 = new PersonalGoal("",pg11);
        obj12 = new PersonalGoal("",pg12);
    }

    @Test
    @DisplayName("Test personal goals on file JSON with generic bookshelf")
    public void GoalsGenericBookshelf() {
>>>>>>> Stashed changes
        Tile[][] bs = {
                {null, new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.GREEN), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
<<<<<<< Updated upstream
        assertEquals(1,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that personal goal on the rulebook's bookshelf matches 0 times")
    public void givenRulebookBookshelf_whenCountingMatches_thenReturnZero() {
=======
        assertEquals(1,obj1.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj2.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj3.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(0,obj4.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj5.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj6.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj7.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj8.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj9.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj10.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj11.numberOfPatternRepetitionInBookshelf(b));
        assertEquals(1,obj12.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test personal goals on file JSON with Rulebook's example bookshelf")
    public void GoalsRulebookBookshelf() {
>>>>>>> Stashed changes
        Tile[][] bs = {
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null, null, null},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), null},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.CYAN), new Tile(TileColor.GREEN), new Tile(TileColor.GREEN)}};
<<<<<<< Updated upstream
=======

        b = new Bookshelf("", bs);
        assertEquals(0,obj1.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj2.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(2,obj3.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(3,obj4.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj5.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj6.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj7.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(2,obj8.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj9.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj10.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj11.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj12.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test personal goals on file JSON with bookshelf with all items = null")
    public void GoalsNullBookshelf() {
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);
        assertEquals(0,obj1.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj2.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj3.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj4.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj5.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj6.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj7.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj8.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj9.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj10.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj11.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj12.numberOfPatternRepetitionInBookshelf(b));
    }
    @Test
    @DisplayName("Test personal goals on file JSON with bookshelf with all items equals to each other")
    public void GoalsEqualsBookshelf() {
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);
        assertEquals(1,obj1.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj2.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj3.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj4.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj5.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj6.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj7.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj8.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj9.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj10.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj11.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj12.numberOfPatternRepetitionInBookshelf(b));
    }
    @Test
    @DisplayName("Test personal goals on file JSON with bookshelf with all items different to each other")
    public void GoalsAllDifferentBookshelf() {
        Tile[][] bs = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};

        b = new Bookshelf("", bs);
        assertEquals(3,obj1.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj2.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj3.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj4.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj5.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj6.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(1,obj7.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj8.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(2,obj9.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj10.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(0,obj11.numberOfPatternRepetitionInBookshelf(b));

        assertEquals(2,obj12.numberOfPatternRepetitionInBookshelf(b));
>>>>>>> Stashed changes

        b = new Bookshelf("", bs);
        assertEquals(0,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
<<<<<<< Updated upstream
    @DisplayName("Test that personal goal matches zero times on a bookshelf filled with nulls")
    public void givenBookshelfFilledWithNulls_whenCountingMatches_thenReturnZero() {
=======
    @DisplayName("Test score method with bookshelf null")
    public void scoreTestBookshelfNull(){
>>>>>>> Stashed changes
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);
<<<<<<< Updated upstream
        assertEquals(0,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
    }

    @Test
    @DisplayName("Test that personal goal matches three times on a bookshelf with tiles groups consisting of a single tile")
    public void givenBookshelfWithGroupsOfOneSingleTile_whenCountingMatches_thenReturnThree() {
=======

        assertEquals(0,b.score());
    }
    @Test
    @DisplayName("Test score method with bookshelf null")
    public void scoreTestBookshelfAllEqualElements(){
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);

        assertEquals(0,b.score()); //!0
    }
    @Test
    @DisplayName("Test score method with bookshelf null")
    public void scoreTestBookshelfAllDifferentElements(){
>>>>>>> Stashed changes
        Tile[][] bs = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};

        b = new Bookshelf("", bs);
<<<<<<< Updated upstream
        assertEquals(3,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(0,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(1,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(2,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(3,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(4,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(5,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
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
        assertEquals(6,personalGoal1.numberOfPatternRepetitionInBookshelf(b));
    }


=======

        assertEquals(0,b.score()); //!0
    }
    @Test
    @DisplayName("Test score method with bookshelf null")
    public void scoreTestNull(){
        Tile[][] bs = {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}};

        b = new Bookshelf("", bs);

        assertEquals(0,obj1.score(b)); //!0
        assertEquals(0,obj2.score(b));
        assertEquals(0,obj3.score(b));
        assertEquals(0,obj4.score(b));
        assertEquals(0,obj5.score(b));
        assertEquals(0,obj6.score(b));
        assertEquals(0,obj7.score(b));
        assertEquals(0,obj8.score(b));
        assertEquals(0,obj9.score(b));
        assertEquals(0,obj10.score(b));
        assertEquals(0,obj11.score(b));
        assertEquals(0,obj12.score(b));
    }@Test
    @DisplayName("Test score method with bookshelf null")
    public void scoreTestAllEqualElements(){
        Tile[][] bs = {
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE), new Tile(TileColor.BLUE)}};

        b = new Bookshelf("", bs);

        assertEquals(0,obj1.score(b)); //!0 devono essere i 6 possibili valori del case ?
        assertEquals(0,obj2.score(b));
        assertEquals(0,obj3.score(b));
        assertEquals(0,obj4.score(b));
        assertEquals(0,obj5.score(b));
        assertEquals(0,obj6.score(b));
        assertEquals(0,obj7.score(b));
        assertEquals(0,obj8.score(b));
        assertEquals(0,obj9.score(b));
        assertEquals(0,obj10.score(b));
        assertEquals(0,obj11.score(b));
        assertEquals(0,obj12.score(b));
    }
    @Test
    @DisplayName("Test score method with bookshelf null")
    public void scoreTestAllDifferentElements(){
        Tile[][] bs = {
                {new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN)},
                {new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE)},
                {new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE)},
                {new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN), new Tile(TileColor.YELLOW)},
                {new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE), new Tile(TileColor.GREEN)},
                {new Tile(TileColor.YELLOW), new Tile(TileColor.WHITE), new Tile(TileColor.BLUE), new Tile(TileColor.CYAN), new Tile(TileColor.PURPLE)}};

        b = new Bookshelf("", bs);

        assertEquals(0,obj1.score(b)); //!0 valori casi 6 test relativi  ?
        assertEquals(0,obj2.score(b));
        assertEquals(0,obj3.score(b));
        assertEquals(0,obj4.score(b));
        assertEquals(0,obj5.score(b));
        assertEquals(0,obj6.score(b));
        assertEquals(0,obj7.score(b));
        assertEquals(0,obj8.score(b));
        assertEquals(0,obj9.score(b));
        assertEquals(0,obj10.score(b));
        assertEquals(0,obj11.score(b));
        assertEquals(0,obj12.score(b));
    }
>>>>>>> Stashed changes
}
