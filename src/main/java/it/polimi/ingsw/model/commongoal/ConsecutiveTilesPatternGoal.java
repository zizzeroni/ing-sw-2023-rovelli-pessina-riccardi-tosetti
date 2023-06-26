package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.ScoreTile;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.ConsecutiveTilesPatternGoalView;

import java.util.List;

/**
 * This class is used to represent those objects that contains the number of {@code Tile}s
 * that must be consecutive for making a point considering the pattern of the goal.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see CommonGoal
 */
public class ConsecutiveTilesPatternGoal extends CommonGoal {

    private final int consecutiveTiles;

    /**
     * Constructor of the class in the implementation with no parameters.
     * Builds a ConsecutiveTilesPatternGoal with no type, ID, ...
     */
    public ConsecutiveTilesPatternGoal() {
        super();
        this.consecutiveTiles = 0;
    }

    /**
     *
     * Constructor of the class in the implementation with parameters.
     * Builds a ConsecutiveTilesPatternGoal with type, ID, ...
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param consecutiveTiles the number of consecutive tile for making a point considering the pattern of the goal.
     */
    public ConsecutiveTilesPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int consecutiveTiles) {
        super(id, numberOfPatternRepetitionsRequired, type);
        this.consecutiveTiles = consecutiveTiles;
    }

    /**
     *
     * Constructor of the class in the implementation with parameters.
     * Builds a ConsecutiveTilesPatternGoal with type, ID, ...
     * In this version are also considered the commonGoalID and numberOfPlayers.
     *
     * @param id the identifier assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param consecutiveTiles the number of consecutive tile for making a point considering the pattern of the goal.
     * @param numberOfPlayers number of active players.
     */
    public ConsecutiveTilesPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int consecutiveTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, numberOfPlayers);
        this.consecutiveTiles = consecutiveTiles;
    }

    public ConsecutiveTilesPatternGoal(int id, int numberOfPatternRepetitionsRequired, CheckType type, List<ScoreTile> scoreTiles, int consecutiveTiles) {
        super(id, numberOfPatternRepetitionsRequired, type, scoreTiles);
        this.consecutiveTiles = consecutiveTiles;
    }

    /**
     * Here we search the number of pattern repetition in the player's bookshelf by declaring a support matrix of the same dimensions of the bookshelf,
     * for every not null tile we assign the number 1 in the support matrix ( 0 for the nulls).
     * Start from the first not null tile, we assign in the support matrix in the position of the tile the group 2
     * then we search if the nearby tiles are of the same colour and if it is true we assign the same group of the first tile.
     * <p>
     * In the second part we count the number of different groups when the counter of the tiles in a group is
     * at least the minimum number of consecutive tiles of the pattern goal.
     *
     * @param bookshelf contains the bookshelf of the player
     * @return generalCounter contains the number of group that have at least the minimum number of consecutive tiles
     *
     * @see it.polimi.ingsw.model.tile.Tile
     * @see Bookshelf
     */
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        int[][] supportMatrix = new int[bookshelf.getNumberOfRows()][bookshelf.getNumberOfColumns()];
        for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                if (bookshelf.getSingleTile(row, column) == null) {
                    supportMatrix[row][column] = 0;
                } else {
                    supportMatrix[row][column] = 1;
                }
            }
        }
        int group = 1;

        for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
            for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                if (supportMatrix[row][column] == 1) {
                    group++;
                    searchGroup(bookshelf, supportMatrix, row, column, group, bookshelf.getSingleTile(row, column).getColor());
                }
            }
        }
        int groupCounter = 0;
        int generalCounter = 0;
        for (int g = 2; g <= group; g++) {
            for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
                for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                    if (supportMatrix[row][column] == g) {
                        groupCounter++;
                    }
                }
            }
            if (groupCounter >= this.consecutiveTiles) {
                generalCounter++;
            }
            groupCounter = 0;
        }
        return generalCounter;
    }

    /**
     * Applies the search pattern algorithm on the {@code Player}'s {@code Bookshelf},
     * confronting the currentTileColor on adjacent rows and lines.
     * Also, a support matrix is used when necessary to keep track of the current group of tiles,
     * each group is distinguished for both number of tiles and color.
     *
     * @param bookshelf the bookshelf of the current active player.
     * @param supportMatrix the matrix used as a support during the algorithm's unfolding.
     * @param row the current row.
     * @param column the current column.
     * @param group the group assigned to the current set of tiles.
     * @param currentTileColor the color of the actual group of tiles.
     *
     * @see it.polimi.ingsw.model.Player
     * @see Bookshelf
     */
    private void searchGroup(Bookshelf bookshelf, int[][] supportMatrix, int row, int column, int group, TileColor currentTileColor) {
        if ((supportMatrix[row][column] == 1) && currentTileColor.equals(bookshelf.getSingleTile(row, column).getColor())) {
            supportMatrix[row][column] = group;

            //up
            if (row != 0) {
                searchGroup(bookshelf, supportMatrix, row - 1, column, group, currentTileColor);
            }
            //left
            if (column != 0) {
                searchGroup(bookshelf, supportMatrix, row, column - 1, group, currentTileColor);
            }
            //down
            if (row != bookshelf.getNumberOfRows() - 1) {
                searchGroup(bookshelf, supportMatrix, row + 1, column, group, currentTileColor);
            }
            //right
            if (column != bookshelf.getNumberOfColumns() - 1) {
                searchGroup(bookshelf, supportMatrix, row, column + 1, group, currentTileColor);
            }
        }
    }

    /**
     * Getter method to access the consecutiveTiles attribute.
     *
     * @return the value associated to consecutiveTiles.
     */

    public int getConsecutiveTiles() {
        return this.consecutiveTiles;
    }

    /**
     * Generates an immutable copy of the current {@code commonGoal}.
     *
     * @return an immutable copy of the ConsecutiveTilesPatternGoal.
     */
    @Override
    public CommonGoalView copyImmutable() {
        return new ConsecutiveTilesPatternGoalView(this);
    }
}
