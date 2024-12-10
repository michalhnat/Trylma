package com.michal.Utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonBuilderTest {

    @Test
    public void testSetBuilder() {
        JsonBuilder builder = JsonBuilder.setBuilder("testType");
        String json = builder.build();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        assertEquals("testType", jsonObject.get("command").getAsString());
    }

    @Test
    public void testSetArgument() {
        JsonBuilder builder = JsonBuilder.setBuilder("testType")
                .setArgument("key1", 123)
                .setArgument("key2", "value");
        String json = builder.build();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        assertEquals(123, jsonObject.get("key1").getAsInt());
        assertEquals("value", jsonObject.get("key2").getAsString());
    }

    @Test
    public void testSetPayloadArgument() {
        JsonBuilder builder = JsonBuilder.setBuilder("testType")
                .setPayloadArgument("payloadKey1", 456)
                .setPayloadArgument("payloadKey2", "payloadValue");
        String json = builder.build();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        JsonObject payload = jsonObject.get("payload").getAsJsonObject();
        assertEquals(456, payload.get("payloadKey1").getAsInt());
        assertEquals("payloadValue", payload.get("payloadKey2").getAsString());
    }
}