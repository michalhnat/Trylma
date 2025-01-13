package com.michal;

import java.io.ObjectInputStream.GetField;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Cell extends Circle {
    private int i;
    private int j;

    public Cell(double centerX, double centerY, int i, int j, double radius, Paint color) {
        super(centerX, centerY, radius, color);
        this.i = i;
        this.j = j;
    }

    public void setXY(double x, double y) {
        this.setCenterX(x);
        this.setCenterY(y);
    }

    public double getX() {
        return this.getCenterX();
    }

    public double getY() {
        return this.getCenterY();
    }

}
