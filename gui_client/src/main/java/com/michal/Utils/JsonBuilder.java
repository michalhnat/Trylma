package com.michal.Utils;

import com.google.gson.JsonObject;

/**
 * Utility class for building JSON objects with commands and payloads.
 */
public class JsonBuilder {
    private JsonObject jsonObject;
    private JsonObject payload;

    /**
     * Private constructor to initialize the JSON builder with a command.
     *
     * @param command the command to set in the JSON object
     */
    private JsonBuilder(String command) {
        jsonObject = new JsonObject();
        payload = new JsonObject();
        jsonObject.addProperty("command", command);
    }

    /**
     * Static method to create a new JsonBuilder instance with the specified command.
     *
     * @param command the command to set in the JSON object
     * @return a new JsonBuilder instance
     */
    public static JsonBuilder setBuilder(String command) {
        return new JsonBuilder(command);
    }

    /**
     * Sets an argument in the JSON object with an integer value.
     *
     * @param key the key for the argument
     * @param value the integer value for the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setArgument(String key, int value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Sets an argument in the JSON object with a string value.
     *
     * @param key the key for the argument
     * @param value the string value for the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setArgument(String key, String value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Sets an argument in the payload JSON object with an integer value.
     *
     * @param key the key for the argument
     * @param value the integer value for the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setPayloadArgument(String key, int value) {
        payload.addProperty(key, value);
        return this;
    }

    /**
     * Sets an argument in the payload JSON object with a string value.
     *
     * @param key the key for the argument
     * @param value the string value for the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setPayloadArgument(String key, String value) {
        payload.addProperty(key, value);
        return this;
    }

    /**
     * Builds the final JSON object by adding the payload to it.
     *
     * @return the JSON object as a string
     */
    public String build() {
        jsonObject.add("payload", payload);
        return jsonObject.toString();
    }
}