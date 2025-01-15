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

public class PrimaryController implements IController {

    private static IController instance;
    private ICommunication communication;
    private GeneralListener listener;


    @FXML
    private TextField ip_tf;

    @FXML
    private TextField port_tf;

    @FXML
    private Button connect_btn;

    @FXML
    private Label info_label;

    @FXML
    private Label games_label;

    @FXML
    private ListView<HboxCell> games_list;

    @FXML
    private Button refresh_button;

    @FXML
    private Button create_button;

    // @FXML
    // private TextField variant_tf;

    // @FXML
    // private TextField layout_tf;

    @FXML
    private ChoiceBox<String> layout_cb;

    @FXML
    private ChoiceBox<String> variant_cb;

    @FXML
    private TextField boardSize_tf;


    @FXML
    private void initialize() {
        communication = App.getCommunication();
        instance = this;
    }

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

                // listener = new PrimaryServerListener(info_label, info_label, games_list,
                // App.getCommunication());
                // Thread listenerThread = new Thread(listener);
                // listenerThread.start();
                // request_games_list();
            } catch (Exception e) {
                info_label.setText("Failed to start listener");
            }
        } catch (FailedConnectingToServerException e) {
            info_label.setText("Failed to connect to the server.");
        }

        request_games_list();

    }

    @FXML
    private void request_games_list() {
        String jsonMessage = JsonBuilder.setBuilder("list").build();
        try {
            App.getCommunication().sendMessage(jsonMessage);
        } catch (Exception e) {
            info_label.setText("Failed to list games");

        }
    }

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
        // String gameName = create_tf.getText();
        // String jsonMessage = JsonBuilder.setBuilder("create").add("name", gameName).build();

    }

    @Override
    public void showInfo(String message) {
        info_label.setText(message);
    }

    @Override
    public void showError(String message) {
        info_label.setText(message);
    }

    @Override
    public void handleMessage(String message) {
        // System.out.println("PrimaryController: " + message);
        JsonDeserializer jsonDeserializer = JsonDeserializer.getInstance();
        switch (jsonDeserializer.getType(message)) {
            case "list":
                // System.out.println("PrimaryController: " + jsonDeserializer.getType(message));
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

    // public void setCommunication(ICommunication communication) {
    // this.communication = communication;
    // }
    // @FXML
    // private void switchToSecondary() throws IOException {
    // App.setRoot("secondary");
    // }

    public static IController getInstance() {
        return instance;
    }
}
