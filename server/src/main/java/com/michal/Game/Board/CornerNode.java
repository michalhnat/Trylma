package com.michal.Game.Board;

import com.michal.Game.Direction;

/**
 * Represents a corner node in the game, which has a specific direction.
 */
public class CornerNode extends Node {
    private final Direction direction;

    /**
     * Constructs a CornerNode with the specified direction.
     *
     * @param direction the direction of the corner node
     */
    public CornerNode(Direction direction) {
        this.direction = direction;
    }

    public CornerNode(CornerNode node) {
        this.direction = node.getDirection();
        this.setPawn(node.getPawn());
        this.setOwner(node.getOwner());
    }

    /**
     * Returns the direction of the corner node.
     *
     * @return the direction of the corner node
     */
    public Direction getDirection() {
        return direction;
    }
}