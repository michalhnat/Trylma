package com.michal.Utils;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonDeserializer {
    private static final Gson gson = new Gson();
    private static JsonDeserializer instance;

    private JsonDeserializer() {}

    public static JsonDeserializer getInstance() {
        if (instance == null) {
            synchronized (JsonDeserializer.class) {
                if (instance == null) {
                    instance = new JsonDeserializer();
                }
            }
        }
        return instance;
    }

    public JsonObject deserialize(String json) throws JsonSyntaxException {
        try {
            return gson.fromJson(json, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Failed to parse JSON: " + e.getMessage());
        }
    }

    public String getType(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("type")) {
            throw new JsonSyntaxException("JSON missing 'type' field");
        }
        return obj.get("type").getAsString();
    }

    public JsonObject getPayload(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("payload")) {
            return new JsonObject();
        }
        return obj.get("payload").getAsJsonObject();
    }

    public String getMessage(String json) throws JsonSyntaxException {
        JsonObject obj = getPayload(json);
        if (!obj.has("content")) {
            throw new JsonSyntaxException("JSON missing 'message' field");
        }
        return obj.get("content").getAsString();
    }

    public List<String> getGamesAsList(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        List<String> gamesList = new ArrayList<>();

        if (!obj.has("payload")) {
            return gamesList;
        }

        JsonArray games = obj.get("payload").getAsJsonArray();
        for (JsonElement game : games) {
            JsonObject gameObj = game.getAsJsonObject();
            String gameString = String.format("Game #%d (%d/%d players)",
                    gameObj.get("gameId").getAsInt(), gameObj.get("currentPlayers").getAsInt(),
                    gameObj.get("maxPlayers").getAsInt());
            gamesList.add(gameString);
        }

        return gamesList;
    }


    // public boolean isError(String json) throws JsonSyntaxException {
    // JsonObject obj = deserialize(json);
    // return obj.has("error") && obj.get("error").getAsBoolean();
    // }

    // public String getErrorMessage(String json) throws JsonSyntaxException {
    // JsonObject obj = deserialize(json);
    // if (!obj.has("message")) {
    // return "Unknown error";
    // }
    // return obj.get("message").getAsString();
    // }
}
