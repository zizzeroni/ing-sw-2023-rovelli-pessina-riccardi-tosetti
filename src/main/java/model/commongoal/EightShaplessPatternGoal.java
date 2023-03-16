package model.commongoal;


import model.Bookshelf;
import model.tile.Tile;
import model.tile.TileColor;

import java.awt.print.Book;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class EightShaplessPatternGoal extends CommonGoal {
    public EightShaplessPatternGoal() {
    }

    public EightShaplessPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public int goalPattern(Bookshelf b) {
        Map<TileColor,List<Tile>> prova = Arrays.stream(b.getTiles()).flatMap(rows -> Arrays.stream(rows)).collect(groupingBy(Tile::getColor,mapping(tile->tile,toList())));
        return prova.entrySet().stream().filter(x->x.getValue().size()>=8).count()>0 ? 1 : 0;
    }
}
