package com.michal;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Hboxtwolabel extends HBox {
  Label label1 = new Label();
  Label label2 = new Label();

  Hboxtwolabel(String label1, String label2) {
    super();

    this.label1.setText(label1.toUpperCase());
    this.label2.setText(label2.toLowerCase());

    this.label1.setMaxWidth(Double.MAX_VALUE);
    this.label2.setMaxWidth(Double.MAX_VALUE);

    HBox.setHgrow(this.label1, Priority.ALWAYS);
    HBox.setHgrow(this.label2, Priority.ALWAYS);

    this.setSpacing(10);
    this.setStyle("-fx-alignment: center-left;");

    this.label1.setStyle("-fx-font-weight: bold");

    this.label2.setStyle("-fx-alignment: center-right;");
    HBox.setMargin(this.label2, new Insets(0, 0, 0, 0));

    this.getChildren().addAll(this.label1, this.label2);
  }
}
