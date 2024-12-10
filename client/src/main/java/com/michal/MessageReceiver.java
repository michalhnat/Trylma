package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import com.michal.Utils.JsonDeserializer;

/**
 * MessageReceiver class that handles receiving and processing messages from the server.
 */
public class MessageReceiver extends ServerMessageHandler {

    /**
     * Constructs a new MessageReceiver instance with the specified display and input stream.
     *
     * @param display the display interface to use
     * @param in the ObjectInputStream to read messages from
     */
    public MessageReceiver(Display display, ObjectInputStream in) {
        super(display, in);
    }

    /**
     * Default constructor for MessageReceiver.
     */
    public MessageReceiver() {}

    /**
     * Handles the received message by displaying it or processing it based on its type.
     *
     * @param message the message received from the server
     */
    @Override
    public void handleMessage(String message) {
        display.displayMessage("\u001B[32mMessage from server:\u001B[0m");
        JsonDeserializer jsonDeserializer = JsonDeserializer.getInstance();
        switch (jsonDeserializer.getType(message)) {
            case "message":
                display.displayMessage(jsonDeserializer.getMessage(message));
                break;
            case "error":
                display.displayError(jsonDeserializer.getMessage(message));
                break;
            case "list":
                List<String> games = jsonDeserializer.getGamesAsList(message);
                display.displayMessage("Available games:");
                for (String game : games) {
                    display.displayMessage(game);
                }
                break;
        }
        display.displayMessage(">", false);
    }

    /**
     * Continuously reads messages from the input stream and handles them.
     */
    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) in.readObject();
                handleMessage(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                display.displayMessage("Server has disconnected");
                break;
            }
        }
    }
}