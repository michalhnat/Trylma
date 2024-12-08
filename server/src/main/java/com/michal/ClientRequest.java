package com.michal;

import com.google.gson.JsonObject;

import java.util.Map;

public class ClientRequest {
    private String command;
    private Map<String, String> arguments;

    public String getCommand() {
        return command;
    }

    public Map<String, String> getArguments() {
        return arguments;
    }
}
