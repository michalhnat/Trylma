package com.michal;

import java.util.HashMap;
import java.util.List;
import com.michal.Utils.JsonBuilder;
import com.michal.Utils.JsonDeserializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class SecondaryController implements IController, BoardControllerMediator {
    private static IController instance;

    @FXML
    private AnchorPane BoardPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button moveButton;

    @FXML
    private Button passButton;

    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private TextField destination_x;

    @FXML
    private TextField destination_y;

    @FXML
    private Label label;

    @FXML
    private ListView<Hboxtwolabel> info_list;

    private Board board;



    private ICommunication communication;

    @FXML
    public void initialize() {
        communication = App.getCommunication();
        instance = this;

        board = new Board(15, this);

    }

    public static IController getInstance() {
        return instance;

    }

    @Override
    public void showInfo(String message) {
        message = message.toUpperCase();
        label.setStyle("-fx-font-weight: bold");
        System.out.println("Info: " + message);
        label.setText(message);
        label.setTextFill(Color.GREEN);
    }

    @Override
    public void showError(String message) {
        message = message.toUpperCase();
        label.setStyle("-fx-font-weight: bold");
        label.setText(message);
        label.setTextFill(Color.RED);
        System.out.println("Error: " + message);
    }

    @Override
    public void handleMessage(String message) {
        // System.out.println("Message: " + message);
        JsonDeserializer jsonDeserializer = JsonDeserializer.getInstance();
        switch (jsonDeserializer.getType(message)) {
            case "board":
                String map = jsonDeserializer.getMessage(message);
                System.out.println(map);
                updateBoard(jsonDeserializer.getMessage(message));
                break;
            case "gameInfo":
                HashMap<String, String> gameInfo = jsonDeserializer.getGameInfoMap(message);
                info_list.getItems().clear();
                gameInfo.forEach((key, value) -> {
                    Hboxtwolabel hbox = new Hboxtwolabel(key, value);
                    info_list.getItems().add(hbox);
                });
                break;
            default:
                showError("Unknown message type: " + jsonDeserializer.getType(message));
                break;
        }
    }

    @FXML
    private void move() {
        int x = Integer.parseInt(this.x.getText());
        int y = Integer.parseInt(this.y.getText());
        int destination_x = Integer.parseInt(this.destination_x.getText());
        int destination_y = Integer.parseInt(this.destination_y.getText());

        if (x < 0 || y < 0 || destination_x < 0 || destination_y < 0) {
            showError("Invalid coordinates");
            return;
        }

        String jsonMessage = JsonBuilder.setBuilder("move").setPayloadArgument("start_x", x)
                .setPayloadArgument("start_y", y).setPayloadArgument("end_x", destination_x)
                .setPayloadArgument("end_y", destination_y).build();
        try {
            communication.sendMessage(jsonMessage);
            board.restet_border_on_active_cells();
        } catch (Exception e) {
            showError("Failed to move");
        }
    }

    @FXML
    private void pass() {
        String jsonMessage = JsonBuilder.setBuilder("pass").build();

        try {
            communication.sendMessage(jsonMessage);
        } catch (Exception e) {
            showError("Failed to pass");
        }
    }

    private void updateBoard(String map) {
        if (board.isEmpty()) {
            board.createBoardOutOfMap(map);
            board.getCells().forEach(cell -> {
                BoardPane.getChildren().add(cell);
            });
        } else {
            // System.out.println("Edit board");
            board.editBoardOutOfMap(map);
        }
    }

    @Override
    public void setStartXY(int x, int y) {
        this.x.setText(Integer.toString(x));
        this.y.setText(Integer.toString(y));
    }

    @Override
    public void setEndXY(int x, int y) {
        this.destination_x.setText(Integer.toString(x));
        this.destination_y.setText(Integer.toString(y));
    }

}
