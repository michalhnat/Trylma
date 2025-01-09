package com.michal;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class HexGrid extends Pane {
    private static final double HEX_SIZE = 30;
    private static final int ROWS = 15; // Adjusted for Trylma layout
    private static final int COLS = 15; // Adjusted for Trylma layout

    public HexGrid() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // Define Trylma-specific positions
                if (isValidHex(row, col)) {
                    double xOffset = HEX_SIZE * 3 / 2 * col;
                    double yOffset = HEX_SIZE * Math.sqrt(3) * (row + 0.5 * (col % 2));
                    Polygon hex = createHexagon(xOffset, yOffset, getHexColor(row, col));
                    this.getChildren().add(hex);
                }
            }
        }
    }

    private boolean isValidHex(int row, int col) {
        // Define the shape of the Trylma board
        // Example: Create a cross shape
        return row == 7 || col == 7 || (row >= 5 && row <= 9 && col >= 5 && col <= 9);
    }

    private Color getHexColor(int row, int col) {
        // Highlight special areas
        if ((row == 0 && col == 7) || (row == 14 && col == 7) ||
            (row == 7 && col == 0) || (row == 7 && col == 14)) {
            return Color.GREEN; // Home areas
        } else if (row == 7 || col == 7) {
            return Color.LIGHTGRAY; // Paths
        }
        return Color.TRANSPARENT;
    }

    private Polygon createHexagon(double x, double y, Color color) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.PI / 3 * i;
            double px = x + HEX_SIZE * Math.cos(angle);
            double py = y + HEX_SIZE * Math.sin(angle);
            hex.getPoints().addAll(px, py);
        }
        hex.setStroke(Color.BLACK);
        hex.setFill(color);
        return hex;
    }
}