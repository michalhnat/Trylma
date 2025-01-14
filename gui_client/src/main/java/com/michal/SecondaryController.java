package com.michal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class SecondaryController {
    @FXML
    private AnchorPane BoardPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button moveButton;

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

    private Board board;

    private GameManager gameManager;

    private ICommunication communication;

    @FXML
    public void initialize() {
        communication = App.getCommunication();

        board = new Board(15);
        board.createBoardOutOfMap("XXXXXXXXXXXXWXXXX\r\n" + //
                "XXXXXXXXXXXWWXXXX\r\n" + //
                "XXXXXXXXXXWWWXXXX\r\n" + //
                "XXXXXXXXXWWWWXXXX\r\n" + //
                "XXXXWWWWWWWWWWWWW\r\n" + //
                "XXXXWWWWWWWWWWWWX\r\n" + //
                "XXXXWWWWWWWWWWWXX\r\n" + //
                "XXXXWWWWWWWWWWXXX\r\n" + //
                "XXXXWWWWWWWWWXXXX\r\n" + //
                "XXXWWWWWWWWWWXXXX\r\n" + //
                "XXWWWWWWWWWWWXXXX\r\n" + //
                "XWWWWWWWWWWWWXXXX\r\n" + //
                "WWWWWWWWWWWWWXXXX\r\n" + //
                "XXXXWWWWXXXXXXXXX\r\n" + //
                "XXXXWWWXXXXXXXXXX\r\n" + //
                "XXXXWWXXXXXXXXXXX\r\n" + //
                "XXXXWXXXXXXXXXXXX");
        board.getCells().forEach(cell -> {
            BoardPane.getChildren().add(cell);
        });

        try {
            gameManager = new GameManager(communication.getInputStream(), communication, label,
                    label, board, borderPane);
            Thread gameManagerThread = new Thread(gameManager);
            gameManagerThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


        // gameManager.start();

    }
}
