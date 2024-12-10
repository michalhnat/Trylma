package com.michal.Utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

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
    public void testGetCommand() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"command\":\"testCommand\"}";
        String command = deserializer.getCommand(json);
        assertEquals("testCommand", command);
    }

    @Test
    public void testGetCommandMissing() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{}";
        assertThrows(JsonSyntaxException.class, () -> deserializer.getCommand(json));
    }

    @Test
    public void testGetPayload() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"payload\":{\"key\":\"value\"}}";
        JsonObject payload = deserializer.getPayload(json);
        assertEquals("value", payload.get("key").getAsString());
    }

    @Test
    public void testIsError() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"error\":true}";
        assertTrue(deserializer.isError(json));
    }

    @Test
    public void testGetErrorMessage() {
        JsonDeserializer deserializer = JsonDeserializer.getInstance();
        String json = "{\"message\":\"error occurred\"}";
        String errorMessage = deserializer.getErrorMessage(json);
        assertEquals("error occurred", errorMessage);
    }
}