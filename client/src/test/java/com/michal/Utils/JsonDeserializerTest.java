package com.michal.Utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.List;

public class JsonDeserializerTest {

    @Test
    public void testDeserialize() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"key\":\"value\"}";
        JsonObject jsonObject = deserializer.deserialize(json);
        assertEquals("value", jsonObject.get("key").getAsString());
    }

    @Test
    public void testDeserializeInvalidJson() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "invalid json";
        assertThrows(JsonSyntaxException.class, () -> deserializer.deserialize(json));
    }

    @Test
    public void testGetType() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"type\":\"testType\"}";
        String type = deserializer.getType(json);
        assertEquals("testType", type);
    }

    @Test
    public void testGetTypeMissing() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{}";
        assertThrows(JsonSyntaxException.class, () -> deserializer.getType(json));
    }

    @Test
    public void testGetPayload() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"payload\":{\"key\":\"value\"}}";
        JsonObject payload = deserializer.getPayload(json);
        assertEquals("value", payload.get("key").getAsString());
    }

    @Test
    public void testGetMessage() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"payload\":{\"content\":\"message\"}}";
        String message = deserializer.getMessage(json);
        assertEquals("message", message);
    }

    @Test
    public void testGetMessageMissing() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"payload\":{}}";
        assertThrows(JsonSyntaxException.class, () -> deserializer.getMessage(json));
    }

    // @Test
    // public void testGetGamesAsList() {
    //     JsonDeserializer deserializer = JsonDeserializer.getInstance();
    //     String json = "{\"payload\":[{\"gameId\":1,\"currentPlayers\":2,\"maxPlayers\":4}]}";
    //     List<String> games = deserializer.getGamesAsList(json);
    //     assertEquals(1, games.size());
    //     assertEquals("Game #1 (2/4 players)", games.getFirst());
    // }
}