package model.commongoal;

import model.Bookshelf;
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

    public MinEqualsTilesPattern(String image, int patternRepetition, CheckType type, Direction direction, int maxEqualsTiles) {
        super(image, patternRepetition, type);
        this.direction = direction;
        this.maxEqualsTiles = maxEqualsTiles;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getMaxEqualsTiles() {
        return maxEqualsTiles;
    }

    public void setMaxEqualsTiles(int maxEqualsTiles) {
        this.maxEqualsTiles = maxEqualsTiles;
    }

    @Override
    public int goalPattern(Bookshelf b) {
        List<TileColor> recentTiles = new ArrayList<TileColor>();
        int cAppearances=0;
        switch (direction) {
            case HORIZONTAL -> {
                for (int i = 0; i < b.getNumRows(); i++) {
                    if (!b.isRowFull(i)) {
                        continue;
                    }
                    for (int j = 0; j < b.getNumColumns(); j++) {
                        recentTiles.add(b.getSingleTile(i, j).getColor());
                    }

                    recentTiles = recentTiles.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    try {
                        if (confrontEqualsDifferentTiles(b.getNumColumns() - recentTiles.size(), this.getType())) {
                            cAppearances++;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    recentTiles.clear();
                }
                if (cAppearances >= this.getPatternRepetition()) {
                    return 1;
                }
            }
            case VERTICAL -> {
                for (int i = 0; i < b.getNumColumns(); i++) {
                    if (!b.isColumnFull(i)) {
                        continue;
                    }
                    for (int j = 0; j < b.getNumRows(); j++) {
                        recentTiles.add(b.getSingleTile(j, i).getColor());
                    }

                    recentTiles = recentTiles.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    try {
                        if (confrontEqualsDifferentTiles(b.getNumRows() - recentTiles.size(), this.getType())) {
                            cAppearances++;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    recentTiles.clear();
                }
                if (cAppearances >= this.getPatternRepetition()) {
                    return 1;
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
        return 0;
    }

    private boolean confrontEqualsDifferentTiles(int numDiff, CheckType typeOfChecking) throws Exception{
        switch (typeOfChecking) {
            case DIFFERENT -> {
                if (numDiff == maxEqualsTiles) {
                    return true;
                }
            }
            case INDIFFERENT -> {
                if (numDiff >= maxEqualsTiles) {
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
