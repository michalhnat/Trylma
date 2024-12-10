package com.michal.Game;

/**
 * Represents a game with a board, a maximum number of players, and a status indicating if the game is in progress.
 */
public class Game {
    private final int maxPlayers;
    private GameStatus status;
    private Board board;

    /**
     * Constructs a Game with the specified board and maximum number of players.
     *
     * @param board the game board
     * @param maxPlayers the maximum number of players allowed in the game
     * @throws IllegalArgumentException if the number of players is not allowed for this game
     */
    public Game(Board board, int maxPlayers) {
        if (!board.getAllowedPlayerNumbers().contains(maxPlayers)) {
            throw new IllegalArgumentException(
                    "Number of players (" + maxPlayers + ") is not allowed for this game.");
        }
        this.board = board;
        this.maxPlayers = maxPlayers;
        this.status = GameStatus.WAITING;
    }

    /**
     * Starts the game and initializes the board.
     */
    public void start() {
        this.status = GameStatus.IN_PROGRESS;
        board.initialize();
    }

    /**
     * Moves a player to a new position on the board.
     *
     * @param player the player making the move
     * @param newPosition the new position to move the player to
     * @throws Exception if the move is not valid
     */
    public void move(Player player, Position newPosition) throws Exception {
        // Implement game-specific move logic
        // If the move is invalid, throw InvalidMoveException
        boolean valid = board.validateMove(new Position(0, 0), newPosition); // Example validation
        if (!valid) {
            throw new Exception("Move is not valid.");
        }
        board.move(new Position(0, 0), newPosition);
    }

    /**
     * Returns whether the game is currently in progress.
     *
     * @return true if the game is in progress, false otherwise
     */
    public boolean isInProgress() {
        return status == GameStatus.IN_PROGRESS;
    }

    /**
     * Returns the maximum number of players allowed in the game.
     *
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    // Additional game logic methods
}