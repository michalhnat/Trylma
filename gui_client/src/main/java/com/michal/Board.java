package com.michal;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private List<Cell> cells;
    private int radius;
    private String currentMap;
    private final Map<String, Paint> colorMap = new HashMap<>();

    public Board(int radius) {
        this.cells = new ArrayList<>();
        this.radius = radius;

        colorMap.put("W", Color.GRAY);
        colorMap.put("B", Color.CYAN);
        colorMap.put("R", Color.RED);

    }

    public void createBoardOutOfMap(String map) {
        currentMap = map;
        String[] mapArray = map.split("\n");
        for (int i = 0; i < mapArray.length; i++) {
            String[] row = mapArray[i].split("");

            // System.out.println("Row " + i + ": " + String.join("", row));
            for (int j = 0; j < row.length; j++) {
                double x = radius * j * 2 + radius + i * radius;
                double y = radius * 2 * i + radius;
                // if ((row[j].equals("W"))) {
                // cells.add(new Cell(x, y, j, 16 - i, radius, Color.GRAY));
                // } else if (row[j].equals("B")) {
                // cells.add(new Cell(x, y, j, 16 - i, radius, Color.CYAN));
                // } else if (row[j].equals("R")) {
                // cells.add(new Cell(x, y, j, 16 - i, radius, Color.RED));
                // }
                if (!(row[j].equals("X"))) {
                    cells.add(new Cell(x, y, j, 16 - i, radius, colorMap.get(row[j])));
                }
            }
        }
    }

    private Cell getCellByCoordinates(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getI() == x && cell.getJ() == y) {
                return cell;
            }
        }
        return null;
    }

    public void editBoardOutOfMap(String map) {
        // String[] mapArray = map.split("\n");
        // for (int i = 0; i < mapArray.length; i++) {
        // String[] row = mapArray[i].split("");
        // for (int j = 0; j < row.length; j++) {
        // Cell cell = getCellByCoordinates(i, j);
        // if (cell != null) {
        // System.out.println("Cell found ");
        // if (row[j].equals("W")) {
        // cell.setFill(Color.GRAY);
        // } else if (row[j].equals("B")) {
        // cell.setFill(Color.CYAN);
        // } else if (row[j].equals("R")) {
        // cell.setFill(Color.RED);
        // }
        // }
        // }
        // }
        Map<int[], Paint> changes = compareMaps(map);
        for (Map.Entry<int[], Paint> entry : changes.entrySet()) {
            int[] location = entry.getKey();

            Cell cell = getCellByCoordinates(location[0], location[1]);
            if (cell != null) {
                // System.out.println(
                // "Before: " + location[0] + " " + location[1] + " Color: " + cell.getFill());
                cell.setFill(entry.getValue());
                // System.out.println(
                // "After " + location[0] + " " + location[1] + " Color: " + entry.getValue());
            }
        }

        currentMap = map;
    }

    public Map<int[], Paint> compareMaps(String newMap) {
        Map<int[], Paint> changes = new HashMap<>();
        String[] newMapArray = newMap.split("\n");
        String[] currentMapArray = currentMap.split("\n");

        for (int i = 0; i < newMapArray.length; i++) {
            String[] newRow = newMapArray[i].split("");
            String[] currentRow = currentMapArray[i].split("");

            for (int j = 0; j < newRow.length; j++) {
                if (!newRow[j].equals(currentRow[j])) {
                    int[] location = {j, 16 - i};
                    Paint newColor = colorMap.get(newRow[j]);
                    changes.put(location, newColor);
                }
            }
        }
        return changes;
    }

    public boolean isEmpty() {
        return cells.isEmpty();
    }
    // public void updateBoard(String map) {
    // if (cells.isEmpty()) {
    // createBoardOutOfMap(map);
    // } else {
    // editBoardOutOfMap(map);
    // }
    // }

    public List<Cell> getCells() {
        return cells;
    }
}
