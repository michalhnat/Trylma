package com.michal;

import java.io.ObjectInputStream;

/**
 * Abstract class that handles server messages and implements the Runnable interface.
 */
public abstract class ServerMessageHandler implements Runnable {
    protected Display display;
    protected ObjectInputStream in;

    /**
     * Constructs a new ServerMessageHandler instance with the specified display and input stream.
     *
     * @param display the display interface to use
     * @param in the ObjectInputStream to read messages from
     */
    public ServerMessageHandler(Display display, ObjectInputStream in) {
        this.display = display;
        this.in = in;
    }

    /**
     * Default constructor for ServerMessageHandler.
     */
    public ServerMessageHandler() {
    }

    /**
     * Sets the display interface.
     *
     * @param display the display interface to set
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * Sets the ObjectInputStream.
     *
     * @param in the ObjectInputStream to set
     */
    public void setObjectInputStream(ObjectInputStream in) {
        this.in = in;
    }

    /**
     * Handles the received message.
     *
     * @param message the message received from the server
     */
    public abstract void handleMessage(String message);

    /**
     * Continuously reads messages from the input stream and handles them.
     */
    public abstract void run();
}