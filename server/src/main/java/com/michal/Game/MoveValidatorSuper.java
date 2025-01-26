package com.michal.Game;

import java.util.HashSet;
import java.util.List;

/**
 * Provides methods to validate moves on the game board.
 */
public class MoveValidatorSuper implements MoveValidator {
    /**
     * Returns a set of valid moves for a pawn starting from the given position.
     *
     * @param boardOriginal the original game board
     * @param start the starting position of the pawn
     * @return a set of valid positions the pawn can move to
     */
    @Override
    public HashSet<Position> getValidMoves(Node[][] boardOriginal, Position start) {
        Node[][] board = deepCopyBoard(boardOriginal);

        // Remove the pawn from the starting position to prevent jumping over itself
        if (board[start.x()][start.y()] != null) {
            board[start.x()][start.y()].setPawn(null);
        }

        HashSet<Position> validMoves = new HashSet<>();
        validMoves.add(start);

        // Check normal hops and long jumps
        recursiveJump(board, start, validMoves);
        recursiveLongJump(board, start, validMoves);

        // Check single tile moves
        List<Position> neighbours = getNeighbours(start, board.length);
        for (Position n : neighbours) {
            if (board[n.x()][n.y()] != null && board[n.x()][n.y()].getPawn() == null) {
                validMoves.add(n);
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
        nextNeighbour:
        for (Position n : neighbours) {
            if (board[n.x()][n.y()] != null && board[n.x()][n.y()].getPawn() != null) {
                Position jump = n;
                Position prev = current;
                while (board[jump.x()][jump.y()].getPawn() != null) {
                    Position temp = jump;
                    jump = new Position(2 * jump.x() - prev.x(), 2 * jump.y() - prev.y());
                    prev = temp;

                    if (jump.x() < 0 || jump.y() < 0 || jump.x() >= board.length || jump.y() >= board.length
                            || board[jump.x()][jump.y()] == null) {
                        continue nextNeighbour;
                    }
                }
                if (board[jump.x()][jump.y()] != null && board[jump.x()][jump.y()].getPawn() == null) {
                    if (!validMoves.contains(jump)) {
                        validMoves.add(jump);
                        recursiveJump(board, jump, validMoves);
                        recursiveLongJump(board, jump, validMoves);
                    }
                }
            }
        }
    }

    /**
     * Recursively checks for valid long jumps from the current position.
     *
     * @param board the game board
     * @param current the current position
     * @param validMoves the set of valid moves
     */
    private void recursiveLongJump(Node[][] board, Position current, HashSet<Position> validMoves) {
        List<Position> neighbours = getNeighbours(current, board.length);
        nextNeighbour:
        for (Position n : neighbours) {
            if (board[n.x()][n.y()] == null || board[n.x()][n.y()].getPawn() != null) {
                continue;
            }

            Position jump = n;
            Position prev = current;
            int tilesJumped = 1;
            while (board[jump.x()][jump.y()].getPawn() == null) {
                Position temp = jump;
                jump = new Position(2 * jump.x() - prev.x(), 2 * jump.y() - prev.y());
                prev = temp;

                if (jump.x() < 0 || jump.y() < 0 || jump.x() >= board.length || jump.y() >= board[0].length) {
                    continue nextNeighbour;
                }
                if (board[jump.x()][jump.y()] == null) {
                    continue nextNeighbour;
                }
                tilesJumped++;
            }

            for (int i = 0; i < tilesJumped; i++) {
                Position temp = jump;
                jump = new Position(2 * jump.x() - prev.x(), 2 * jump.y() - prev.y());
                prev = temp;

                if (jump.x() < 0 || jump.y() < 0 || jump.x() >= board.length || jump.y() >= board[0].length
                        || board[jump.x()][jump.y()] == null || board[jump.x()][jump.y()].getPawn() != null) {
                    continue nextNeighbour;
                }
            }

            if (board[jump.x()][jump.y()] != null && board[jump.x()][jump.y()].getPawn() == null) {
                if (!validMoves.contains(jump)) {
                    validMoves.add(jump);
                    recursiveJump(board, jump, validMoves);
                    recursiveLongJump(board, jump, validMoves);
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

    /**
     * Creates a deep copy of the given game board.
     *
     * @param boardOriginal the original game board
     * @return a deep copy of the game board
     */
    private Node[][] deepCopyBoard(Node[][] boardOriginal) {
        Node[][] boardCopy = new Node[boardOriginal.length][boardOriginal[0].length];
        for (int i = 0; i < boardOriginal.length; i++) {
            for (int j = 0; j < boardOriginal[i].length; j++) {
                if (boardOriginal[i][j] != null) {
                    boardCopy[i][j] = new Node(boardOriginal[i][j]);
                }
            }
        }
        return boardCopy;
    }
}