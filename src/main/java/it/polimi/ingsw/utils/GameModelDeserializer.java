package it.polimi.ingsw.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GameModelDeserializer implements JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonElement jsonElement, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Game game = new Gson().fromJson(jsonElement.getAsJsonObject(), Game.class);

        try {
            List<CommonGoal> commonGoals = new ArrayList<>();
            if (jsonElement.getAsJsonObject().get("commonGoals") != null) {
                for (JsonElement commonGoal : jsonElement.getAsJsonObject().get("commonGoals").getAsJsonArray()) {

                    JsonObject commonGoalAsJsonObject = commonGoal.getAsJsonObject();
                    
                    List<List<Integer>> pattern = new ArrayList<>();
                    for (JsonElement patternRow : commonGoalAsJsonObject.get("pattern").getAsJsonArray()) {
                        pattern.add(new ArrayList<>());
                        for (JsonElement patternRowElement : patternRow.getAsJsonArray()) {
                            pattern.get(pattern.size() - 1).add(patternRowElement.getAsInt());
                        }
                    }
                    
                    List<ScoreTile> scoreTiles = new ArrayList<>();
                    for (JsonElement scoreTile : commonGoalAsJsonObject.get("scoreTiles").getAsJsonArray()) {
                        JsonObject scoreTileAsJsonObject = scoreTile.getAsJsonObject();
                        int value = scoreTileAsJsonObject.get("value").getAsInt();
                        int playerID = scoreTileAsJsonObject.get("playerID").getAsInt();
                        int commonGoalID = scoreTileAsJsonObject.get("commonGoalID").getAsInt();
                        
                        scoreTiles.add(new ScoreTile(value, playerID, commonGoalID));
                    }

                    int id = commonGoalAsJsonObject.get("id").isJsonNull() ? -1 : commonGoalAsJsonObject.get("id").getAsInt();
                    int numberOfPatternRepetitionsRequired = commonGoalAsJsonObject.get("numberOfPatternRepetitionsRequired").getAsInt();
                    CheckType commonGoalType = CheckType.valueOf(commonGoalAsJsonObject.get("type").getAsString());
                    
                    switch (id) {
                        case 1 -> {
                            commonGoals.add(new TilesInPositionsPatternGoal(id, numberOfPatternRepetitionsRequired, commonGoalType, scoreTiles, pattern));
                        }
                        case 2 -> {
                            commonGoals.add(new MinEqualsTilesPattern(id, numberOfPatternRepetitionsRequired, CheckType.DIFFERENT, scoreTiles, Direction.VERTICAL, 0));
                        }
                        case 3 -> {
                            commonGoals.add(new ConsecutiveTilesPatternGoal(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles, 4));
                        }
                        case 4 -> {
                            commonGoals.add(new ConsecutiveTilesPatternGoal(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles, 2));
                        }
                        case 5 -> {
                            commonGoals.add(new MinEqualsTilesPattern(id, numberOfPatternRepetitionsRequired, CheckType.INDIFFERENT, scoreTiles, Direction.VERTICAL, 3));
                        }
                        case 6 -> {
                            commonGoals.add(new MinEqualsTilesPattern(id, numberOfPatternRepetitionsRequired, CheckType.DIFFERENT, scoreTiles, Direction.HORIZONTAL, 0));
                        }
                        case 7 -> {
                            commonGoals.add(new MinEqualsTilesPattern(id, numberOfPatternRepetitionsRequired, CheckType.INDIFFERENT, scoreTiles, Direction.HORIZONTAL, 2));
                        }
                        case 8 -> {
                            commonGoals.add(new FourCornersPatternGoal(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles));
                        }
                        case 9 -> {
                            commonGoals.add(new EightShapelessPatternGoal(id, numberOfPatternRepetitionsRequired, CheckType.INDIFFERENT, scoreTiles));
                        }
                        case 10 -> {
                            commonGoals.add(new DiagonalEqualPattern(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles, new int[][]{
                                    {1, 0, 1},
                                    {0, 1, 0},
                                    {1, 0, 1},
                            }));
                        }
                        case 11 -> {
                            commonGoals.add(new DiagonalEqualPattern(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles, new int[][]{
                                    {1, 0, 0, 0, 0},
                                    {0, 1, 0, 0, 0},
                                    {0, 0, 1, 0, 0},
                                    {0, 0, 0, 1, 0},
                                    {0, 0, 0, 0, 1},
                            }));
                        }
                        case 12 -> {
                            commonGoals.add(new StairPatternGoal(id, numberOfPatternRepetitionsRequired, CheckType.INDIFFERENT, scoreTiles));
                        }
                        default -> {
                            throw new Exception("This class does not exists");
                        }
                    }
                }
            }

            game.setCommonGoals(commonGoals);

        } catch (IllegalArgumentException ie) {
            System.out.println(ie.getMessage());
            System.out.println("Common goals cannot be serialized ..");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return game;
    }
}