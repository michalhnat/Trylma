package com.michal;

public interface PlayerCommunicator {
    void sendMessage(String message);

    void sendError(String message);
}
