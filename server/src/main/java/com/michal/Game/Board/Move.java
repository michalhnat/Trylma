package com.michal.Game.Board;

/**
 * Represents a move in the game, consisting of a starting position and an ending position.
 */
public class Move {
    private Position from;
    private Position to;

    /**
     * Constructs a Move with the specified starting and ending positions.
     *
     * @param from the starting position of the move
     * @param to the ending position of the move
     */
    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the starting position of the move.
     *
     * @return the starting position of the move
     */
    public Position getFrom() {
        return from;
    }

    /**
     * Returns the ending position of the move.
     *
     * @return the ending position of the move
     */
    public Position getTo() {
        return to;
    }

    /**
     * Sets the starting position of the move.
     *
     * @param from the starting position to set
     */
    public void setFrom(Position from) {
        this.from = from;
    }

    /**
     * Sets the ending position of the move.
     *
     * @param to the ending position to set
     */
    public void setTo(Position to) {
        this.to = to;
    }
}