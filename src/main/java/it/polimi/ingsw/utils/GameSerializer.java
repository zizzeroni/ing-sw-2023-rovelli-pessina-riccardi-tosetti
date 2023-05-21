package it.polimi.ingsw.utils;

import com.google.gson.*;
import it.polimi.ingsw.model.Game;

import java.lang.reflect.Type;

public class GameSerializer implements JsonSerializer<Game> {

    public JsonElement serialize(Game game, Type type, JsonSerializationContext jsonSerializationContext)
    {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("numberOfPlayers", game.getNumberOfPlayersToStartGame());

        return jsonObject;
    }
}