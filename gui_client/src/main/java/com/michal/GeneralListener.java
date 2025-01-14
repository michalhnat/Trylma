package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import com.michal.Utils.JsonDeserializer;
import javafx.application.Platform;

public class GeneralListener implements Runnable {
    private IController controller;
    private ObjectInputStream in;
    private ICommunication communication;

    public GeneralListener(ICommunication communication) {
        this.communication = communication;
        this.in = communication.getInputStream();
    }

    public void setController(IController controller) {
        this.controller = controller;
    }

    public IController getController() {
        return this.controller;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) in.readObject();
                System.out.println("Recived: " + message);
                handleMessage(message);
            } catch (ClassNotFoundException e) {
                // System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Platform.runLater(() -> setErrorLabel("Server has disconnected"));
                break;
            }
        }
    }

    private void handleMessage(String message) {
        JsonDeserializer jsonDeserializer = JsonDeserializer.getInstance();
        switch (jsonDeserializer.getType(message)) {
            case "message":
                Platform.runLater(() -> setInfoLabel(jsonDeserializer.getMessage(message)));
                break;
            case "error":
                setErrorLabel(jsonDeserializer.getMessage(message));
                break;
            default:
                Platform.runLater(() -> controller.handleMessage(message));
                break;
        }
        // Platform.runLater(() -> controller.handleMessage(message));
    }

    public void setErrorLabel(String message) {
        Platform.runLater(() -> controller.showError(message));
    }

    public void setInfoLabel(String message) {
        Platform.runLater(() -> controller.showInfo(message));
    }

}
