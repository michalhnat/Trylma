package com.michal;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * A custom JavaFX HBox container that creates a horizontal layout with a label and button. The
 * label expands to fill available space while the button maintains its preferred size. Used for
 * creating consistent row layouts in lists or forms.
 */
public class HboxCell extends HBox {
  /** The label component that displays text and expands to fill space */
  Label label = new Label();

  /**
   * Creates a new HBoxCell with the specified label text and button. The layout will arrange the
   * label and button horizontally, with the label expanding to fill available space.
   *
   * @param labelText The text to display in the label
   * @param button The button to place after the label
   */
  HboxCell(String labelText, Button button) {
    super();

    label.setText(labelText);
    label.setMaxWidth(Double.MAX_VALUE);
    HBox.setHgrow(label, Priority.ALWAYS);

    this.getChildren().addAll(label, button);
  }
}
