package com.michal;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Utils.JsonBuilder;
import com.michal.Utils.JsonDeserializer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Primary controller for the main application window. Handles server connection, game listing, and
 * game creation functionality. Implements IController interface for message handling and UI
 * updates.
 */
public class PrimaryController implements IController {
    /** Singleton instance of the controller */
    private static IController instance;

    /** Communication interface for server interaction */
    private ICommunication communication;

    /** Listener for server messages */
    private GeneralListener listener;

    // FXML Injected Components
    /** Text field for server IP address input */
    @FXML
    private TextField ip_tf;
    /** Text field for server port input */
    @FXML
    private TextField port_tf;
    /** Button to initiate server connection */
    @FXML
    private Button connect_btn;
    /** Label for displaying information messages */
    @FXML
    private Label info_label;
    /** Label for games section */
    @FXML
    private Label games_label;
    /** List view for displaying available games */
    @FXML
    private ListView<HboxCell> games_list;
    /** Button to refresh games list */
    @FXML
    private Button refresh_button;
    /** Button to create new game */
    @FXML
    private Button create_button;
    /** Choice box for selecting game layout */
    @FXML
    private ChoiceBox<String> layout_cb;
    /** Choice box for selecting game variant */
    @FXML
    private ChoiceBox<String> variant_cb;
    /** Text field for board size input */
    @FXML
    private TextField boardSize_tf;

    /**
     * Initializes the controller. Called automatically after FXML loading.
     */
    @FXML
    private void initialize() {
        communication = App.getCommunication();
        instance = this;
    }

    /**
     * Handles connection to the game server. Validates IP and port inputs before attempting
     * connection.
     *
     * @throws IOException If connection fails
     */
    @FXML
    private void connectToServer() throws IOException {
        String ip = ip_tf.getText();
        int port = Integer.parseInt(port_tf.getText());

        if (ip == null || ip.isEmpty() || port == 0 || port < 0 || port > 65535) {
            info_label.setText("Invalid IP or port");
            return;
        }

        try {
            InetAddress address = InetAddress.getByName(ip);
            App.getCommunication().connectToServer(address, port);
            info_label.setText("Connected to the server.");

            try {
                listener = new GeneralListener(communication);
                App.setGeneralListener(listener);
                App.getGeneralListener().setController(this);

                Thread listenerThread = new Thread(listener);
                listenerThread.start();

                request_games_list();
            } catch (Exception e) {
                info_label.setText("Failed to start listener");
            }
        } catch (FailedConnectingToServerException e) {
            info_label.setText("Failed to connect to the server.");
        }

        request_games_list();

    }

    /**
     * Requests list of available games from server.
     */
    @FXML
    private void request_games_list() {
        String jsonMessage = JsonBuilder.setBuilder("list").build();
        try {
            App.getCommunication().sendMessage(jsonMessage);
        } catch (Exception e) {
            info_label.setText("Failed to list games");

        }
    }

    /**
     * Creates a new game with specified parameters. Validates layout, variant and board size before
     * creation.
     */
    @FXML
    private void create_game() {
        try {
            String layout = layout_cb.getValue().toString();
            String variant = variant_cb.getValue().toString();
            int boardSize = Integer.parseInt(boardSize_tf.getText());

            if (layout == null || layout.isEmpty() || variant == null || variant.isEmpty()
                    || boardSize == 0 || boardSize < 0) {
                throw new Exception("Invalid layout, variant or board size");
            }

            String jsonMessage = JsonBuilder.setBuilder("create")
                    .setPayloadArgument("layout", layout).setPayloadArgument("variant", variant)
                    .setPayloadArgument("boardSize", boardSize).build();

            try {
                App.getCommunication().sendMessage(jsonMessage);
                request_games_list();
            } catch (Exception e) {
                info_label.setText("Failed to create game");
            }
        } catch (Exception e) {
            info_label.setText(e.getMessage());
        }

    }

    @FXML
    private void read_save() {
        try {
            String jsonMessage = JsonBuilder.setBuilder("list_saves").build();
            App.getCommunication().sendMessage(jsonMessage);
            // Stage stage = save_window();


            // stage.show();
        } catch (Exception e) {
            info_label.setText("Failed to save game");
        }
    }

    private Stage save_window(List<String[]> saves) {

        HashMap<String, String> idBoard_map = new HashMap<>();


        saves.forEach(save -> {
            idBoard_map.put(save[0], save[1]);
        });

        Stage stage = new Stage();
        stage.setTitle("Saved game");
        stage.initModality(Modality.APPLICATION_MODAL);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        VBox game_preview = new VBox();
        game_preview.setAlignment(Pos.CENTER);
        vbox.getChildren().add(game_preview);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 10, 10, 10));



        ListView<HboxCell> saves_list = new ListView<>();

        saves.forEach(save -> {
            Button button = new Button();
            button.setText("Load");

            button.setOnAction(e -> {
                try {
                    String jsonMessage = JsonBuilder.setBuilder("load_game")
                            .setPayloadArgument("saveID", String.valueOf(save[0])).build();
                    communication.sendMessage(jsonMessage);
                    stage.close();
                    request_games_list();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showError(("Failed to read game"));
                }
            });
            saves_list.getItems().add(new HboxCell("Game #" + save[0], button));
        });

        saves_list.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<HboxCell>() {
                    @Override
                    public void changed(ObservableValue<? extends HboxCell> observable,
                            HboxCell oldValue, HboxCell newValue) {
                        game_preview.getChildren().clear();

                        String map = idBoard_map.get(newValue.getLabel().getText());
                        Board board = new Board(10);

                        board.disactivate_all_cells();
                        board.createBoardOutOfMap(map);

                        Group group = new Group();
                        group.getChildren().addAll(board.getCells());

                        StackPane centeredPreview = new StackPane();
                        centeredPreview.getChildren().add(group);
                        StackPane.setAlignment(group, Pos.CENTER);

                        game_preview.getChildren().add(centeredPreview);
                    }
                });

        vbox.getChildren().addAll(saves_list);
        Scene scene = new Scene(vbox, 400, 700);
        stage.setScene(scene);

        return stage;
    }

    /**
     * Displays informational message in the UI.
     * 
     * @param message Message to display
     */
    @Override
    public void showInfo(String message) {
        info_label.setText(message);
    }

    /**
     * Displays error message in the UI.
     * 
     * @param message Error message to display
     */
    @Override
    public void showError(String message) {
        info_label.setText(message);
    }

    /**
     * Handles incoming messages from server. Processes different message types and updates UI
     * accordingly.
     * 
     * @param message JSON formatted message from server
     */
    @Override
    public void handleMessage(String message) {
        JsonDeserializer jsonDeserializer = JsonDeserializer.getInstance();
        switch (jsonDeserializer.getType(message)) {
            case "list":
                List<String> games = jsonDeserializer.getGamesAsList(message);
                List<HboxCell> cells = createCells(games);
                games_list.getItems().clear();
                games_list.getItems().addAll(cells);
                break;
            case "save_list":
                List<String[]> saves = jsonDeserializer.getSavesAsList(message);
                // for (String[] save : saves) {
                // System.out.println(save[0] + " " + save[1]);
                // }
                Stage stage = save_window(saves);
                stage.show();
                break;
            default:
                showError("Unknown message type: " + jsonDeserializer.getType(message));
                break;
        }
    }


    /**
     * Creates HboxCell list items for games list view.
     * 
     * @param labels List of game descriptions
     * @return List of HboxCell items with join buttons
     */
    public List<HboxCell> createCells(List<String> labels) {
        List<HboxCell> cells = new ArrayList<>();
        for (int i = 0; i < labels.size(); i++) {
            Button button = new Button();
            button.setText("Join");
            int gameID = extractGameID(labels.get(i));
            button.setOnAction(e -> {
                try {
                    if (gameID == -1) {
                        showError("Failed to join game");
                        return;
                    }
                    String jsonMessage = JsonBuilder.setBuilder("join")
                            .setPayloadArgument("gameID", String.valueOf(gameID)).build();
                    App.setRoot("secondary");
                    App.getGeneralListener().setController(SecondaryController.getInstance());
                    communication.sendMessage(jsonMessage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    showError(("Failed to join game"));
                }
            });
            cells.add(new HboxCell(labels.get(i), button));
        }
        return cells;
    }


    /**
     * Extracts game ID from game description label.
     * 
     * @param label Game description text
     * @return Game ID or -1 if extraction fails
     */
    private int extractGameID(String label) {
        try {
            int hashIndex = label.indexOf('#');
            int spaceIndex = label.indexOf(' ', hashIndex);
            if (hashIndex != -1 && spaceIndex != -1) {
                String idStr = label.substring(hashIndex + 1, spaceIndex);
                return Integer.parseInt(idStr);
            }
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    /**
     * Returns singleton instance of the controller.
     * 
     * @return IController instance
     */
    public static IController getInstance() {
        return instance;
    }
}
