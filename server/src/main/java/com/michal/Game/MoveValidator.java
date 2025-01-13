package com.michal.Game;

import java.util.HashSet;

public interface MoveValidator {
    HashSet<Position> getValidMoves(Node[][] board, Position start);

    default boolean isValidMove(Node[][] board, Player player, Position start, Position move) {
        if (board[start.getX()][start.getY()] == null) {
            return false;
        }
        if (board[start.getX()][start.getY()].getPawn() == null
                || board[start.getX()][start.getY()].getPawn().getPlayer() != player) {
            return false;
        }

        HashSet<Position> validMoves = getValidMoves(board, start);

        return validMoves.contains(move);
    }
}
