package com.michal;

import java.util.HashMap;
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

/**
 * Controller for the game board view. Handles game state, board visualization, and player moves.
 * Implements IController for message handling and BoardControllerMediator for board interactions.
 */
public class SecondaryController implements IController, BoardControllerMediator {
    /** Singleton instance of the controller */
    private static IController instance;

    // FXML Injected Components
    /** Pane containing the game board */
    @FXML
    private AnchorPane BoardPane;
    /** Main layout container */
    @FXML
    private BorderPane borderPane;
    /** Button to confirm move */
    @FXML
    private Button moveButton;
    /** Button to pass turn */
    @FXML
    private Button passButton;
    /** Start X coordinate input */
    @FXML
    private TextField x;
    /** Start Y coordinate input */
    @FXML
    private TextField y;
    /** Destination X coordinate input */
    @FXML
    private TextField destination_x;
    /** Destination Y coordinate input */
    @FXML
    private TextField destination_y;
    /** Status message display */
    @FXML
    private Label label;
    /** Game information list view */
    @FXML
    private ListView<Hboxtwolabel> info_list;

    /** Game board instance */
    private Board board;
    /** Communication interface for server interaction */
    private ICommunication communication;

    /**
     * Initializes the controller. Creates board and sets up communication.
     */
    @FXML
    public void initialize() {
        communication = App.getCommunication();
        instance = this;

        board = new Board(15, this);
    }

    /**
     * Returns singleton instance of the controller.
     * 
     * @return IController instance
     */
    public static IController getInstance() {
        return instance;
    }

    /**
     * Displays informational message with green color.
     * 
     * @param message Message to display
     */
    @Override
    public void showInfo(String message) {
        message = message.toUpperCase();
        label.setStyle("-fx-font-weight: bold");
        System.out.println("Info: " + message);
        label.setText(message);
        label.setTextFill(Color.GREEN);
    }

    /**
     * Displays error message with red color.
     * 
     * @param message Error message to display
     */
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
                if (gameInfo.get("players").equals("1")
                        && gameInfo.get("status").equals("IN_PROGRESS")) {
                    showError("No more players");
                    return;
                }
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

    /**
     * Handles move button click. Validates coordinates and sends move request to server.
     */
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

    /**
     * Handles pass button click. Sends pass turn request to server.
     */
    @FXML
    private void pass() {
        String jsonMessage = JsonBuilder.setBuilder("pass").build();

        try {
            communication.sendMessage(jsonMessage);
        } catch (Exception e) {
            showError("Failed to pass");
        }
    }

    /**
     * Updates game board based on server map data. Creates new board if empty, updates existing
     * board otherwise.
     * 
     * @param map String representation of board state
     */
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

    /**
     * Sets starting coordinates for move.
     * 
     * @param x Starting X coordinate
     * @param y Starting Y coordinate
     */
    @Override
    public void setStartXY(int x, int y) {
        this.x.setText(Integer.toString(x));
        this.y.setText(Integer.toString(y));
    }

    /**
     * Sets ending coordinates for move.
     * 
     * @param x Destination X coordinate
     * @param y Destination Y coordinate
     */
    @Override
    public void setEndXY(int x, int y) {
        this.destination_x.setText(Integer.toString(x));
        this.destination_y.setText(Integer.toString(y));
    }
}
