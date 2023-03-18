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
        switch(direction) {
            case HORIZONTAL:
                for(int i=0;i<b.getNumRows();i++) {
                    if(!b.isRowFull(i)) {
                        continue;
                    }
                    for(int j=0;j<b.getNumColumns();j++) {
                        recentTiles.add(b.getSingleTile(i,j).getColor());
                    }

                    recentTiles = recentTiles.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    switch(this.getType()) {
                        case DIFFERENT:
                            if((b.getNumColumns() - recentTiles.size()) >= maxEqualsTiles) {
                                cAppearances++;
                            }
                            break;
                    }

                }
                if(cAppearances>=this.getPatternRepetition()) {
                    return 1;
                }
                break;
            case VERTICAL:
                for(int i=0;i<b.getNumColumns();i++) {
                    if (!b.isColumnFull(i)) {
                        continue;
                    }
                    for (int j = 0; j < b.getNumRows(); j++) {
                        recentTiles.add(b.getSingleTile(i, j).getColor());
                    }

                    recentTiles = recentTiles.stream().distinct().collect(Collectors.toCollection(ArrayList::new));

                    if ((b.getNumRows() - recentTiles.size()) >= maxEqualsTiles) {
                        cAppearances++;
                    }
                }
                break;
        }
        return 0;
    }

    private boolean confrontEqualsDifferentTiles(int numDiff, CheckType typeOfChecking) {
        switch(typeOfChecking) {

        }
    }
}
