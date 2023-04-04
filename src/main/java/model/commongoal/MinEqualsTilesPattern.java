package model.commongoal;

import model.Bookshelf;
import model.tile.ScoreTile;
import model.tile.TileColor;

import java.util.*;
import java.util.stream.Collectors;

public class MinEqualsTilesPattern extends CommonGoal{

    private Direction direction;
    private int maxEqualsTiles;     //Per HORIZONTAL deve essere pari a 2, per VERTICAL deve essere pari a 3, per quelle complete deve essere 0

    public MinEqualsTilesPattern() {
        super();
        direction = null;
        maxEqualsTiles = 0;
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
        List<TileColor> recentTiles = new ArrayList<TileColor>();
        int patternAppearances=0;
        int cAppearances=0;
        switch (this.direction) {
            case HORIZONTAL -> {
                for (int i = 0; i < bookshelf.getNumRows(); i++) {
                    if (!bookshelf.isRowFull(i)) {
                        continue;
                    }
                    for (int j = 0; j < bookshelf.getNumColumns(); j++) {
                        recentTiles.add(bookshelf.getSingleTile(i, j).getColor());
                    }

                    recentTiles = recentTiles.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    try {
                        if (confrontEqualsDifferentTiles(bookshelf.getNumColumns() - recentTiles.size(), this.getType())) {
                            cAppearances++;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    recentTiles.clear();
                }
            }
            case VERTICAL -> {
                for (int i = 0; i < bookshelf.getNumColumns(); i++) {
                    if (!bookshelf.isColumnFull(i)) {
                        continue;
                    }
                    for (int j = 0; j < bookshelf.getNumRows(); j++) {
                        recentTiles.add(bookshelf.getSingleTile(j, i).getColor());
                    }

                    recentTiles = recentTiles.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    try {
                        if (confrontEqualsDifferentTiles(bookshelf.getNumRows() - recentTiles.size(), this.getType())) {
                            cAppearances++;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    recentTiles.clear();
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
        patternAppearances = cAppearances / this.getNumberOfPatternRepetitionsRequired();
        return patternAppearances;
    }

    private boolean confrontEqualsDifferentTiles(int numDiff, CheckType typeOfChecking) throws Exception{
        switch (typeOfChecking) {
            case EQUALS, DIFFERENT -> {
                if (numDiff == this.maxEqualsTiles) {
                    return true;
                }
            }
            case INDIFFERENT -> {
                if (numDiff >= this.maxEqualsTiles) {
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
