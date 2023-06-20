package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.MinEqualsTilesPatternView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class to represent the goal pattern with all {@code Tile}s
 * disposed in the following pattern on the {@code Board}.
 * Four lines each formed by 5 tiles of
 * maximum three different types. One
 * line can show the same or a different
 * combination of another line.
 *
 * @see it.polimi.ingsw.model.tile.Tile
 * @see it.polimi.ingsw.model.Board
 */
public class MinEqualsTilesPattern extends CommonGoal {
    //contains the directions that can be used in this pattern
    private Direction direction;
    //contains the maximum number of tiles that can be the same in a column/row
    private int maxEqualsTiles;     //HORIZONTAL should be 2, VERTICAL should be 3, full should be 0

    /**
     * Class constructor without parameters.
     * Builds a MinEqualsTilesPattern.
     */
    //Constructors
    public MinEqualsTilesPattern() {
        super();
        this.direction = null;
        this.maxEqualsTiles = 0;
    }

    /**
     * Class constructor with parameters.
     * Builds an TilesInPositionsPatternGoal with a specified type, ID, ...
     *
     * @param imageID the image assigned to the card.
     * @param patternRepetition contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param direction contains the directions that can be used in this pattern.
     * @param maxEqualsTiles contains the maximum number of tiles that can be the same in a column/row.
     */
    public MinEqualsTilesPattern(int imageID, int patternRepetition, CheckType type, Direction direction, int maxEqualsTiles) {
        super(imageID, patternRepetition, type);
        this.direction = direction;
        this.maxEqualsTiles = maxEqualsTiles;
    }

    /**
     * Class constructor with parameters.
     * Builds an TilesInPositionsPatternGoal with a specified type, ID, ...
     * (numberOfPlayers and commonGoalID are also considered in this version).
     *
     * @param imageID the image assigned to the card.
     * @param numberOfPatternRepetitionsRequired contains the number of times the personal goal must be completed to take the score tile.
     * @param type the type of check that has to be done on the considered common goal's card.
     * @param direction contains the directions that can be used in this pattern.
     * @param maxEqualsTiles contains the maximum number of tiles that can be the same in a column/row.
     * @param numberOfPlayers number of active players.
     * @param commonGoalID the identifier of the given common goal
     */
    public MinEqualsTilesPattern(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID, Direction direction, int maxEqualsTiles) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
        this.direction = direction;
        this.maxEqualsTiles = maxEqualsTiles;
    }

    /**
     * Gets the directions that is used to retrieve the pattern.
     *
     * @return contains the directions that is used for the pattern representation.
     *
     */
    //Get/Set method
    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Used to access maxEqualsTiles value.
     *
     * @return contains the maximum number of tiles that can be the same in a column/row
     *
     * @see MinEqualsTilesPattern#maxEqualsTiles
     */
    public int getMaxEqualsTiles() {
        return this.maxEqualsTiles;
    }

    public void setMaxEqualsTiles(int maxEqualsTiles) {
        this.maxEqualsTiles = maxEqualsTiles;
    }

    /**
     * Based on the direction:
     * Makes a comparison based on the type of request of all the rows/columns,
     * finding the number of different {@code Tile}s only in the complete rows/columns,
     * with the maximum number of equal tiles.
     *
     * @params bookshelf contains the bookshelf of the {@code Player}
     * @return the number of rows or column that respect the maxEquals
     * @param bookshelf is the selected {@code Bookshelf}.
     * @return the number of times the current pattern occurs in the {@code Bookshelf}.
     *
     * @see Bookshelf
     * @see it.polimi.ingsw.model.tile.Tile
     * @see it.polimi.ingsw.model.Player
     */
    @Override
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        List<TileColor> tileColorsInDirection = new ArrayList<>();
        int patternAppearances = 0;
        int appearancesInDirection = 0;
        switch (this.direction) {
            case HORIZONTAL -> {
                for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
                    if (!bookshelf.isRowFull(row)) {
                        continue;
                    }
                    for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                        tileColorsInDirection.add(bookshelf.getSingleTile(row, column).getColor());
                    }

                    tileColorsInDirection = tileColorsInDirection.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    try {
                        if (confrontEqualsDifferentTiles(bookshelf.getNumberOfColumns() - tileColorsInDirection.size(), this.getType())) {
                            appearancesInDirection++;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    tileColorsInDirection.clear();
                }
            }
            case VERTICAL -> {
                for (int column = 0; column < bookshelf.getNumberOfColumns(); column++) {
                    if (!bookshelf.isColumnFull(column)) {
                        continue;
                    }
                    for (int row = 0; row < bookshelf.getNumberOfRows(); row++) {
                        tileColorsInDirection.add(bookshelf.getSingleTile(row, column).getColor());
                    }

                    tileColorsInDirection = tileColorsInDirection.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    try {
                        if (confrontEqualsDifferentTiles(bookshelf.getNumberOfRows() - tileColorsInDirection.size(), this.getType())) {
                            appearancesInDirection++;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    tileColorsInDirection.clear();
                }
            }
            default -> {
                try {
                    throw new Exception("This direction does not exist");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        patternAppearances = appearancesInDirection;
        return patternAppearances;
    }

    /**
     * This method is used to verify the CheckType of the {@code Tile}s.
     *
     * @param numberOfEqualTiles number of total tiles found equal during the call of the method.
     * @param typeOfChecking the type checked by the method when it is called.
     * @return {@code true} if and only if the CheckType correspond,
     *          {@code false} otherwise.
     * @throws Exception if the considered CheckType does not exist.
     *
     * @see it.polimi.ingsw.model.tile.Tile
     */
    private boolean confrontEqualsDifferentTiles(int numberOfEqualTiles, CheckType typeOfChecking) throws Exception {
        switch (typeOfChecking) {
            case EQUALS, DIFFERENT -> {
                if (numberOfEqualTiles == this.maxEqualsTiles) {
                    return true;
                }
            }
            case INDIFFERENT -> {
                if (numberOfEqualTiles >= this.maxEqualsTiles) {
                    return true;
                }
            }
            default -> {
                throw new Exception("This CheckType does not exist");
            }
        }
        return false;
    }
    /**
     * This method will be redefined in each common goal and will serve to print on the terminal the current type of common goal.
     *
     * @return an immutable copy of the MinEqualsTilesPatternView.
     *
     * @see CommonGoal
     */
    @Override
    public CommonGoalView copyImmutable() {
        return new MinEqualsTilesPatternView(this);
    }
    /**
     * Redefine the equals method to allow a compare based on the MinEqualsTilesPattern.
     *
     * @param o is the object being evaluated to be equals to another (the one that calls the method).
     * @return {@code true} if and only if the two tiles are 'equals',
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof MinEqualsTilesPattern obj) {
            return this.getDirection() == obj.getDirection()
                    && this.getMaxEqualsTiles() == obj.getMaxEqualsTiles()
                    && this.getNumberOfPatternRepetitionsRequired() == obj.getNumberOfPatternRepetitionsRequired()
                    && this.getType() == obj.getType();
        }
        return false;
    }
}
