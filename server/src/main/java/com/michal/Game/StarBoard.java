package com.michal.Game;

import com.michal.Utils.StarBuilder;

import java.util.*;

public class StarBoard extends Board {

    private final int size;
    private final Node[][] board;
    private final List<Integer> allowedPlayerNumbers = new ArrayList<>(Arrays.asList(2, 3, 4, 6));
    private final MoveValidator moveValidator;

    public StarBoard(int size, MoveValidator moveValidator) {
        super();
        this.size = size;
        this.board = StarBuilder.buildStar(size);
        this.moveValidator = moveValidator;
    }

    @Override
    public List<Integer> getAllowedPlayerNumbers() {
        return allowedPlayerNumbers;
    }

    @Override
    public void move(Position start, Position end) {
        Pawn movedPawn = board[start.getX()][start.getY()].getPawn();
        board[start.getX()][start.getY()].setPawn(null);
        board[end.getX()][end.getY()].setPawn(movedPawn);
    }

    @Override
    public void print() {
        // Print logic
    }

    @Override
    public Player checkIfSomeoneWon(List<Player> players) {
        // Map all directions to their oppoosites
        Map<Direction, Direction> oppositeDirections = Map.of(
                Direction.SOUTH, Direction.NORTH,
                Direction.NORTH, Direction.SOUTH,
                Direction.SOUTHWEST, Direction.NORTHEAST,
                Direction.NORTHEAST, Direction.SOUTHWEST,
                Direction.NORTHWEST, Direction.SOUTHEAST,
                Direction.SOUTHEAST, Direction.NORTHWEST
        );

        // For each player, check if they won
        for (Player p : players) {
            String playerColor = p.getColor();
            Direction playerDestination = null;
            for (Node[] row : board) {
                for (Node node : row) {
                    if (node instanceof CornerNode cornerNode && cornerNode.getOwner() == p) {
                        playerDestination = oppositeDirections.get(cornerNode.getDirection());
                        break;
                    }
                }
            }

            // Check every corner that is the destination of the player. If all of them have a pawn of the player color, the player won
            boolean won = true;
            for (Node[] row : board) {
                for (Node node : row) {
                    if (node instanceof CornerNode cornerNode && cornerNode.getDirection() == playerDestination) {
                        if (cornerNode.getPawn() == null || !Objects.equals(cornerNode.getPawn().getPlayer().getColor(), playerColor)) {
                            won = false;
                            break;
                        }
                    }
                }
            }

            if (won) {
                return p;
            }
        }

        return null;
    }

    @Override
    public void initialize(Layout layout, List<Player> players) {

        Map<Layout, List<Direction>> layoutToDirections = Map.of(
                Layout.SIXPLAYERS, List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST, Direction.SOUTHEAST),
                Layout.FOURPLAYERS, List.of(Direction.SOUTHWEST, Direction.NORTHWEST, Direction.NORTHEAST, Direction.SOUTHEAST),
                Layout.THREEPLAYERS_ONESET, List.of(Direction.SOUTH, Direction.NORTHWEST, Direction.NORTHEAST),
                Layout.THREEPLAYERS_TWOSETS, List.of(Direction.SOUTH, Direction.SOUTHWEST, Direction.NORTHWEST, Direction.NORTH, Direction.NORTHEAST, Direction.SOUTHEAST),
                Layout.TWOPLAYERS_ONESET, List.of(Direction.SOUTH, Direction.NORTH),
                Layout.TWOPLAYERS_TWOSETS_ADJACENT, List.of(Direction.SOUTHWEST, Direction.NORTHEAST, Direction.NORTHWEST, Direction.SOUTHEAST),
                Layout.TWOPLAYERS_TWOSETS_OPPOSITE, List.of(Direction.SOUTHWEST, Direction.NORTHWEST, Direction.SOUTHEAST, Direction.NORTHEAST),
                Layout.TWOPLAYERS_THREESETS, List.of(Direction.SOUTH, Direction.NORTH, Direction.SOUTHEAST, Direction.NORTHEAST, Direction.SOUTHWEST, Direction.NORTHWEST)
        );

        List<Direction> directions = layoutToDirections.get(layout);
        if (directions != null) {
            int playerIndex = 0;
            for (Direction direction : directions) {
                for (Node[] nodes : board) {
                    for (Node node : nodes) {
                        if (node instanceof CornerNode cornerNode && cornerNode.getDirection() == direction) {
                            cornerNode.setOwner(players.get(playerIndex));
                            cornerNode.setPawn(new Pawn(players.get(playerIndex)));
                        }
                    }
                }
                playerIndex = (playerIndex + 1) % players.size();
            }
        }
    }

    @Override
    public boolean validateMove(Player player, Position start, Position end) {
        return moveValidator.isValidMove(board, player, start, end);
    }

    public Node[][] getBoardArray() {
        return board;
    }
}
