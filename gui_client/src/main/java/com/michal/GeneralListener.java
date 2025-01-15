package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import com.michal.Utils.JsonDeserializer;
import javafx.application.Platform;

/**
 * A general listener class that handles incoming messages in a separate thread. This class is
 * responsible for receiving and processing messages from a communication stream, and updating the
 * UI accordingly through a controller.
 */
public class GeneralListener implements Runnable {
    private IController controller;
    private ObjectInputStream in;
    private ICommunication communication;

    /**
     * Constructs a new GeneralListener with the specified communication interface.
     * 
     * @param communication The communication interface to listen on
     */
    public GeneralListener(ICommunication communication) {
        this.communication = communication;
        this.in = communication.getInputStream();
    }

    /**
     * Sets the controller for this listener.
     * 
     * @param controller The controller to be used for handling UI updates
     */
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * Gets the current controller.
     * 
     * @return The current controller instance
     */
    public IController getController() {
        return this.controller;
    }

    /**
     * Runs the listener thread. Continuously reads messages from the input stream and processes
     * them using the handleMessage method. If an error occurs during reading, it will show an error
     * message and terminate the loop.
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
                e.printStackTrace();
                Platform.runLater(() -> setErrorLabel("Server has disconnected"));
                break;
            }
        }
    }

    /**
     * Handles incoming messages based on their type. Uses JsonDeserializer to determine the message
     * type and processes accordingly: - "message": Shows info message - "error": Shows error
     * message - default: Passes message to controller
     * 
     * @param message The message to be handled
     */
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
    }

    /**
     * Sets an error message to be displayed in the UI. This method ensures the UI update happens on
     * the JavaFX Application Thread.
     * 
     * @param message The error message to be displayed
     */
    public void setErrorLabel(String message) {
        Platform.runLater(() -> controller.showError(message));
    }

    /**
     * Sets an info message to be displayed in the UI. This method ensures the UI update happens on
     * the JavaFX Application Thread.
     * 
     * @param message The info message to be displayed
     */
    public void setInfoLabel(String message) {
        Platform.runLater(() -> controller.showInfo(message));
    }
}
