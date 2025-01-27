package com.michal.Game.Bots;

import com.michal.Game.*;
import com.michal.Game.Board.CornerNode;
import com.michal.Game.Board.Move;
import com.michal.Game.Board.Node;
import com.michal.Game.Board.Position;
import com.michal.Game.MoveValidation.MoveValidator;

import java.util.*;

public class BotAlgorithmSmart implements BotAlgorithm {
    private MoveValidator moveValidator;

    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }

    @Override
    public Move makeMove(Node[][] originalBoard, Player botPlayer) {
        Node[][] board = deepCopyBoard(originalBoard);

        List<Move> bestMoves = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                Node node = board[x][y];
                if (node != null && node.getPawn() != null
                        && node.getPawn().getPlayer() == botPlayer) {
                    int finalX = x;
                    int finalY = y;
                    List<Move> validMoves = moveValidator.getValidMoves(board, new Position(x, y))
                            .stream().map(end -> new Move(new Position(finalX, finalY), end))
                            .toList();
                    for (Move move : validMoves) {
                        Node[][] boardCopy = deepCopyBoard(board);
                        applyMove(boardCopy, move);

                        int score = evaluateBoard(boardCopy, botPlayer);

                        if (score > maxScore) {
                            maxScore = score;
                            bestMoves.clear();
                            bestMoves.add(move);
                        } else if (score == maxScore) {
                            bestMoves.add(move);
                        }
                    }
                }
            }
        }

        return bestMoves.isEmpty() ? null : bestMoves.get(new Random().nextInt(bestMoves.size()));
    }


    private int evaluateBoard(Node[][] board, Player player) {
        // Precompute destination positions
        Map<Direction, Direction> oppositeDirections = Map.of(Direction.SOUTH, Direction.NORTH,
                Direction.NORTH, Direction.SOUTH, Direction.SOUTHWEST, Direction.NORTHEAST,
                Direction.NORTHEAST, Direction.SOUTHWEST, Direction.NORTHWEST, Direction.SOUTHEAST,
                Direction.SOUTHEAST, Direction.NORTHWEST);

        HashSet<Direction> playerDestinations = new HashSet<>();
        List<Position> destinationPositions = new ArrayList<>();

        for (Node[] nodes : board) {
            for (Node node : nodes) {
                switch (node) {
                    case null -> {
                        continue;
                    }
                    case CornerNode cornerNode when cornerNode.getOwner() == null -> {
                        continue;
                    }
                    case CornerNode cornerNode when Objects.equals(cornerNode.getOwner().getColor(),
                            player.getColor()) -> {
                        Direction targetDirection =
                                oppositeDirections.get(cornerNode.getDirection());
                        playerDestinations.add(targetDirection);
                    }
                    default -> {
                    }
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Node node = board[i][j];
                if (node instanceof CornerNode cornerNode
                        && playerDestinations.contains(cornerNode.getDirection())) {
                    destinationPositions.add(new Position(i, j));
                }
            }
        }

        // Calculate score
        int score = 0;
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                Node node = board[x][y];
                if (node == null || node.getPawn() == null)
                    continue;

                if (Objects.equals(node.getPawn().getPlayer().getColor(), player.getColor())) {
                    if (node instanceof CornerNode cornerNode
                            && playerDestinations.contains(cornerNode.getDirection())) {
                        int depthBonus = calculateDepthBonus(new Position(x, y), board.length,
                                cornerNode.getDirection());
                        score += 5 + depthBonus;
                    } else {
                        if (node instanceof CornerNode cornerNode && Objects
                                .equals(cornerNode.getOwner().getColor(), player.getColor())) {
                            int depthPenalty = calculateDepthBonus(new Position(x, y), board.length,
                                    cornerNode.getDirection());
                            score -= 3 + depthPenalty;
                        }
                        int minDistance = Integer.MAX_VALUE;
                        for (Position dest : destinationPositions) {
                            int distance = calculateDistance(new Position(x, y), dest);
                            minDistance = Math.min(minDistance, distance);
                        }

                        score -= minDistance;
                    }
                }
            }
        }
        return score;
    }

    private int calculateDepthBonus(Position position, int length, Direction direction) {
        int distanceFromEnd = 0;
        int blockSize = (length - 1) / 4;
        switch (direction) {
            case NORTH -> distanceFromEnd =
                    calculateDistance(position, new Position(length - 1 - blockSize, length - 1));
            case SOUTH -> distanceFromEnd = calculateDistance(position, new Position(blockSize, 0));
            case NORTHEAST -> distanceFromEnd =
                    calculateDistance(position, new Position(length - 1, length - blockSize - 1));
            case NORTHWEST -> distanceFromEnd =
                    calculateDistance(position, new Position(blockSize, length - blockSize - 1));
            case SOUTHEAST -> distanceFromEnd =
                    calculateDistance(position, new Position(length - blockSize - 1, blockSize));
            case SOUTHWEST -> distanceFromEnd =
                    calculateDistance(position, new Position(0, blockSize));
        }
        return blockSize - distanceFromEnd;
    }

    private int calculateDistance(Position p1, Position p2) {
        // Calculate differences
        int lenX = p2.x() - p1.x(); // Change in x (can be positive or negative)
        int lenY = p2.y() - p1.y(); // Change in y (can be positive or negative)

        int diagonalMoves = 0;
        int straightMoves = 0;

        // Check if diagonal movement is possible
        if ((lenX > 0 && lenY > 0) || (lenX < 0 && lenY < 0)) {
            // Both x and y are moving in the allowed diagonal direction
            diagonalMoves = Math.min(Math.abs(lenX), Math.abs(lenY));
            // Remove diagonal moves from the remaining distance
            lenX -= diagonalMoves * Integer.signum(lenX);
            lenY -= diagonalMoves * Integer.signum(lenY);
        }

        // Remaining moves are pure horizontal or vertical
        straightMoves = Math.abs(lenX) + Math.abs(lenY);

        // Total steps
        return diagonalMoves + straightMoves;
    }

    /**
     * Applies the given move to the board.
     *
     * @param board the current game board
     * @param move the move to apply
     */
    private void applyMove(Node[][] board, Move move) {
        Node fromNode = board[move.getFrom().x()][move.getFrom().y()];
        Node toNode = board[move.getTo().x()][move.getTo().y()];

        // Move the pawn
        toNode.setPawn(fromNode.getPawn());
        fromNode.setPawn(null);

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
                    if (boardOriginal[i][j] instanceof CornerNode) {
                        boardCopy[i][j] = new CornerNode((CornerNode) boardOriginal[i][j]);
                    } else {
                        boardCopy[i][j] = new Node(boardOriginal[i][j]);
                    }
                }
            }
        }
        return boardCopy;
    }
}


