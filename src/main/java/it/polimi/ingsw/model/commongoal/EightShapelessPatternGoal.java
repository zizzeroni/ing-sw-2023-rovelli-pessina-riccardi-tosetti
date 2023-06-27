package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.EightShapelessPatternGoalView;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Class to represent the goal pattern with eight {@code Tile}s disposed in a shapeless form on the {@code Board} .
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Board
 */
public class EightShapelessPatternGoal extends CommonGoal {
    /**
     * Class constructor without parameters.
     * Builds an EightShapelessPatternGoal.
     */
    public EightShapelessPatternGoal() {
        super();
    }

    /**
     *
     * Class constructor with parameters.
     * Builds an EightShapelessPatternGoal with a specified type, ID, ...
     *
     * @param id the identifier assigned to the card.
     * @param patternRepetition contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     *
     */
    public EightShapelessPatternGoal(int id, int patternRepetition, CheckType type) {
        super(id, patternRepetition, type);
    }

    /**
     *
     * Class constructor with parameters.
     * Builds a EightShapelessPatternGoal with specific type, ID ...
     * (numberOfPlayers is also considered).
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param numberOfPlayers number of active players.
     */
    public EightShapelessPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
    }

    /**
     *
     * Class constructor with parameters.
     * Builds a EightShapelessPatternGoal with specific type, ID ...
     * (scoreTiles list is also considered).
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param scoreTiles  list of current score tiles.
     */
    public EightShapelessPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
    }

    /**
    * Controls if there are 8 {@code Tile}s of the same color in any position on the {@code Board}.
     *<p>
     *
    * @param bookshelf the {@code Bookshelf} of the {@code Player}.
    * @return the number of times the pattern is achieved.
     *
     * @see it.polimi.ingsw.model.Board
     * @see it.polimi.ingsw.model.Player
     * @see Bookshelf
    */
    @Override
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        return Math.toIntExact(Arrays.stream(bookshelf.getTiles())                //Transform the bookshelf in a Stream of tiles' ARRAYS
                .flatMap(Arrays::stream)                                          //Transform the ARRAY of tiles Stream into a Stream of ONLY tiles
                .filter(Objects::nonNull)                                         //I filter removing all elements equals to NULL (which i can't group after)
                .collect(groupingBy(Tile::getColor, Collectors.counting()))       //I group the tiles with "groupingBy" into a Map<TileColor, numberOfOccurences>, where i use the TileColor key specifing "Tile::GetColor", for the values instead i use the "counting()" method
                .entrySet().stream()                                              //I transform the Map into a Set and then into a Stream
                .filter(x -> x.getValue() >= 8).count());                         //I filter the Stream keeping only the Colors to which are associated more than 7 tiles and then i count them
    }

    /**
     * This method will be redefined in each common goal and will serve to print on the terminal the current type of common goal.
     *
     * @return an immutable copy of the EightShapelessPatternGoalView.
     *
     * @see CommonGoal
     */
    @Override
    public CommonGoalView copyImmutable() {
        return new EightShapelessPatternGoalView(this);
    }
}
