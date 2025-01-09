package com.michal;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HboxCell extends HBox{
        Label label = new Label();
        // Button button = new Button();

        HboxCell(String labelText, Button button) {
            super();

            label.setText(labelText);
            label.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(label, Priority.ALWAYS);

            // button.setText(buttonText);

            this.getChildren().addAll(label, button);
          }
        
}
