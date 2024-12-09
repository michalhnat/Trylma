// MockBoard.java
package com.michal.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockBoard extends Board {
    private final int size = 8;
    private final String[][] board = new String[size][size];
    private final List<Integer> allowedPlayerNumbers = new ArrayList<>(Arrays.asList(2, 3, 4, 6));

    @Override
    public List<Integer> getAllowedPlayerNumbers() {
        return allowedPlayerNumbers;
    }

    @Override
    public void move(Position start, Position end) {
        // Mock move logic
    }

    @Override
    public void print() {
        // Mock print
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public void initialize() {
        // Mock initialize
    }

    @Override
    public boolean validateMove(Position start, Position end) {
        return true;
    }
}
