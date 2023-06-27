package it.polimi.ingsw.model.view;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.PersonalGoal;
import it.polimi.ingsw.utils.OptionsValues;

import java.io.Serializable;

/**
 * This class represents the PersonalGoal's view.
 * The class contains a series of getters to access their personal goals, their goal tiles, their {@code Bookshelf}s, chats
 * and a series of other class related relevant informations.
 *
 *
 * @see PersonalGoal
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Player
 */
public class PersonalGoalView implements Serializable {
    private final int numberOfColumns;
    private final int numberOfRows;
    private final TileView[][] pattern;
    private final int id;    //Attribute from Card class, of which we don't have a "View" class

    /**
     * Class constructor.
     * Used to associate the representation of the {@code PersonalGoal} in the {@code PersonalGoalView}
     * with the linked logic in the {@code personalGoalModel} (passed as parameter).
     *
     * @param personalGoalModel the model of the considered {@code PersonalGoal}.
     *
     * @see PersonalGoal
     *
     */
    public PersonalGoalView(PersonalGoal personalGoalModel) {
        this.numberOfColumns = personalGoalModel.getNumberOfColumns();
        this.numberOfRows = personalGoalModel.getNumberOfRows();
        this.pattern = new TileView[personalGoalModel.getNumberOfRows()][personalGoalModel.getNumberOfColumns()];
        this.id = personalGoalModel.getId();
        for (int row = 0; row < personalGoalModel.getNumberOfRows(); row++) {
            for (int column = 0; column < personalGoalModel.getNumberOfColumns(); column++) {
                this.pattern[row][column] = (personalGoalModel.getSingleTile(row, column) != null ? new TileView(personalGoalModel.getSingleTile(row, column)) : null);
            }
        }
    }

    /*
     * TODO
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter that returns the number of columns
     * in the {@code Bookshelf}.
     *
     * @return the number of columns.
     *
     * @see Bookshelf
     */
    public int getNumColumns() {
        return this.numberOfColumns;
    }

    /**
     * Getter that returns the number of rows
     * in the {@code Bookshelf}.
     *
     * @return the number of rows.
     *
     * @see Bookshelf
     */
    public int getNumRows() {
        return this.numberOfRows;
    }

    /**
     * Getter to access the pattern (the one linked to the current {@code PersonalGoal}).
     *
     * @return the current personal goal's pattern.
     *
     * @see PersonalGoal
     */
    public TileView[][] getPattern() {
        return this.pattern;
    }

    /**
     * Used to access the value of a specified {@code Tile} in the {@code Bookshelf}.
     * The one at the given coordinates.
     *
     * @param row is the first bookshelf's coordinate.
     * @param column is the second bookshelf's coordinate
     * @return the tile at the given coordinates.
     *
     * @see Bookshelf
     * @see it.polimi.ingsw.model.tile.Tile
     */
    public TileView getSingleTile(int row, int column) {
        return this.pattern[row][column];
    }

    /**
     * This method is used to display the result of the user's interactions
     * during the {@code Game} (those linked with the {@code PersonalGoal}s)
     * of the active {@code Player}s.
     *
     * @return a string representing the display of the PersonalGoal.
     *
     * @see it.polimi.ingsw.model.Game
     * @see PersonalGoal
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < this.numberOfRows; row++) {
            output.append("[ ");
            for (int column = 0; column < this.numberOfColumns; column++) {
                TileView currentTile = this.pattern[row][column];
                if (currentTile == null || currentTile.getColor() == null) {
                    output.append("0 ");
                } else {
                    output.append(currentTile.getColor()).append(" ");
                }
            }
            output.append("]\n");
        }
        return output.substring(0, output.length() - 1);
    }

    /**
     * Used to calculate the score for the {@code Tile}'s patterns actually present
     * on the {@code Player}'s {@code Bookshelf}.
     *
     * @param bookshelf the bookshelf of the considered player.
     * @return the current score.
     *
     * @see it.polimi.ingsw.model.Player
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Bookshelf
     */
    public int score(BookshelfView bookshelf) {
        switch (this.numberOfPatternRepetitionInBookshelf(bookshelf)) {
            case 0 -> {
                return OptionsValues.PERSONAL_GOAL_ZERO_TILE_SCORE;
            }
            case 1 -> {
                return OptionsValues.PERSONAL_GOAL_ONE_TILE_SCORE;
            }
            case 2 -> {
                return OptionsValues.PERSONAL_GOAL_TWO_TILE_SCORE;
            }
            case 3 -> {
                return OptionsValues.PERSONAL_GOAL_THREE_TILE_SCORE;
            }
            case 4 -> {
                return OptionsValues.PERSONAL_GOAL_FOUR_TILE_SCORE;
            }
            case 5 -> {
                return OptionsValues.PERSONAL_GOAL_FIVE_TILE_SCORE;
            }
            case 6 -> {
                return OptionsValues.PERSONAL_GOAL_SIX_TILE_SCORE;
            }
            default -> {
                return OptionsValues.PERSONAL_GOAL_SIX_TILE_SCORE;
            }
        }
    }

    /**
     * Calculates the number of the repetitions of the possible goal patterns
     * in the {@code Bookshelf} of the active {@code Player}.
     *
     * @param bookshelf
     * @return
     *
     * @see it.polimi.ingsw.model.Bookshelf
     * @see it.polimi.ingsw.model.Player
     */
    private int numberOfPatternRepetitionInBookshelf(BookshelfView bookshelf) {
        int counter = 0;
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                if (this.pattern[row][column] != null && bookshelf.getSingleTile(row, column) != null && bookshelf.getSingleTile(row, column).getColor().equals(this.pattern[row][column].getColor())) {
                    counter++;
                }
            }
        }
        return counter;
    }

}
