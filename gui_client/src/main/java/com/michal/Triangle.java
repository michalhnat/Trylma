package com.michal;

import javafx.scene.shape.Polygon;
import javafx.scene.paint.Color;

public class Triangle extends Polygon {
    public static final double TRIANGLE_SIZE = 20.0;
    private int row;
    private int col;

    public Triangle(double x, double y, boolean pointUp) {
        if (pointUp) {
            getPoints().addAll(x, y - TRIANGLE_SIZE, x - TRIANGLE_SIZE * Math.sqrt(3) / 2,
                    y + TRIANGLE_SIZE / 2, x + TRIANGLE_SIZE * Math.sqrt(3) / 2,
                    y + TRIANGLE_SIZE / 2);
        } else {
            getPoints().addAll(x, y + TRIANGLE_SIZE, x - TRIANGLE_SIZE * Math.sqrt(3) / 2,
                    y - TRIANGLE_SIZE / 2, x + TRIANGLE_SIZE * Math.sqrt(3) / 2,
                    y - TRIANGLE_SIZE / 2);
        }

        setFill(Color.WHITE);
        setStroke(Color.BLACK);

        setOnMouseClicked(e -> {
            System.out.println("Clicked triangle at: " + row + "," + col);
        });
    }

    public void setGridPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public double getTriangleSize() {
        return TRIANGLE_SIZE;
    }
}
