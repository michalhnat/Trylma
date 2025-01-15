package com.michal;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Utils.JsonBuilder;
import com.michal.Utils.JsonDeserializer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
