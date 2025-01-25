package com.michal;

import java.util.HashMap;
import com.michal.Utils.JsonBuilder;
import com.michal.Utils.JsonDeserializer;
import atlantafx.base.controls.Notification;
import atlantafx.base.theme.Styles;
import atlantafx.base.util.Animations;
import javafx.application.Platform;
import javafx.beans.binding.Binding;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private StackPane centerStackPane;
    @FXML
    private AnchorPane BoardPane;
    /** Main layout container */
    @FXML
    private BorderPane root;

    @FXML
    private AnchorPane notificationPane;
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
        BoardPane.setStyle("-fx-border-color: black; -fx-border-width: 2;");

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
        // Remove existing notifications
        notificationPane.getChildren().removeIf(child -> child instanceof Notification);

        var info = new Notification(message);
        info.getStyleClass().add(Styles.ELEVATED_1);
        info.getStyleClass().add(Styles.ACCENT);
        info.setOnClose(e -> {
            var fadeOut = Animations.fadeOut(info, Duration.seconds(0.5));
            fadeOut.setOnFinished(event -> notificationPane.getChildren().remove(info));
            fadeOut.playFromStart();
        });

        notificationPane.getChildren().add(info);

        System.out.println("Info: " + message);
    }
    // @Override
    // public void showInfo(String message) {
    // BoardPane.getChildren().removeIf(child -> child instanceof Notification);

    // var info = new Notification(message);
    // info.getStyleClass().add(Styles.ELEVATED_1);
    // info.getStyleClass().add(Styles.SUCCESS);
    // info.setOnClose(e -> {
    // var fadeOut = Animations.fadeOut(info, Duration.seconds(0.5));
    // fadeOut.setOnFinished(event -> BoardPane.getChildren().remove(info));
    // fadeOut.playFromStart();
    // });

    // // info_list.getItems()
    // BoardPane.getChildren().add(info);

    // System.out.println("Info: " + message);

    // // message = message.toUpperCase();
    // // label.setStyle("-fx-font-weight: bold");
    // // System.out.println("Info: " + message);
    // // label.setText(message);
    // // label.setTextFill(Color.GREEN);
    // }

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

                if (gameInfo.get("status").equals("FINISHED")) {
                    Stage popup = winningPopup(gameInfo.get("winner"));
                    popup.show();
                    return;
                }

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

            Group group = new Group();
            group.getChildren().addAll(board.getCells());

            // Add the group to the BoardPane
            BoardPane.getChildren().add(group);

            // Center the board using StackPane properties
            Platform.runLater(() -> {
                double boardWidth = BoardPane.getWidth();
                double boardHeight = BoardPane.getHeight();
                double groupWidth = group.getLayoutBounds().getWidth();
                double groupHeight = group.getLayoutBounds().getHeight();

                System.out.println("BoardPane.getWidth(): " + boardWidth);
                System.out.println("BoardPane.getHeight(): " + boardHeight);
                System.out.println("group.getLayoutBounds().getWidth(): " + groupWidth);
                System.out.println("group.getLayoutBounds().getHeight(): " + groupHeight);

                // group.setLayoutX((boardWidth - groupWidth) / 2);
                // group.setLayoutY((boardHeight - groupHeight) / 2);
            });
            // board.createBoardOutOfMap(map);

            // Group group = new Group();

            // System.out.println("BoardPane.getPrefWidth(): " + BoardPane.getPrefWidth());
            // System.out.println("BoardPane.getPrefHeight(): " + BoardPane.getPrefHeight());
            // System.out.println(
            // "group.getLayoutBounds().getWidth(): " + group.getLayoutBounds().getWidth());
            // System.out.println(
            // "group.getLayoutBounds().getHeight(): " + group.getLayoutBounds().getHeight());

            // group.setLayoutX((BoardPane.getPrefWidth() - group.getLayoutBounds().getWidth()) /
            // 2);
            // group.setLayoutY((BoardPane.getPrefHeight() - group.getLayoutBounds().getWidth()) /
            // 2);
            // group.getChildren().addAll(board.getCells());

            // BoardPane.getChildren().add(group);

            // group.layoutXProperty().bind(
            // Bindings.createDoubleBinding(
            // () -> (BoardPane.getWidth() - group.getLayoutBounds().getWidth()) / 2,
            // BoardPane.widthProperty(),
            // group.layoutBoundsProperty()
            // )
            // );

            // group.layoutYProperty().bind(
            // Bindings.createDoubleBinding(
            // () -> (anchorPane.getHeight() - group.getLayoutBounds().getHeight()) / 2,
            // anchorPane.heightProperty(),
            // group.layoutBoundsProperty()
            // )
            // );
            // board.getCells().forEach(cell -> {
            // BoardPane.getChildren().add(cell);
            // });
        } else {
            // System.out.println("Edit board");
            board.editBoardOutOfMap(map);
        }
    }

    private Stage winningPopup(String winners_color) {
        Stage stage = new Stage();
        Label label = new Label(winners_color + " wins!");
        label.setStyle(
                "-fx-font-size: 24px;" + "-fx-font-family: 'Arial';" + "-fx-text-fill: #333333;" +

                        "-fx-background-color: linear-gradient(to bottom, #f0f0f0, #d9d9d9);" +

                        "-fx-border-color: #b3b3b3;" + "-fx-border-radius: 10px;"
                        + "-fx-background-radius: 10px;" + "-fx-padding: 10px;"
                        + "-fx-alignment: center;");
        label.setAlignment(Pos.CENTER);

        // Fix the resource path:
        Image bgImage = new Image(
                getClass().getResource("/com/michal/graphics/victory.png").toExternalForm());
        BackgroundImage backgroundImage =
                new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER, new BackgroundSize(BackgroundSize.AUTO,
                                BackgroundSize.AUTO, false, false, true, false));

        VBox root = new VBox(label);
        root.setAlignment(Pos.TOP_CENTER);
        root.setBackground(new Background(backgroundImage));

        Scene scene = new Scene(root, 512, 512);
        stage.setScene(scene);
        stage.setTitle("Game Result");
        stage.show();
        return stage;
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
