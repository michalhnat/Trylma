package com.michal.Game;

import com.michal.Utils.StarBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StandardBoard extends Board {

    private final int size;
    private final Node[][] board;
    private final List<Integer> allowedPlayerNumbers = new ArrayList<>(Arrays.asList(2, 3, 4, 6));
    private final MoveValidator moveValidator = new MoveValidatorStandard();

    public StandardBoard(int size) {
        super();
        this.size = size;
        this.board = StarBuilder.buildStar(size);
    }

    @Override
    public List<Integer> getAllowedPlayerNumbers() {
        return allowedPlayerNumbers;
    }

    @Override
    public void move(Position start, Position end) {
        // Move logic
    }

    @Override
    public void print() {
        // Print logic
    }

    @Override
    public boolean isGameOver() {
        // Game over logic
        return false;
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
    public boolean validateMove(Position start, Position end) {
        return moveValidator.isValidMove(board, start, end);
    }
}
