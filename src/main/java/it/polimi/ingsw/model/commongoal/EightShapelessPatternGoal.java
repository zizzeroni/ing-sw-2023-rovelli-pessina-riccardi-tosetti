package it.polimi.ingsw.model.commongoal;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.tile.Tile;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class EightShapelessPatternGoal extends CommonGoal {
    public EightShapelessPatternGoal() {
        super();
    }

    public EightShapelessPatternGoal(int imageID, int patternRepetition, CheckType type) {
        super(imageID, patternRepetition, type);
    }

    public EightShapelessPatternGoal(int imageID, int patternRepetition, CheckType type, int numberOfPlayers) {
        super(imageID, patternRepetition, type, numberOfPlayers);
    }

    @Override
    public int numberOfPatternRepetitionInBookshelf(Bookshelf bookshelf) {
        return Math.toIntExact(Arrays.stream(bookshelf.getTiles())                //Trasforma la bookshelf in uno Stream di ARRAY di tiles
                .flatMap(Arrays::stream)                                          //Trasforma la Stream di ARRAY di tiles, in una Stream di SOLI tiles
                .filter(Objects::nonNull)                                         //Filtro togliendo tutti gli elementi pari a NULL (che non posso successivamente raggr.)
                .collect(groupingBy(Tile::getColor, Collectors.counting()))       //Raggruppo le tile con "groupingBy" in una Map<TileColor, numOccorrenze> , dove uso la chiave TileColor specificando "Tile::GetColor", mentre per i valori il metodo "counting()"
                .entrySet().stream()                                              //Trasformo la Map in una Set e poi in una Stream
                .filter(x -> x.getValue() >= 8).count());                         //Filtro la Stream tenendo solamente i Color a cui sono associate più di 7
    }
}
