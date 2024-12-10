package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import com.michal.Utils.JsonDeserializer;

public class MessageReceiver extends ServerMessageHandler {
    public MessageReceiver(Display display, ObjectInputStream in) {
        super(display, in);
    }

    public MessageReceiver() {}


    @Override
    public void hanldeMessage(String message) {
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

    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) in.readObject();
                hanldeMessage(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                // e.printStackTrace();
                display.displayMessage("Server has disconnected");
                break;
            }

        }
    }
}
