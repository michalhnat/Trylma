package com.michal.Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * MockBoard is a mock implementation of the Board class for testing purposes.
 */
public class MockBoard extends Board {
    private final int size = 8;
    private final Node[][] board = new Node[size][size];
    private final List<Integer> allowedPlayerNumbers = new ArrayList<>(Arrays.asList(2, 3, 4, 6));

    /**
     * Returns the list of allowed player numbers for the game.
     *
     * @return a list of allowed player numbers
     */
    @Override
    public List<Integer> getAllowedPlayerNumbers() {
        return allowedPlayerNumbers;
    }

    /**
     * Mock implementation of the move method.
     *
     * @param start the starting position of the move
     * @param end the ending position of the move
     */
    @Override
    public void move(Position start, Position end) {
        // Mock move logic
    }

    /**
     * Mock implementation of the print method.
     */
    @Override
    public void print() {
        // Mock print
    }

    /**
     * Mock implementation of the isGameOver method.
     *
     * @return false indicating the game is not over
     */
    @Override
    public boolean isGameOver() {
        return false;
    }

    /**
     * Mock implementation of the initialize method.
     */
    @Override
    public void initialize(Layout layout, List<Player> players) {
        // Mock initialize
    }

    /**
     * Mock implementation of the validateMove method.
     *
     * @param start the starting position of the move
     * @param end the ending position of the move
     * @return true indicating the move is valid
     */
    @Override
    public boolean validateMove(Player player, Position start, Position end) {
        return true;
    }

    /**
     * Returns the board array.
     *
     * @return the board array
     */
    @Override
    public Node[][] getBoardArray() {
        return board;
    }
}