package com.michal.Game;

import java.util.HashSet;

/**
 * Interface for validating moves on the game board.
 */
public interface MoveValidator {
    /**
     * Returns a set of valid moves for a pawn starting from the given position.
     *
     * @param board the game board
     * @param start the starting position of the pawn
     * @return a set of valid positions the pawn can move to
     */
    HashSet<Position> getValidMoves(Node[][] board, Position start);

    /**
     * Checks if a move is valid for the given player.
     *
     * @param board the game board
     * @param player the player making the move
     * @param start the starting position of the pawn
     * @param move the position to move the pawn to
     * @return true if the move is valid, false otherwise
     */
    default boolean isValidMove(Node[][] board, Player player, Position start, Position move) {
        if (board[start.x()][start.y()] == null) {
            return false;
        }
        if (board[start.x()][start.y()].getPawn() == null
                || board[start.x()][start.y()].getPawn().getPlayer() != player) {
            return false;
        }

        HashSet<Position> validMoves = getValidMoves(board, start);

        return validMoves.contains(move);
    }
}