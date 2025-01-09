package com.michal;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Utils.JsonBuilder;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class PrimaryController {

    private ICommunication communication;
    private PrimaryServerListener listener;
    
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

    @FXML
    private TextField create_tf;
    

    // @FXML
    // public void initialize() {
    //     // communication = App.getCommunication();
    //     if (App.getCommunication().getInputStream() == null) {
    //         System.out.println("null");
    //     } else {
    //         System.out.println("not null");
    //     }
    //     listener = new PrimaryServerListener(info_label, info_label, games_list, App.getCommunication().getInputStream());
        
    // }

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
                // System.out.println(App.getCommunication().isConnected());
                listener = new PrimaryServerListener(info_label, info_label, games_list, App.getCommunication());
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
        int players = Integer.parseInt(create_tf.getText());

        if (players < 2 || players > 6 || players % 2 != 0) {
            info_label.setText("Invalid number of players");
            return;
        }

        String jsonMessage =
                    JsonBuilder.setBuilder("create").setPayloadArgument("players", players).build();
        // String gameName = create_tf.getText();
        // String jsonMessage = JsonBuilder.setBuilder("create").add("name", gameName).build();
        try {
            App.getCommunication().sendMessage(jsonMessage);
            request_games_list();
        } catch (Exception e) {
            info_label.setText("Failed to create game");
        }
    }

    // public void setCommunication(ICommunication communication) {
    //     this.communication = communication;
    // }
    // @FXML
    // private void switchToSecondary() throws IOException {
    //     App.setRoot("secondary");
    // }
}
