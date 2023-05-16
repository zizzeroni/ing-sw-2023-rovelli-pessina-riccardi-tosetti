package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commongoal.CommonGoal;
import it.polimi.ingsw.model.commongoal.EightShapelessPatternGoal;
import it.polimi.ingsw.model.commongoal.FourCornersPatternGoal;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.tile.TileColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class GameTest {
    private Game game;
    private List<Player> players;
    private List<CommonGoal> commonGoals;

    @BeforeEach
    public void resetAttributes() {
        this.game = null;
        this.players = new ArrayList<Player>(4);
        this.commonGoals = new ArrayList<CommonGoal>(2);

        List<ScoreTile> scoreTiles = new ArrayList<>();

        this.players.add(new Player("Alessandro", true, Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4)), new Bookshelf()));
        this.players.add(new Player("Andrea", true, Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4)), new Bookshelf()));
        this.players.add(new Player("Francesco", true, Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4)), new Bookshelf()));
        this.players.add(new Player("Luca", true, Arrays.asList(new ScoreTile(1), new ScoreTile(2), new ScoreTile(4)), new Bookshelf()));

        this.commonGoals.add(new FourCornersPatternGoal());
        this.commonGoals.add(new EightShapelessPatternGoal());

        this.players.get(0).setPersonalGoal(new PersonalGoal(1, new Tile[][]{
            {null, null, null, null, new Tile(TileColor.CYAN)},
            {null, new Tile(TileColor.YELLOW), null, null, null},
            {new Tile(TileColor.WHITE), null, null, null, null},
            {null, null, null, new Tile(TileColor.GREEN), null},
            {null, new Tile(TileColor.BLUE), null, null, null},
            {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(0).setPersonalGoal(new PersonalGoal(2, new Tile[][]{
            {null, null, null, null, new Tile(TileColor.CYAN)},
            {null, new Tile(TileColor.YELLOW), null, null, null},
            {new Tile(TileColor.WHITE), null, null, null, null},
            {null, null, null, new Tile(TileColor.GREEN), null},
            {null, new Tile(TileColor.BLUE), null, null, null},
            {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(0).setPersonalGoal(new PersonalGoal(3, new Tile[][]{
            {null, null, null, null, new Tile(TileColor.CYAN)},
            {null, new Tile(TileColor.YELLOW), null, null, null},
            {new Tile(TileColor.WHITE), null, null, null, null},
            {null, null, null, new Tile(TileColor.GREEN), null},
            {null, new Tile(TileColor.BLUE), null, null, null},
            {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));
        this.players.get(0).setPersonalGoal(new PersonalGoal(4, new Tile[][]{
            {null, null, null, null, new Tile(TileColor.CYAN)},
            {null, new Tile(TileColor.YELLOW), null, null, null},
            {new Tile(TileColor.WHITE), null, null, null, null},
            {null, null, null, new Tile(TileColor.GREEN), null},
            {null, new Tile(TileColor.BLUE), null, null, null},
            {null, null, null, new Tile(TileColor.PURPLE), null}}
        ));


    }

    @Test
    @DisplayName("Check if on turn change the size of the bag remains the same with a board who doesn't need a refill")
    public void bag_size_remains_the_same_when_board_refill_is_not_needed_on_turn_change() {

        Board board = new Board();
        board.setTiles(new Tile[][]{
            {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile(), new Tile()},
            {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
            {new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
            {new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
            {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE)},
            {new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile()},
            {new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile()},
            {new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()},
            {new Tile(), new Tile(), new Tile(), new Tile(), new Tile(TileColor.PURPLE), new Tile(TileColor.PURPLE), new Tile(), new Tile(), new Tile()}
        });

        List<Tile> bag = new ArrayList<>();

        for (int i = 0; i < 132; i++){
            bag.add(new Tile(TileColor.values()[i % 6]));
        }

        int size = bag.size();

        this.game = new Game(4, 0, this.players, bag, board, this.commonGoals);
        //this.game.changeTurn();

        assertEquals(size, this.game.getBag().size());
    }

//    @Test
//    @DisplayName("Check if on turn change the size of the bag reduces by the size of the tiles that are need to refill the board")
//    public void bag_size_reduces_by_the_number_of_tiles_that_are_needed_to_refill_the_board_on_turn_change() {
//
//        Board board = new Board();
//        board.setNumberOfUsableTiles(43);
//        board.setTiles(new Tile[][]{
//            {new Tile(), new Tile(), new Tile(), null, null, new Tile(), new Tile(), new Tile(), new Tile()},
//            {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
//            {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
//            {new Tile(), null, null, null, null, null, null, new Tile(), new Tile()},
//            {null, null, null, null, null, null, null, null, null},
//            {null, null, null, null, null, null, null, null, new Tile()},
//            {new Tile(), new Tile(), null, null, null, null, null, new Tile(), new Tile()},
//            {new Tile(), new Tile(), new Tile(), null, null, null, new Tile(), new Tile(), new Tile()},
//            {new Tile(), new Tile(), new Tile(), new Tile(), null, null, new Tile(), new Tile(), new Tile()}
//        });
//
//        List<Tile> bag = new ArrayList<>();
//
//        for (int i = 0; i < 132; i++){
//            bag.add(new Tile(TileColor.values()[i % 6]));
//        }
//
//        int size = bag.size();
//
//        this.game = new Game(4, 0, this.players, bag, board, this.commonGoals);
//
//        assertEquals(43, size - this.game.getBag().size());
//    }
}
