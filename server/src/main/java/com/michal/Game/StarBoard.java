package com.michal.Game;

import com.michal.Utils.StarBuilder;

import java.util.*;

/**
 * Represents a star-shaped game board.
 */
public class StarBoard extends Board {

    private final Node[][] board;
    private final MoveValidator moveValidator;

    /**
     * Constructs a StarBoard with the specified size and move validator.
     *
     * @param size the size of the star-shaped board
     * @param moveValidator the validator for moves on the board
     */
    public StarBoard(int size, MoveValidator moveValidator) {
        super();
        this.board = StarBuilder.buildStar(size);
        this.moveValidator = moveValidator;
    }

    /**
     * Moves a pawn from the start position to the end position.
     *
     * @param start the starting position of the pawn
     * @param end the ending position of the pawn
     */
    @Override
    public void move(Position start, Position end) {
        Pawn movedPawn = board[start.x()][start.y()].getPawn();
        board[start.x()][start.y()].setPawn(null);
        board[end.x()][end.y()].setPawn(movedPawn);
    }

    /**
     * Checks if any player has won the game.
     *
     * @param players the list of players in the game
     * @return the player who won, or null if no player has won
     */
    @Override
    public Player checkIfSomeoneWon(List<Player> players) {
        // Map all directions to their opposites
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

    /**
     * Initializes the board with the specified layout and players.
     *
     * @param layout the layout of the board
     * @param players the list of players in the game
     */
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

    /**
     * Validates a move from the start position to the end position for the specified player.
     *
     * @param player the player making the move
     * @param start the starting position of the move
     * @param end the ending position of the move
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean validateMove(Player player, Position start, Position end) {
        return moveValidator.isValidMove(board, player, start, end);
    }

    /**
     * Returns the 2D array representing the game board.
     *
     * @return the 2D array representing the game board
     */
    public Node[][] getBoardArray() {
        return board;
    }
}