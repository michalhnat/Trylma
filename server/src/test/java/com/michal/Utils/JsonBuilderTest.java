package com.michal.Utils;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import java.util.Arrays;

public class JsonBuilderTest {

    @Test
    public void testSetBuilder() {
        JsonBuilder builder = JsonBuilder.setBuilder("testType");
        String json = builder.build();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        assertEquals("testType", jsonObject.get("type").getAsString());
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

    @Test
    public void testSetPayloadArray() {
        JsonObject item1 = new JsonObject();
        item1.addProperty("key1", "value1");
        JsonObject item2 = new JsonObject();
        item2.addProperty("key2", "value2");

        JsonBuilder builder = JsonBuilder.setBuilder("testType")
                .setPayloadArray(Arrays.asList(item1, item2));
        String json = builder.build();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
        assertTrue(jsonObject.get("payload").isJsonArray());
        assertEquals(2, jsonObject.get("payload").getAsJsonArray().size());
    }
}