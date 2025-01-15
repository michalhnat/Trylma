package com.michal.Game;

import java.util.HashSet;
import java.util.List;

public class MoveValidatorStandard implements MoveValidator{
    @Override
    public HashSet<Position> getValidMoves(Node[][] board, Position start) {
        HashSet<Position> validMoves = new HashSet<>();
        validMoves.add(start);

        // First, recursively check every single possible jump
        recursiveJump(board, start, validMoves);

        // Then, single tile moves
        List<Position> neighbours = getNeighbours(start, board.length);
        for (Position n : neighbours) {
            if (board[n.getX()][n.getY()] != null) {  // If the tile is on the board
                if (board[n.getX()][n.getY()].getPawn() == null) {  // If the tile is empty
                    validMoves.add(n);
                }
            }
        }

        validMoves.remove(start);
        return validMoves;
    }

    private void recursiveJump(Node[][] board, Position current, HashSet<Position> validMoves) {
        List<Position> neighbours = getNeighbours(current, board.length);
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

    private List<Position> getNeighbours (Position center, int boardLength) {
        List<Position> neighbours = new java.util.ArrayList<>(List.of(
                new Position(center.getX() - 1, center.getY()),
                new Position(center.getX() + 1, center.getY()),
                new Position(center.getX(), center.getY() - 1),
                new Position(center.getX(), center.getY() + 1),
                new Position(center.getX() + 1, center.getY() + 1),
                new Position(center.getX() - 1, center.getY() - 1)
        ));

        neighbours.removeIf(neighbour -> neighbour.getX() < 0 || neighbour.getY() < 0 || neighbour.getX() >= boardLength || neighbour.getY() >= boardLength);

        return neighbours;
    }
}
