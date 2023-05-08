package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.Tile;
import it.polimi.ingsw.model.view.CommonGoalView;
import it.polimi.ingsw.model.view.commongoal.EightShapelessPatternGoalView;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class EightShapelessPatternGoal extends CommonGoal {
    //Constructors
    public EightShapelessPatternGoal() {
        super();
    }

    public EightShapelessPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

    public EightShapelessPatternGoal(int imageID, int numberOfPatternRepetitionsRequired, CheckType type, int numberOfPlayers, int commonGoalID) {
        super(imageID, numberOfPatternRepetitionsRequired, type, numberOfPlayers, commonGoalID);
    }
    /*
    Control if there are 8 tiles of the same colour
    @param bookshelf contains the bookshelf of the player
    @return true if I found 8 same colour tiles, otherwise false
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
    /*
    @return an immutable copy of the common goal
    */
    @Override
    public CommonGoalView copyImmutable() {
        return new EightShapelessPatternGoalView(this);
    }
    /*
    Redefine the equals method
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof EightShapelessPatternGoal obj) {
            return this.getNumberOfPatternRepetitionsRequired() == obj.getNumberOfPatternRepetitionsRequired()
                    && this.getType() == obj.getType();
        }
        return false;
    }
}
