package com.michal.Game;

import java.util.HashSet;
import java.util.List;

/**
 * Standard implementation of the MoveValidator interface.
 */
public class MoveValidatorStandard implements MoveValidator {
    /**
     * Returns a set of valid moves for a pawn starting from the given position.
     *
     * @param board the game board
     * @param start the starting position of the pawn
     * @return a set of valid positions the pawn can move to
     */
    @Override
    public HashSet<Position> getValidMoves(Node[][] board, Position start) {
        HashSet<Position> validMoves = new HashSet<>();
        validMoves.add(start);

        // First, recursively check every single possible jump
        recursiveJump(board, start, validMoves);

        // Then, single tile moves
        List<Position> neighbours = getNeighbours(start, board.length);
        for (Position n : neighbours) {
            if (board[n.x()][n.y()] != null) {  // If the tile is on the board
                if (board[n.x()][n.y()].getPawn() == null) {  // If the tile is empty
                    validMoves.add(n);
                }
            }
        }

        validMoves.remove(start);
        return validMoves;
    }

    /**
     * Recursively checks for valid jumps from the current position.
     *
     * @param board the game board
     * @param current the current position
     * @param validMoves the set of valid moves
     */
    private void recursiveJump(Node[][] board, Position current, HashSet<Position> validMoves) {
        List<Position> neighbours = getNeighbours(current, board.length);
        for (Position n : neighbours) {
            if (board[n.x()][n.y()] != null) {  // If the tile is on the board
                if (board[n.x()][n.y()].getPawn() != null) {  // If the tile has a pawn that can be jumped over

                    // For every neighbouring pawn, check if the tile behind it is empty
                    Position jump = new Position(2 * n.x() - current.x(), 2 * n.y() - current.y());
                    if (board[jump.x()][jump.y()] != null) {  // If the tile is on the board
                        if (board[jump.x()][jump.y()].getPawn() == null) {  // If the tile is empty
                            if (!validMoves.contains(jump)) {
                                validMoves.add(jump);
                                recursiveJump(board, jump, validMoves); // recursively check for more jumps
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a list of neighbouring positions for the given position.
     *
     * @param center the center position
     * @param boardLength the length of the board
     * @return a list of neighbouring positions
     */
    private List<Position> getNeighbours(Position center, int boardLength) {
        List<Position> neighbours = new java.util.ArrayList<>(List.of(
                new Position(center.x() - 1, center.y()),
                new Position(center.x() + 1, center.y()),
                new Position(center.x(), center.y() - 1),
                new Position(center.x(), center.y() + 1),
                new Position(center.x() + 1, center.y() + 1),
                new Position(center.x() - 1, center.y() - 1)
        ));

        neighbours.removeIf(neighbour -> neighbour.x() < 0 || neighbour.y() < 0 || neighbour.x() >= boardLength || neighbour.y() >= boardLength);

        return neighbours;
    }
}