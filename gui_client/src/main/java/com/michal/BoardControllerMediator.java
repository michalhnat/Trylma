package com.michal;

/**
 * Mediator interface that facilitates communication between the Board and its Controller. Handles
 * coordinate updates for start and end positions on the board.
 */
public interface BoardControllerMediator {

    /**
     * Sets the starting X and Y coordinates.
     * 
     * @param x The x-coordinate of the starting position
     * @param y The y-coordinate of the starting position
     */
    public void setStartXY(int x, int y);

    /**
     * Sets the ending X and Y coordinates.
     * 
     * @param x The x-coordinate of the ending position
     * @param y The y-coordinate of the ending position
     */
    public void setEndXY(int x, int y);
}
