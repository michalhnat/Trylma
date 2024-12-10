package com.michal.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

/**
 * A utility class for deserializing JSON strings into JsonObject instances.
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
     * @return the singleton instance of JsonDeserializer
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
     * @throws JsonSyntaxException if the JSON string is not valid
     */
    public JsonObject deserialize(String json) throws JsonSyntaxException {
        try {
            return gson.fromJson(json, JsonObject.class);
        } catch (JsonSyntaxException e) {
            throw new JsonSyntaxException("Failed to parse JSON: " + e.getMessage());
        }
    }

    /**
     * Extracts the command field from a JSON string.
     *
     * @param json the JSON string containing the command
     * @return the command as a string
     * @throws JsonSyntaxException if the JSON string is not valid or does not contain a command field
     */
    public String getCommand(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("command")) {
            throw new JsonSyntaxException("JSON missing 'command' field");
        }
        return obj.get("command").getAsString();
    }

    /**
     * Extracts the payload field from a JSON string.
     *
     * @param json the JSON string containing the payload
     * @return the payload as a JsonObject
     * @throws JsonSyntaxException if the JSON string is not valid
     */
    public JsonObject getPayload(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("payload")) {
            return new JsonObject(); // Empty payload is valid
        }
        return obj.get("payload").getAsJsonObject();
    }

    /**
     * Checks if the JSON string contains an error field set to true.
     *
     * @param json the JSON string to check
     * @return true if the JSON string contains an error field set to true, false otherwise
     * @throws JsonSyntaxException if the JSON string is not valid
     */
    public boolean isError(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        return obj.has("error") && obj.get("error").getAsBoolean();
    }

    /**
     * Extracts the error message from a JSON string.
     *
     * @param json the JSON string containing the error message
     * @return the error message as a string, or "Unknown error" if the message field is not present
     * @throws JsonSyntaxException if the JSON string is not valid
     */
    public String getErrorMessage(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("message")) {
            return "Unknown error";
        }
        return obj.get("message").getAsString();
    }
}