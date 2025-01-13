package com.michal.Game;

import java.util.HashSet;
import java.util.List;

public class MoveValidatorStandard implements MoveValidator{
    @Override
    public HashSet<Position> getValidMoves(Node[][] board, Position start) {
        HashSet<Position> validMoves = new HashSet<>();
        validMoves.add(start);

        // First, single tile moves
        List<Position> neighbours = getNeighbours(start);
        for (Position n : neighbours) {
            if (board[n.getX()][n.getY()] != null) {  // If the tile is on the board
                if (board[n.getX()][n.getY()].getPawn() == null) {  // If the tile is empty
                    validMoves.add(n);
                }
            }
        }

        // Then, recursively check every single possible jump
        recursiveJump(board, start, validMoves);

        validMoves.remove(start);
        return validMoves;
    }

    private void recursiveJump(Node[][] board, Position current, HashSet<Position> validMoves) {
        List<Position> neighbours = getNeighbours(current);
        for (Position n : neighbours) {
            if (board[n.getX()][n.getY()] != null) {  // If the tile is on the board
                if (board[n.getX()][n.getY()].getPawn() != null) {  // If the tile has a pawn that can be jumped over

                    // For every neighbouring pawn, check if the tile behind it is empty
                    Position jump = new Position(2 * n.getX() - current.getX(), 2 * n.getY() - current.getY());
                    if (board[jump.getX()][jump.getY()] != null) {  // If the tile is on the board
                        if (board[jump.getX()][jump.getY()].getPawn() == null) {  // If the tile is empty
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

    private List<Position> getNeighbours (Position center) {

        return List.of(
                new Position(center.getX() - 1, center.getY()),
                new Position(center.getX() + 1, center.getY()),
                new Position(center.getX(), center.getY() - 1),
                new Position(center.getX(), center.getY() + 1),
                new Position(center.getX() + 1, center.getY() + 1),
                new Position(center.getX() - 1, center.getY() - 1)
        );
    }
}
