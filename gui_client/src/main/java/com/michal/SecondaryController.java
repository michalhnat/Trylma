package com.michal;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    public void initialize() {
        Board starBoard = new Board(15);
        starBoard.createBoardOutOfMap("XXXXXXXXXXXXBXXXX\r\n" + //
                "XXXXXXXXXXXBBXXXX\r\n" + //
                "XXXXXXXXXXBBBXXXX\r\n" + //
                "XXXXXXXXXBBBBXXXX\r\n" + //
                "XXXXBBBBWWWWWBBBB\r\n" + //
                "XXXXBBBWWWWWWBBBX\r\n" + //
                "XXXXBBWWWWWWWBBXX\r\n" + //
                "XXXXBWWWWWWWWBXXX\r\n" + //
                "XXXXWWWWWWWWWXXXX\r\n" + //
                "XXXRWWWWWWWWRXXXX\r\n" + //
                "XXRRWWWWWWWRRXXXX\r\n" + //
                "XRRRWWWWWWRRRXXXX\r\n" + //
                "RRRRWWWWWRRRRXXXX\r\n" + //
                "XXXXRRRRXXXXXXXXX\r\n" + //
                "XXXXRRRXXXXXXXXXX\r\n" + //
                "XXXXRRXXXXXXXXXXX\r\n" + //
                "XXXXRXXXXXXXXXXXX");
        starBoard.getCells().forEach(cell -> {
            BoardPane.getChildren().add(cell);
        });
    }
}
