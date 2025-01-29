package com.michal;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Represents a single cell in the game board implemented as a StackPane. Each cell is a clickable,
 * interactive hexagonal element that displays coordinates and responds to mouse events with visual
 * feedback.
 */
public class Cell extends StackPane {
    /** X-coordinate in the board grid */
    private int i;
    /** Y-coordinate in the board grid */
    private int j;
    /** Visual circle representation of the cell */
    private Circle circle;
    /** Text displaying coordinates */
    private Text text;
    /** Reference to the parent board */
    private Board board;
    /** Cell's color */
    private Color color;

    /**
     * Creates a new cell with specified position and appearance.
     *
     * @param centerX The X position of the cell center
     * @param centerY The Y position of the cell center
     * @param i The grid X-coordinate
     * @param j The grid Y-coordinate
     * @param radius The radius of the cell
     * @param color The color of the cell
     * @param board Reference to the parent board
     */
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

    /**
     * Handles mouse enter events by changing opacity and showing coordinates.
     *
     * @param event The mouse event
     */
    private void handleMouseEntered(MouseEvent event) {
        circle.setOpacity(0.7);
        text.setVisible(true);
    }

    /**
     * Handles mouse exit events by restoring opacity and hiding coordinates.
     *
     * @param event The mouse event
     */
    private void handleMouseExited(MouseEvent event) {
        circle.setOpacity(1.0);
        // circle.setStrokeWidth(0);
        text.setVisible(false);
    }

    /**
     * Handles mouse click events by notifying the board and updating visual state.
     *
     * @param event The mouse event
     */
    private void handleMouseClicked(MouseEvent event) {
        board.handleCellClick(i, j);
        circle.setStrokeWidth(1);
        circle.setStroke(Color.WHITE);
    }

    /**
     * Resets the cell's border to its default state.
     */
    public void resetBorder() {
        circle.setStrokeWidth(0);
    }

    /**
     * Sets the cell's position in the layout.
     *
     * @param x The new X position
     * @param y The new Y position
     */
    public void setXY(double x, double y) {
        this.setLayoutX(x);
        this.setLayoutY(y);
    }

    /**
     * Gets the cell's center X coordinate.
     *
     * @return The X coordinate of the cell's center
     */
    public double getX() {
        return this.getLayoutX() + circle.getRadius();
    }

    /**
     * Gets the cell's center Y coordinate.
     *
     * @return The Y coordinate of the cell's center
     */
    public double getY() {
        return this.getLayoutY() + circle.getRadius();
    }

    /**
     * Gets the cell's grid X-coordinate.
     *
     * @return The grid X-coordinate
     */
    public int getI() {
        return i;
    }

    /**
     * Gets the cell's grid Y-coordinate.
     *
     * @return The grid Y-coordinate
     */
    public int getJ() {
        return j;
    }

    /**
     * Sets the fill color of the cell.
     *
     * @param color The new fill color
     */
    public void setFill(Paint color) {
        circle.setFill(color);
    }

    /**
     * Gets the current fill color of the cell.
     *
     * @return The current fill color
     */
    public Paint getFill() {
        return circle.getFill();
    }

    public void disactivate() {
        this.setOnMouseEntered(null);
        this.setOnMouseExited(null);
        this.setOnMouseClicked(null);
    }

    public void activate() {
        this.setOnMouseEntered(event -> handleMouseEntered(event));
        this.setOnMouseExited(event -> handleMouseExited(event));
        this.setOnMouseClicked(event -> handleMouseClicked(event));
    }
}
