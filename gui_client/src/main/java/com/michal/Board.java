package com.michal;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Cell> cells;
    private int radius;

    public Board(int radius) {
        this.cells = new ArrayList<>();
        this.radius = radius;
    }

    public void createBoardOutOfMap(String map) {
        String[] mapArray = map.split("\n");
        for (int i = 0; i < mapArray.length; i++) {
            String[] row = mapArray[i].split("");

            System.out.println("Row " + i + ": " + String.join("", row));
            for (int j = 0; j < row.length; j++) {
                double x = radius * j * 2 + radius + i * radius;
                double y = radius * 2 * i + radius;
                if ((row[j].equals("W"))) {
                    cells.add(new Cell(x, y, i, j, radius, Color.GRAY));
                } else if (row[j].equals("B")) {
                    cells.add(new Cell(x, y, i, j, radius, Color.CYAN));
                } else if (row[j].equals("R")) {
                    cells.add(new Cell(x, y, i, j, radius, Color.RED));
                }

            }
        }
    }

    public List<Cell> getCells() {
        return cells;
    }
}
