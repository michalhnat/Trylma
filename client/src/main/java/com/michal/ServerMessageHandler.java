package com.michal;

import java.io.ObjectInputStream;

public abstract class ServerMessageHandler implements Runnable {
    protected Display display;
    protected ObjectInputStream in;

    public ServerMessageHandler(Display display, ObjectInputStream in) {
        this.display = display;
        this.in = in;
    }

    public abstract void hanldeMessage(String message);

    public abstract void run();

}