package com.michal;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SecondaryController {

    @FXML
    private AnchorPane hexGridPane;

    @FXML
    public void initialize() {
        HexGrid hexGrid = new HexGrid();
        hexGridPane.getChildren().add(hexGrid);
    }
}