package com.michal.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * Utility class for deserializing JSON strings into Java objects.
 */
public class JsonDeserializer {
    private static final Gson gson = new Gson();
    private static JsonDeserializer instance;

    /**
     * Private constructor to prevent instantiation.
     */
    private JsonDeserializer() {}

    /**
     * Returns the singleton instance of JsonDeserializer.
     *
     * @return the singleton instance
     */
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

    /**
     * Deserializes a JSON string into a JsonObject.
     *
     * @param json the JSON string to deserialize
     * @return the deserialized JsonObject
     * @throws JsonSyntaxException if the JSON is not valid
     */
    public JsonObject deserialize(String json) throws JsonSyntaxException {
        try {
            return gson.fromJson(json, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Failed to parse JSON: " + e.getMessage());
        }
    }

    /**
     * Extracts the type field from a JSON string.
     *
     * @param json the JSON string to extract the type from
     * @return the type as a string
     * @throws JsonSyntaxException if the JSON is not valid or missing the type field
     */
    public String getType(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("type")) {
            throw new JsonSyntaxException("JSON missing 'type' field");
        }
        return obj.get("type").getAsString();
    }

    /**
     * Extracts the payload field from a JSON string.
     *
     * @param json the JSON string to extract the payload from
     * @return the payload as a JsonObject
     * @throws JsonSyntaxException if the JSON is not valid
     */
    public JsonObject getPayload(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("payload")) {
            return new JsonObject();
        }
        return obj.get("payload").getAsJsonObject();
    }

    /**
     * Extracts the message content from the payload of a JSON string.
     *
     * @param json the JSON string to extract the message from
     * @return the message content as a string
     * @throws JsonSyntaxException if the JSON is not valid or missing the content field
     */
    public String getMessage(String json) throws JsonSyntaxException {
        JsonObject obj = getPayload(json);
        if (!obj.has("content")) {
            throw new JsonSyntaxException("JSON missing 'message' field");
        }
        return obj.get("content").getAsString();
    }

    /**
     * Extracts a list of games from the payload of a JSON string.
     *
     * @param json the JSON string to extract the games from
     * @return a list of games as strings
     * @throws JsonSyntaxException if the JSON is not valid
     */
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

    /**
     * Extracts a list of save games from the payload of a JSON string.
     *
     * @param json the JSON string to extract the saves from
     * @return a list of string arrays where each array contains [id, board]
     * @throws JsonSyntaxException if the JSON is not valid
     */
    public List<String[]> getSavesAsList(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        List<String[]> savesList = new ArrayList<>();

        if (!obj.has("payload")) {
            return savesList;
        }

        JsonArray saves = obj.get("payload").getAsJsonArray();
        for (JsonElement save : saves) {
            JsonObject saveObj = save.getAsJsonObject();

            String[] gameString = new String[2];

            gameString[0] = String.format("%d", saveObj.get("id").getAsInt());
            gameString[1] = saveObj.get("board").getAsString();

            savesList.add(gameString);
        }

        return savesList;
    }

    /**
     * Extracts the move history from a game board JSON string.
     *
     * @param json the JSON string containing move history
     * @return a map of move numbers to board states
     * @throws JsonSyntaxException if the JSON is not valid or not of type "loaded_boards"
     */
    public HashMap<Integer, String> getMovesHistory(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        HashMap<Integer, String> movesHistory = new HashMap<>();

        String type = getType(json);
        if (!type.equals("loaded_boards")) {
            System.out.println("Invalid message type: " + type);
            return movesHistory;
        }
        if (!obj.has("payload")) {
            return movesHistory;
        }

        JsonArray movesArray = obj.get("payload").getAsJsonArray();
        for (JsonElement element : movesArray) {
            if (element.isJsonObject()) {
                JsonObject moveObj = element.getAsJsonObject();
                Integer number = Integer.valueOf(moveObj.get("number").getAsString());
                String board = moveObj.get("board").getAsString();
                movesHistory.put(number, board);
            }
        }
        return movesHistory;
    }

    /**
     * Extracts game information from the payload of a JSON string.
     *
     * @param json the JSON string containing game information
     * @return a map of game information keys to their values
     * @throws JsonSyntaxException if the JSON is not valid
     */
    public HashMap<String, String> getGameInfoMap(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        HashMap<String, String> gameInfo = new HashMap<>();

        if (!obj.has("payload")) {
            return gameInfo;
        }

        JsonObject payload = obj.get("payload").getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : payload.entrySet()) {
            String key = entry.getKey();
            JsonElement element = entry.getValue();
            if (element.isJsonPrimitive()) {
                String value = element.getAsString();
                gameInfo.put(key, value);
            }
        }

        return gameInfo;
    }
}
