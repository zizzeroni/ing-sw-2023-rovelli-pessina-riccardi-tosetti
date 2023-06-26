package it.polimi.ingsw.model;

import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.utils.OptionsValues;

/**
 * This class represents the {@code Player}'s personal goal.
 * It provides a series of methods to access and sets the personal gaol's pattern,
 * accessing single goal pattern's {@code Tile}s, ...
 * It also permits to evaluate the number of goal pattern's repetitions in the {@code Bookshelf}
 * and the score of the {@code Player} completing it.
 *
 * @see Player
 * @see Bookshelf
 * @see Tile
 */
public class PersonalGoal extends Card {
    private final int numberOfColumns = 5;
    private final int numberOfRows = 6;
    private Tile[][] pattern;

    /**
     * Class constructor.
     */
    public PersonalGoal() {
        super();
        this.pattern = new Tile[this.numberOfRows][this.numberOfColumns];
        for (int row = 0; row < this.numberOfRows; row++) {
            for (int column = 0; column < this.numberOfColumns; column++) {
                this.pattern[row][column] = null;
            }
        }
    }

    /**
     * Class constructor.
     *
     * @param id the identifier of the personal goal.
     * @param pattern the pattern associated to the personal goal.
     */
    public PersonalGoal(int id, Tile[][] pattern) {
        super(id);
        this.pattern = pattern;
    }

    public PersonalGoal(PersonalGoal personalGoal) {
        this.pattern = personalGoal.getPattern();
        this.setId(personalGoal.getId());
    }

    /**
     * Gets the number of columns in the personal goal's pattern.
     *
     * @return the number of columns.
     */
    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    /**
     * Gets the number of rows in the personal goal's pattern.
     *
     * @return the number of rows.
     */
    public int getNumberOfRows() {
        return this.numberOfRows;
    }

    /**
     * Gets the {@code PersonalGoal}'s pattern.
     *
     * @return the pattern linked to the current personal goal.
     */
    public Tile[][] getPattern() {
        return this.pattern;
    }

    /**
     * Sets the personal goal's pattern.
     *
     * @param pattern is the pattern to be set.
     */
    public void setPattern(Tile[][] pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets the {@code Tile} at the given coordinates,
     * expressed as rows and columns.
     *
     * @param row is the first coordinate.
     * @param column is the second coordinate.
     * @return the Tile of the specified {@code PersonalGoal}.
     *
     * @see Tile
     */
    public Tile getSingleTile(int row, int column) {
        return this.pattern[row][column];
    }

    /**
     * Gives the number of pattern repetitions in the chosen Bookshelf.
     *
     * @param bookshelf is the selected {@code Bookshelf}.
     * @return the number of pattern's repetitions.
     */
    @Override
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
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

    /**
     * Evaluates the score of the Bookshelf based on the repetitions of the {@code PersonalGoal}'s patterns.
     *
     * @param bookshelf is the {@code Player}'s {@code Bookshelf}.
     * @return the value of the points assigned to the pattern repetitions identified in the {@code Bookshelf}
     *          using the #numberOfPatternRepetitionInBookshelf.
     *
     * @see Bookshelf
     * @see Player
     */
    public int score(Bookshelf bookshelf) {
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
            default -> {
                return OptionsValues.PERSONAL_GOAL_SIX_TILE_SCORE;
            }
        }
    }

    /**
     * Deploys a simple representation of the personal goal.
     *
     * @return the String representing the {@code PersonalGoal}
     */
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int row = 0; row < this.numberOfRows; row++) {
            output.append("[ ");
            for (int column = 0; column < this.numberOfColumns; column++) {
                Tile currentTile = this.pattern[row][column];
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PersonalGoal))
            return false;

        return (this.getId() == ((PersonalGoal) obj).getId());
    }
}
