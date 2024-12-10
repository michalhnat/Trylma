package com.michal.Game;

/**
 * Represents a pawn in the game with a color and position.
 */
public class Pawn {
    private String color;
    private Position position;

    /**
     * Constructs a Pawn with the specified color and position.
     *
     * @param color the color of the pawn
     * @param position the position of the pawn
     */
    public Pawn(String color, Position position) {
        this.color = color;
        this.position = position;
    }

    /**
     * Returns the color of this pawn.
     *
     * @return the color of the pawn
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color of this pawn.
     *
     * @param color the new color of the pawn
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Returns the position of this pawn.
     *
     * @return the position of the pawn
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Sets the position of this pawn.
     *
     * @param position the new position of the pawn
     */
    public void setPosition(Position position) {
        this.position = position;
    }
}
