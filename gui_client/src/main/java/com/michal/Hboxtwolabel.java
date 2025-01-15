package com.michal;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * A custom JavaFX HBox container that displays two labels side by side. The left label is displayed
 * in uppercase with bold font, while the right label is displayed in lowercase. Both labels expand
 * equally to fill available space. The container is left-aligned with 10px spacing between labels.
 */
public class Hboxtwolabel extends HBox {
  /** The left label component with bold text, displayed in uppercase */
  Label label1 = new Label();

  /** The right label component, displayed in lowercase */
  Label label2 = new Label();

  /**
   * Creates a new Hboxtwolabel with two labels. The left label will be displayed in uppercase with
   * bold font. The right label will be displayed in lowercase. Both labels will expand equally to
   * fill the available space.
   *
   * @param label1 The text for the left label (will be converted to uppercase)
   * @param label2 The text for the right label (will be converted to lowercase)
   */
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
