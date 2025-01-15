package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import com.michal.Utils.JsonDeserializer;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class GameManager implements Runnable {
    private ObjectInputStream in;
    private ICommunication communication;
    private Label errorLabel;
    private Label infoLabel;
    private BorderPane borderPane;
    private Board board;


    public GameManager(ObjectInputStream in, ICommunication communication, Label errorLabel,
            Label infoLabel, Board board, BorderPane borderPane) {
        this.in = in;
        this.communication = communication;
        this.errorLabel = errorLabel;
        this.infoLabel = infoLabel;
        this.board = board;
        this.borderPane = borderPane;
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

    // private void createBoard(String map) {
    // Board board = new Board(15);
    // board.createBoardOutOfMap(map);
    // }

    private void drawBoard(String map) {
        Platform.runLater(() -> {
            board.editBoardOutOfMap(map);
        });
    }

    private void handleMessage(String message) {
        JsonDeserializer jsonDeserializer = JsonDeserializer.getInstance();
        switch (jsonDeserializer.getType(message)) {
            case "message":
                Platform.runLater(() -> setInfoLabel(jsonDeserializer.getMessage(message)));
                break;
            case "error":
                Platform.runLater(() -> setErrorLabel(jsonDeserializer.getMessage(message)));
                break;
            case "GameInfo":
                System.out.println(message);
                break;
            case "board":
                drawBoard(jsonDeserializer.getMessage(message));
                break;
        }
    }

}
