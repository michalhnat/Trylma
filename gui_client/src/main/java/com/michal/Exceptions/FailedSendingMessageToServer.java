package com.michal.Exceptions;

public class FailedSendingMessageToServer extends Exception {
    public FailedSendingMessageToServer(String message) {
        super(message);
    }
}
