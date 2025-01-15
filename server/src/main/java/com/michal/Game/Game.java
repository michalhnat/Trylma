package com.michal.Game;

import com.google.errorprone.annotations.Var;

import java.util.List;

/**
 * Represents a game with a board, a maximum number of players, and a status indicating if the game
 * is in progress.
 */
public class Game {
    private final int maxPlayers;
    private GameStatus status;
    private Board board;
    private Layout layout;
    private Variant variant;

    /**
     * Constructs a Game with the specified board and maximum number of players.
     *
     * @param board the game board
     * @param maxPlayers the maximum number of players allowed in the game
     * @throws IllegalArgumentException if the number of players is not allowed for this game
     */
    public Game(Board board, Layout layout, Variant variant) {
        if (!board.getAllowedPlayerNumbers().contains(layout.getPlayers())) {
            throw new IllegalArgumentException( // TODO tego nie potrzeba?
                    "Number of players is not allowed for this game.");
        }
        this.board = board;
        this.maxPlayers = layout.getPlayers();
        this.status = GameStatus.WAITING;
        this.layout = layout;
        this.variant = variant;
    }

    /**
     * Starts the game and initializes the board.
     */
    public void start(List<Player> players) {
        this.status = GameStatus.IN_PROGRESS;
        board.initialize(layout, players);
    }

    /**
     * Moves a player to a new position on the board.
     *
     * @param player the player making the move
     * @param end the new position to move the player to
     * @throws Exception if the move is not valid
     */
    public void move(Player player, Position start, Position end) throws Exception {
        // Implement game-specific move logic
        // If the move is invalid, throw InvalidMoveException
        boolean valid = board.validateMove(player, start, end);
        if (!valid) {
            throw new Exception("Move is not valid.");
        }
        else {
            board.move(start, end);
        }
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

    public Node[][] getBoard() {
        return board.getBoardArray();
    }

    public Layout getLayout() {
        return layout;
    }

    public Variant getVariant() {
        return variant;
    }

    public GameStatus getStatus() {
        return status;
    }

    // Additional game logic methods
}
