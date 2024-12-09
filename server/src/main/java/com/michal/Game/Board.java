package com.michal.Game;

public abstract class Board {
    public abstract void move(Position start, Position end);

    public abstract void print();

    public abstract boolean isGameOver();

    public abstract boolean validateMove(Position start, Position end);

}