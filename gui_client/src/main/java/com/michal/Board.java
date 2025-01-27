package com.michal;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a game board composed of colored cells in a hexagonal grid pattern. The board manages
 * cell creation, modification, and interaction handling between cells. It maintains the state of
 * the board and handles color mapping for different cell types.
 */
public class Board {
    /** List of cells that make up the board */
    private List<Cell> cells;
    /** Radius of each cell in the hexagonal grid */
    private int radius;
    /** Current string representation of the board map */
    private String currentMap;
    /** Maps string identifiers to their corresponding colors */
    private final Map<String, Color> colorMap = new HashMap<>();
    /** Mediator for communication between board and controller */
    private BoardControllerMediator controller;
    /** Coordinates of the first clicked cell [x,y] */
    private int[] first_clicked = new int[2];
    /** Coordinates of the second clicked cell [x,y] */
    private int[] second_clicked = new int[2];
    /** Flag indicating if the user is selecting the end cell */
    private boolean isSelectingEnd = false;


    /**
     * Constructs a new Board with specified radius and controller.
     *
     * @param radius The radius of each hexagonal cell
     * @param controller The mediator for board-controller communication
     */
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

    public Board(int radius) {
        this.cells = new ArrayList<>();
        this.radius = radius;

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

    /**
     * Creates the board layout from a string map representation. Each character in the map
     * represents a cell color or empty space.
     *
     * @param map String representation of the board layout
     */
    public void createBoardOutOfMap(String map) {
        currentMap = map;
        String[] mapArray = map.split("\n");
        for (int i = 0; i < mapArray.length; i++) {
            String[] row = mapArray[i].split("");
            for (int j = 0; j < row.length; j++) {
                double x = radius * j * 2 + radius + i * radius;
                double y = radius * 2 * i + radius;
                if (!(row[j].equals("X"))) {
                    cells.add(new Cell(x, y, j, mapArray.length - i - 1, radius,
                            colorMap.get(row[j]), this));
                }

            }
        }
    }

    /**
     * Retrieves a cell at specified coordinates.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return The Cell at the specified coordinates, or null if not found
     */
    private Cell getCellByCoordinates(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getI() == x && cell.getJ() == y) {
                return cell;
            }
        }
        return null;
    }

    /**
     * Updates the board layout based on a new map representation. Only changes cells that differ
     * from the current layout.
     *
     * @param map New string representation of the board layout
     */
    public void editBoardOutOfMap(String map) {
        Map<int[], Color> changes = compareMaps(map);
        for (Map.Entry<int[], Color> entry : changes.entrySet()) {
            int[] location = entry.getKey();

            Cell cell = getCellByCoordinates(location[0], location[1]);
            if (cell != null) {
                cell.setFill(entry.getValue());
            }
        }

        currentMap = map;
    }

    /**
     * Compares two map representations and returns the differences.
     *
     * @param newMap The new map to compare against the current map
     * @return Map of coordinates and their new colors for changed cells
     */
    public Map<int[], Color> compareMaps(String newMap) {
        Map<int[], Color> changes = new HashMap<>();
        String[] newMapArray = newMap.split("\n");
        String[] currentMapArray = currentMap.split("\n");

        for (int i = 0; i < newMapArray.length; i++) {
            String[] newRow = newMapArray[i].split("");
            String[] currentRow = currentMapArray[i].split("");

            for (int j = 0; j < newRow.length; j++) {
                if (!newRow[j].equals(currentRow[j])) {
                    int[] location = {j, newMapArray.length - i - 1};
                    Color newColor = colorMap.get(newRow[j]);
                    changes.put(location, newColor);
                }
            }
        }
        return changes;
    }

    /**
     * Handles cell click events and updates selected cell coordinates. Manages the state of first
     * and second clicked cells.
     *
     * @param i The x-coordinate of the clicked cell
     * @param j The y-coordinate of the clicked cell
     */
    public void handleCellClick(int i, int j) {
        if (!isSelectingEnd) {
            Cell firstCell = getCellByCoordinates(first_clicked[0], first_clicked[1]);
            Cell secondCell = getCellByCoordinates(second_clicked[0], second_clicked[1]);

            if (firstCell != null) {
                firstCell.resetBorder();
            }

            if (secondCell != null) {
                secondCell.resetBorder();
            }

            first_clicked[0] = i;
            first_clicked[1] = j;
            second_clicked[0] = 0;
            second_clicked[1] = 0;

            controller.setStartXY(i, j);

            controller.setEndXY(0, 0);

            isSelectingEnd = true;

        } else {
            second_clicked[0] = i;
            second_clicked[1] = j;
            controller.setEndXY(i, j);
            isSelectingEnd = false;
        }
    }

    /**
     * Checks if the board has any cells.
     *
     * @return true if the board has no cells, false otherwise
     */
    public boolean isEmpty() {
        return cells.isEmpty();
    }

    /**
     * Resets the border styling of currently active (selected) cells.
     */
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

    public void disactivate_all_cells() {
        for (Cell cell : cells) {
            cell.disactivate();
        }
    }

    /**
     * Gets the list of all cells on the board.
     *
     * @return List of Cell objects representing the board
     */
    public List<Cell> getCells() {
        return cells;
    }

    public double getBoardWidth() {
        return radius * 2 * (currentMap.split("\n")[0].length() + 1);
    }

    public double getBoardHeight() {
        return radius * 2 * (currentMap.split("\n").length + 1);
    }
}
