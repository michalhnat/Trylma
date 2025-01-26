package com.michal.Game.Board;

/**
 * Represents a position on the game board with x and y coordinates.
 */
public record Position(int x, int y) {
    /**
     * Constructs a Position with the specified coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Position {
    }

    /**
     * Returns the x-coordinate of this position.
     *
     * @return the x-coordinate
     */
    @Override
    public int x() {
        return x;
    }

    /**
     * Returns the y-coordinate of this position.
     *
     * @return the y-coordinate
     */
    @Override
    public int y() {
        return y;
    }

}