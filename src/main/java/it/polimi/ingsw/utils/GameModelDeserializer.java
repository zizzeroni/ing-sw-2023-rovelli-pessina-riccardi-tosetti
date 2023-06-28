package it.polimi.ingsw.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.commongoal.*;
import it.polimi.ingsw.model.tile.ScoreTile;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Deserializer used to enact the game model's deserialization.
 *
 * @see Game
 */
public class GameModelDeserializer implements JsonDeserializer<Game> {

    /**
     * Reads the json game's files and deserialize it into a Game's instance.
     *
     * @param jsonElement the json of the current element.
     * @param type the element's type.
     * @param jsonDeserializationContext the context in which the json is being deserialized.
     * @return the deserialized game.
     * @throws JsonParseException occurs in case of an error in jason's parsing.
     */
    @Override
    public Game deserialize(JsonElement jsonElement, Type type,
                            JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Gson gson = new Gson();
        JsonElement commonGoalsAsJsonElement = gson.fromJson(jsonElement.toString(), JsonElement.class).getAsJsonObject().remove("commonGoals");
        jsonElement.getAsJsonObject().remove("commonGoals");

        Game game = gson.fromJson(jsonElement, Game.class);

        try {
            List<CommonGoal> commonGoals = new ArrayList<>();
            if (commonGoalsAsJsonElement != null) {
                for (JsonElement commonGoal : commonGoalsAsJsonElement.getAsJsonArray()) {

                    JsonObject commonGoalAsJsonObject = commonGoal.getAsJsonObject();

                    List<List<Integer>> pattern = new ArrayList<>();
                    JsonElement patternAsJsonElement = commonGoalAsJsonObject.get("pattern");

                    if (patternAsJsonElement != null) {
                        for (JsonElement patternRow : patternAsJsonElement.getAsJsonArray()) {
                            pattern.add(new ArrayList<>());
                            for (JsonElement patternRowElement : patternRow.getAsJsonArray()) {
                                pattern.get(pattern.size() - 1).add(patternRowElement.getAsInt());
                            }
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

                    int id = commonGoalAsJsonObject.get("id") == null ? -1 : commonGoalAsJsonObject.get("id").getAsInt();
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
                            commonGoals.add(new DiagonalEqualPattern(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles, pattern));
                        }
                        case 11 -> {
                            commonGoals.add(new DiagonalEqualPattern(id, numberOfPatternRepetitionsRequired, CheckType.EQUALS, scoreTiles, pattern));
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
            System.err.println("Error while serializing: Common goals cannot be serialized ..., "+ ie.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return game;
    }
}