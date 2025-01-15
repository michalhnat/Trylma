package com.michal.Game;

import java.util.HashSet;
import java.util.List;

public class MoveValidatorSuper implements MoveValidator {
    @Override
    public HashSet<Position> getValidMoves(Node[][] boardOriginal, Position start) {
        Node[][] board = deepCopyBoard(boardOriginal);

        // Usun pionek gracza (zeby nie mogl np przeskoczyc nad samym soba)
        if (board[start.getX()][start.getY()] != null) {
            board[start.getX()][start.getY()].setPawn(null); // Remove the pawn from the new Node
        }

        HashSet<Position> validMoves = new HashSet<>();
        validMoves.add(start);

        // Check normal hops, and long jumps
        recursiveJump(board, start, validMoves);
        recursiveLongJump(board, start, validMoves);

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
        nextNeighbour:
        for (Position n : neighbours) {
            if (board[n.getX()][n.getY()] != null) {  // If the tile is on the board
                if (board[n.getX()][n.getY()].getPawn() != null) {  // If the tile has a pawn that can be jumped over

                    // Find the first empty tile in that direction
                    Position jump = n;
                    Position prev = current;
                    while (board[jump.getX()][jump.getY()].getPawn() != null) {
                        Position temp = jump;
                        jump = new Position(2 * jump.getX() - prev.getX(), 2 * jump.getY() - prev.getY());
                        prev = temp;

                        if (jump.getX() < 0 || jump.getY() < 0 || jump.getX() >= board.length || jump.getY() >= board.length
                        || board[jump.getX()][jump.getY()] == null) {
                            continue nextNeighbour;
                        }
                    }
                    if (board[jump.getX()][jump.getY()] != null) {  // If the tile is on the board
                        if (board[jump.getX()][jump.getY()].getPawn() == null) {  // If the tile is empty
                            if (!validMoves.contains(jump)) {
                                validMoves.add(jump);
                                recursiveJump(board, jump, validMoves); // recursively check for more jumps
                                recursiveLongJump(board, jump, validMoves); // recursively check for more jumps
                            }
                        }
                    }
                }
            }
        }
    }

    private void recursiveLongJump(Node[][] board, Position current, HashSet<Position> validMoves) {
        List<Position> neighbours = getNeighbours(current, board.length);
        nextNeighbour:
        for (Position n : neighbours) {
            if (board[n.getX()][n.getY()] == null || board[n.getX()][n.getY()].getPawn() != null) {
                continue;  // We consider only empty neighbouring tiles
            }

            Position jump = n;
            Position prev = current;
            int tilesJumped = 1;
            while (board[jump.getX()][jump.getY()].getPawn() == null) {
                Position temp = jump;
                jump = new Position(2 * jump.getX() - prev.getX(), 2 * jump.getY() - prev.getY());
                prev = temp;

                if (jump.getX() < 0 || jump.getY() < 0 || jump.getX() >= board.length || jump.getY() >= board[0].length) {
                    continue nextNeighbour;
                }
                if (board[jump.getX()][jump.getY()] == null) {
                    continue nextNeighbour;
                }
                tilesJumped++;
            }

            for (int i = 0; i < tilesJumped; i++) {
                Position temp = jump;
                jump = new Position(2 * jump.getX() - prev.getX(), 2 * jump.getY() - prev.getY());
                prev = temp;

                if (jump.getX() < 0 || jump.getY() < 0 || jump.getX() >= board.length || jump.getY() >= board[0].length
                || board[jump.getX()][jump.getY()] == null
                || board[jump.getX()][jump.getY()].getPawn() != null) {
                    continue nextNeighbour;
                }
            }

            if (jump.getX() < 0 || jump.getY() < 0 || jump.getX() >= board.length || jump.getY() >= board[0].length) {
                continue;
            }
            if (board[jump.getX()][jump.getY()] != null) {  // If the tile is on the board
                if (board[jump.getX()][jump.getY()].getPawn() == null) {  // If the tile is empty
                    if (!validMoves.contains(jump)) {
                        validMoves.add(jump);
                        recursiveJump(board, jump, validMoves); // recursively check for more jumps
                        recursiveLongJump(board, jump, validMoves); // recursively check for more jumps
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

    private Node[][] deepCopyBoard(Node[][] boardOriginal) {
        Node[][] boardCopy = new Node[boardOriginal.length][boardOriginal[0].length];
        for (int i = 0; i < boardOriginal.length; i++) {
            for (int j = 0; j < boardOriginal[i].length; j++) {
                if (boardOriginal[i][j] != null) {
                    boardCopy[i][j] = new Node(boardOriginal[i][j]); // Assuming Node has a copy constructor
                }
            }
        }
        return boardCopy;
    }
}
