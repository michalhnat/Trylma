package com.michal;

import java.util.Objects;

public interface Mediator {
    public void handleMessage(String msg, ClientHandler sender);
}
