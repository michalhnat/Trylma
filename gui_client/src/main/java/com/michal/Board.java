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
    private final Map<String, Color> colorMap = new HashMap<>();
    private BoardControllerMediator controller;

    private int[] first_clicked = new int[2];
    private int[] second_clicked = new int[2];

    public Board(int radius, BoardControllerMediator controller) {
        this.cells = new ArrayList<>();
        this.radius = radius;
        this.controller = controller;

        colorMap.put("W", Color.GRAY);
        colorMap.put("B", Color.BLUE);
        colorMap.put("R", Color.RED);
        colorMap.put("Y", Color.YELLOW);
        colorMap.put("G", Color.GREEN);
        colorMap.put("P", Color.PURPLE);
        colorMap.put("O", Color.ORANGE);
        colorMap.put("C", Color.CYAN);
        colorMap.put("M", Color.MAGENTA);
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
                    cells.add(new Cell(x, y, j, 16 - i, radius, colorMap.get(row[j]), this));
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
        Map<int[], Color> changes = compareMaps(map);
        for (Map.Entry<int[], Color> entry : changes.entrySet()) {
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

    public Map<int[], Color> compareMaps(String newMap) {
        Map<int[], Color> changes = new HashMap<>();
        String[] newMapArray = newMap.split("\n");
        String[] currentMapArray = currentMap.split("\n");

        for (int i = 0; i < newMapArray.length; i++) {
            String[] newRow = newMapArray[i].split("");
            String[] currentRow = currentMapArray[i].split("");

            for (int j = 0; j < newRow.length; j++) {
                if (!newRow[j].equals(currentRow[j])) {
                    int[] location = {j, 16 - i};
                    Color newColor = colorMap.get(newRow[j]);
                    changes.put(location, newColor);
                }
            }
        }
        return changes;
    }

    public void handleCellClick(int i, int j) {
        if (first_clicked[0] == 0 && first_clicked[1] == 0) {
            first_clicked[0] = i;
            first_clicked[1] = j;
            controller.setStartXY(i, j);
        } else if (second_clicked[0] == 0 && second_clicked[1] == 0) {
            second_clicked[0] = i;
            second_clicked[1] = j;
            controller.setEndXY(i, j);
        } else {
            first_clicked[0] = i;
            first_clicked[1] = j;
            controller.setStartXY(i, j);
            second_clicked[0] = 0;
            second_clicked[1] = 0;
            controller.setEndXY(0, 0);
        }
        Cell firstCell = getCellByCoordinates(first_clicked[0], first_clicked[1]);
        Cell secondCell = getCellByCoordinates(second_clicked[0], second_clicked[1]);

        // System.out.println("First: " + first_clicked[0] + " " + first_clicked[1]);
        // System.out.println("Second: " + second_clicked[0] + " " + second_clicked[1]);

        if (firstCell != null) {
            firstCell.resetBorder();
        }
        if (secondCell != null) {
            secondCell.resetBorder();
        }
    }

    public boolean isEmpty() {
        return cells.isEmpty();
    }

    public void restet_border_on_active_cells() {
        Cell firstCell = getCellByCoordinates(first_clicked[0], first_clicked[1]);
        Cell secondCell = getCellByCoordinates(second_clicked[0], second_clicked[1]);

        if (firstCell != null) {
            firstCell.resetBorder();
        }
        if (secondCell != null) {
            secondCell.resetBorder();
        }
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
