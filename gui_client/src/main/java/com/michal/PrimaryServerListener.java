package com.michal;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.michal.Utils.JsonBuilder;
import com.michal.Utils.JsonDeserializer;

public class PrimaryServerListener implements Runnable {
    private Label errorLabel;
    private Label infoLabel;
    private ListView<HboxCell> listView;
    // private Socket socket;
    private ObjectInputStream in;
    private ICommunication communication;

    public PrimaryServerListener(Label errorLabel, Label infoLabel, ListView<HboxCell> listView, ICommunication communication) {
        this.errorLabel = errorLabel;
        this.infoLabel = infoLabel;
        this.listView = listView;
        this.in = communication.getInputStream();
        this.communication = communication;
        // try {
        //     this.in = new ObjectInputStream(socket.getInputStream());
        // } catch (Exception e) {
        //     Platform.runLater(() -> errorLabel.setText("Error initializing input stream: " + e.getMessage()));
        // }
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) in.readObject();
                handleMessage(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Platform.runLater(() -> errorLabel.setText("Server has disconnected"));
                break;
            }
        }
    }

    private void setInfoLabel(String message) {
        Platform.runLater(() -> infoLabel.setText(message));
    }

    private void setErrorLabel(String message) {
        Platform.runLater(() -> errorLabel.setText(message));
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
            case "list":
                List<String> games = jsonDeserializer.getGamesAsList(message);
                List<HboxCell> cells = createCells(games);
                Platform.runLater(() -> {
                    listView.getItems().clear();
                    listView.getItems().addAll(cells);
                });
                break;
            default:
                setErrorLabel("Unknown message type: " + jsonDeserializer.getType(message));
                break;
        }
    }

    public List<HboxCell> createCells(List<String> labels) {
        List<HboxCell> cells = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            Button button = new Button();
            button.setText("Join");
            int gameID = extractGameID(labels.get(i));
            button.setOnAction(e -> {
                try {
                    if (gameID == -1) {
                        setErrorLabel("Failed to join game");
                        return;
                    }
                    String jsonMessage = JsonBuilder.setBuilder("join")
                                    .setPayloadArgument("gameID", String.valueOf(gameID))
                                    .build();
                    communication.sendMessage(jsonMessage);
                    App.setRoot("secondary");
                } catch (Exception ex) {
                    setErrorLabel("Failed to join game");
                }
            });
            cells.add(new HboxCell(labels.get(i), button));
        }
        return cells;
    }

    private int extractGameID(String label) {
        try {
            int hashIndex = label.indexOf('#');
            int spaceIndex = label.indexOf(' ', hashIndex);
            if (hashIndex != -1 && spaceIndex != -1) {
                String idStr = label.substring(hashIndex + 1, spaceIndex);
                return Integer.parseInt(idStr);
            }
        } catch (NumberFormatException e) {
            // Handle parsing error
        }
        return -1;
    }
}
