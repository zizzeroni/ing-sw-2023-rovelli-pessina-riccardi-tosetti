import model.Board;
import model.Game;
import model.PersonalGoal;
import model.Player;
import model.commongoal.CommonGoal;
import model.commongoal.EightShaplessPatternGoal;
import model.commongoal.FourCornersPatternGoal;
import model.tile.Tile;
import model.tile.TileColor;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
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

        this.players.add(new Player("Alessandro", true));
        this.players.add(new Player("Andrea", true));
        this.players.add(new Player("Francesco", true));
        this.players.add(new Player("Luca", true));

        this.commonGoals.add(new FourCornersPatternGoal());
        this.commonGoals.add(new EightShaplessPatternGoal());

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

        this.game.changeTurn();

        assertEquals(this.game.getBag().size(), size);
    }
}
