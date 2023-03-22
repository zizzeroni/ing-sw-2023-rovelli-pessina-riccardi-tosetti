package model.commongoal;


import model.Bookshelf;
import model.tile.GoalTile;
import model.tile.Tile;
import model.tile.TileColor;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class EightShaplessPatternGoal extends CommonGoal {
    public EightShaplessPatternGoal() {
        super();
    }

    public EightShaplessPatternGoal(String image, int patternRepetition, CheckType type) {
        super(image, patternRepetition, type);
    }

    public EightShaplessPatternGoal(String image, int patternRepetition, CheckType type, GoalTile[] scoreTiles) {
        super(image, patternRepetition, type, scoreTiles);
    }

    @Override
    public int goalPattern(Bookshelf b) {
        return Math.toIntExact(Arrays.stream(b.getTiles())                              //Trasforma la bookshelf in uno Stream di ARRAY di tiles
                .flatMap(Arrays::stream)                                                //Trasforma la Stream di ARRAY di tiles, in una Stream di SOLI tiles
                .filter(Objects::nonNull)                                               //Filtro togliendo tutti gli elementi pari a NULL (che non posso successivamente raggr.)
                .collect(groupingBy(Tile::getColor, Collectors.counting()))             //Raggruppo le tile con "groupingBy" in una Map<TileColor, numOccorrenze> , dove uso la chiave TileColor specificando "Tile::GetColor", mentre per i valori il metodo "counting()"
                .entrySet().stream()                                                    //Trasformo la Map in una Set e poi in una Stream
                .filter(x->x.getValue()>=8).count());                                   //Filtro la Stream tenendo solamente i Color a cui sono associate più di 7
    }
}
