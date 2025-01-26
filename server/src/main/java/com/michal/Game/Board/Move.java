package com.michal.Game.Board;

public class Move {
    private Position from;
    private Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public void setFrom(Position from) {
        this.from = from;
    }

    public void setTo(Position to) {
        this.to = to;
    }
}
