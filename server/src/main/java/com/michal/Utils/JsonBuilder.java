package com.michal.Utils;

import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.michal.Game.GameInfo;

/**
 * A utility class for building JSON objects with a specified type and payload.
 */
public class JsonBuilder {
    private JsonObject jsonObject;
    private JsonObject payload;
    private JsonArray payloadArray;
    private boolean isArrayPayload;

    /**
     * Constructs a JsonBuilder with the specified type.
     *
     * @param type the type of the JSON object
     */
    private JsonBuilder(String type) {
        jsonObject = new JsonObject();
        payload = new JsonObject();
        isArrayPayload = false;
        jsonObject.addProperty("type", type);
    }

    /**
     * Creates a new JsonBuilder with the specified type.
     *
     * @param type the type of the JSON object
     * @return a new JsonBuilder instance
     */
    public static JsonBuilder setBuilder(String type) {
        return new JsonBuilder(type);
    }

    /**
     * Adds an integer argument to the JSON object.
     *
     * @param key the key of the argument
     * @param value the integer value of the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setArgument(String key, int value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Adds a string argument to the JSON object.
     *
     * @param key the key of the argument
     * @param value the string value of the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setArgument(String key, String value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    /**
     * Adds an integer argument to the payload of the JSON object.
     *
     * @param key the key of the argument
     * @param value the integer value of the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setPayloadArgument(String key, int value) {
        payload.addProperty(key, value);
        return this;
    }

    /**
     * Adds a string argument to the payload of the JSON object.
     *
     * @param key the key of the argument
     * @param value the string value of the argument
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setPayloadArgument(String key, String value) {
        payload.addProperty(key, value);
        return this;
    }

    /**
     * Sets the payload of the JSON object to an array of JSON objects.
     *
     * @param items the list of JSON objects to set as the payload
     * @return the current JsonBuilder instance
     */
    public JsonBuilder setPayloadArray(List<JsonObject> items) {
        isArrayPayload = true;
        payloadArray = new JsonArray();
        items.forEach(payloadArray::add);
        return this;
    }

    /**
     * Builds the JSON object and returns it as a string.
     *
     * @return the JSON object as a string
     */
    public String build() {
        if (isArrayPayload) {
            jsonObject.add("payload", payloadArray);
        } else {
            jsonObject.add("payload", payload);
        }
        return jsonObject.toString();
    }
}