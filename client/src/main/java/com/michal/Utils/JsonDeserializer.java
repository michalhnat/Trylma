package com.michal.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class JsonDeserializer {
    private static final Gson gson = new Gson();
    private static JsonDeserializer instance;

    private JsonDeserializer() {
    }

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

    public String getCommand(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("command")) {
            throw new JsonSyntaxException("JSON missing 'command' field");
        }
        return obj.get("command").getAsString();
    }

    public JsonObject getPayload(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("payload")) {
            return new JsonObject(); // Empty payload is valid
        }
        return obj.get("payload").getAsJsonObject();
    }

    public boolean isError(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        return obj.has("error") && obj.get("error").getAsBoolean();
    }

    public String getErrorMessage(String json) throws JsonSyntaxException {
        JsonObject obj = deserialize(json);
        if (!obj.has("message")) {
            return "Unknown error";
        }
        return obj.get("message").getAsString();
    }
}