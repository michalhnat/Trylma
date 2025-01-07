package com.michal.Game;

public class CornerNode extends Node{
    private final Direction direction;

    public CornerNode(Integer x, Integer y, Direction direction) {
        super(x, y);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
