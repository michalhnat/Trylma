// Board.java
package com.michal.Game;

import java.util.List;

public abstract class Board {
    public abstract List<Integer> getAllowedPlayerNumbers();

    public abstract void move(Position start, Position end);

    public abstract void print();

    public abstract boolean isGameOver();

    public abstract void initialize();

    public abstract boolean validateMove(Position start, Position end);
}
