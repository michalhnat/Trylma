
package com.michal;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Cell extends StackPane {
    private int i;
    private int j;
    private Circle circle;
    private Text text;
    private Board board;
    private Color color;

    public Cell(double centerX, double centerY, int i, int j, double radius, Color color,
            Board board) {
        this.i = i;
        this.j = j;
        this.board = board;
        this.color = color;

        circle = new Circle(radius, color);
        circle.setStrokeWidth(0);

        text = new Text("(" + i + "," + j + ")");

        text.setVisible(false);
        text.setFont(new Font(radius / 2));

        this.getChildren().addAll(circle, text);
        this.setLayoutX(centerX); // - radius
        this.setLayoutY(centerY); // - radius

        this.setOnMouseEntered(event -> handleMouseEntered(event));
        this.setOnMouseExited(event -> handleMouseExited(event));
        this.setOnMouseClicked(event -> handleMouseClicked(event));
    }

    private void handleMouseEntered(MouseEvent event) {
        circle.setOpacity(0.7);
        // circle.setStroke(Paint.valueOf("black"));
        // circle.setStrokeWidth(1);
        text.setVisible(true);
    }

    private void handleMouseExited(MouseEvent event) {
        circle.setOpacity(1.0);
        // circle.setStrokeWidth(0);
        text.setVisible(false);
    }

    private void handleMouseClicked(MouseEvent event) {
        board.handleCellClick(i, j);
        circle.setStrokeWidth(1);
        circle.setStroke(color.invert());
        // System.out.println("Clicked on cell: " + i + ", " + j);
    }

    public void resetBorder() {
        circle.setStrokeWidth(0);
    }

    public void setXY(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    public double getX() {
        return this.getLayoutX() + circle.getRadius();
    }

    public double getY() {
        return this.getLayoutY() + circle.getRadius();
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public void setFill(Paint color) {
        circle.setFill(color);
    }

    public Paint getFill() {
        return circle.getFill();
    }
}

// package com.michal;

// import java.io.ObjectInputStream.GetField;
// import javafx.scene.paint.Paint;
// import javafx.scene.shape.Circle;

// public class Cell extends Circle {
// private int i;
// private int j;

// public Cell(double centerX, double centerY, int i, int j, double radius, Paint color) {
// super(centerX, centerY, radius, color);
// this.i = i;
// this.j = j;


// this.setOnMouseEntered(event -> {
// this.setOpacity(0.7);
// this.setStroke(Paint.valueOf("black"));
// this.setStrokeWidth(1);
// this.setUserData("(" + i + "," + j + ")");
// });

// this.setOnMouseExited(event -> {
// this.setOpacity(1.0);
// this.setStrokeWidth(0);
// });
// }

// public void setXY(double x, double y) {
// this.setCenterX(x);
// this.setCenterY(y);
// }

// public double getX() {
// return this.getCenterX();
// }

// public double getY() {
// return this.getCenterY();
// }

// }
