package com.michal.Game;

public class CornerNode extends Node{
    private final Direction direction;

    public CornerNode(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
