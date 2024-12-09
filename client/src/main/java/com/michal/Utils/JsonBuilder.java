package com.michal.Utils;

import com.google.gson.JsonObject;

public class JsonBuilder {
    private JsonObject jsonObject;
    private JsonObject payload;

    private JsonBuilder(String command) {
        jsonObject = new JsonObject();
        payload = new JsonObject();
        jsonObject.addProperty("command", command);
    }

    public static JsonBuilder setBuilder(String command) {
        return new JsonBuilder(command);
    }

    public JsonBuilder setArgument(String key, int value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonBuilder setArgument(String key, String value) {
        jsonObject.addProperty(key, value);
        return this;
    }

    public JsonBuilder setPayloadArgument(String key, int value) {
        payload.addProperty(key, value);
        return this;
    }

    public JsonBuilder setPayloadArgument(String key, String value) {
        payload.addProperty(key, value);
        return this;
    }

    public String build() {
        jsonObject.add("payload", payload);
        return jsonObject.toString();
    }
}