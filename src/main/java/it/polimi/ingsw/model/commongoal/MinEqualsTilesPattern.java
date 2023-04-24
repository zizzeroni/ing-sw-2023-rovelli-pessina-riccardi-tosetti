package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.TileColor;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MinEqualsTilesPattern extends CommonGoal {
    private Direction direction;
    private int maxEqualsTiles;     //HORIZONTAL should be 2, VERTICAL should be 3, full should be 0

    public MinEqualsTilesPattern() {
        super();
        this.direction = null;
        this.maxEqualsTiles = 0;
    }

    public MinEqualsTilesPattern(int imageID, int patternRepetition, CheckType type, Direction direction, int maxEqualsTiles) {
        super(imageID, patternRepetition, type);
        this.direction = direction;
        this.maxEqualsTiles = maxEqualsTiles;
    }

    public MinEqualsTilesPattern(int imageID, int patternRepetition, CheckType type, int numberOfPlayers, Direction direction, int maxEqualsTiles) {
        super(imageID, patternRepetition, type, numberOfPlayers);
        this.direction = direction;
        this.maxEqualsTiles = maxEqualsTiles;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMaxEqualsTiles() {
        return this.maxEqualsTiles;
    }

    public void setMaxEqualsTiles(int maxEqualsTiles) {
        this.maxEqualsTiles = maxEqualsTiles;
    }

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
        patternAppearances = appearancesInDirection / this.getNumberOfPatternRepetitionsRequired();
        return patternAppearances;
    }

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
}
