package com.michal;

import com.google.gson.JsonObject;

public class JsonBuilder {
    private JsonObject jsonObject;

    private JsonBuilder(String command) {
        jsonObject = new JsonObject();
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

    public String build() {
        return jsonObject.toString();
    }
}