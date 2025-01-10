package com.michal.Game;

import java.util.HashSet;

public interface MoveValidator {
    HashSet<Position> getValidMoves(Node[][] board, Position start);

    default boolean isValidMove(Node[][] board, Position start, Position move) {
        HashSet<Position> validMoves = getValidMoves(board, start);

        return validMoves.contains(move);
    }
}
