package com.michal.Utils;

import java.util.List;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.michal.Game.GameInfo;

public class JsonBuilder {
    private JsonObject jsonObject;
    private JsonObject payload;
    private JsonArray payloadArray;
    private boolean isArrayPayload;

    private JsonBuilder(String type) {
        jsonObject = new JsonObject();
        payload = new JsonObject();
        isArrayPayload = false;
        jsonObject.addProperty("type", type);
    }

    public static JsonBuilder setBuilder(String type) {
        return new JsonBuilder(type);
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

    public JsonBuilder setPayloadArray(List<JsonObject> items) {
        isArrayPayload = true;
        payloadArray = new JsonArray();
        items.forEach(payloadArray::add);
        return this;
    }

    public String build() {
        if (isArrayPayload) {
            jsonObject.add("payload", payloadArray);
        } else {
            jsonObject.add("payload", payload);
        }
        return jsonObject.toString();
    }
}
