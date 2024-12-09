package com.michal.Game;

public class Game {
    private final int maxPlayers;
    private boolean inProgress;
    private Board board;

    public Game(Board board, int maxPlayers) {
        if (!board.getAllowedPlayerNumbers().contains(maxPlayers)) {
            throw new IllegalArgumentException(
                    "Number of players (" + maxPlayers + ") is not allowed for this game.");
        }
        this.board = board;
        this.maxPlayers = maxPlayers;
        this.inProgress = false;
    }

    public void start() {
        this.inProgress = true;
        board.initialize();
    }

    public void move(Player player, Position newPosition) throws Exception {
        // Implement game-specific move logic
        // If the move is invalid, throw InvalidMoveException
        boolean valid = board.validateMove(new Position(0, 0), newPosition); // Example validation
        if (!valid) {
            throw new Exception("Move is not valid.");
        }
        board.move(new Position(0, 0), newPosition);
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    // Additional game logic methods
}
