package com.michal.Game;

import com.michal.Game.Board.Board;
import com.michal.Game.Board.Layout;
import com.michal.Game.Board.Node;
import com.michal.Game.Board.Position;

import java.util.List;

/**
 * Represents a game with a board, a maximum number of players, and a status indicating if the game
 * is in progress.
 */
public class Game {
    private final int maxPlayers;
    private GameStatus status;
    private final Board board;
    private final Layout layout;
    private final Variant variant;

    /**
     * Constructs a Game with the specified board and maximum number of players.
     *
     * @param board the game board
     * @param layout the game layout
     * @param variant the game variant
     * @throws IllegalArgumentException if the number of players is not allowed for this game
     */
    public Game(Board board, Layout layout, Variant variant) {
        this.board = board;
        this.maxPlayers = layout.getPlayers();
        this.status = GameStatus.WAITING;
        this.layout = layout;
        this.variant = variant;
    }

    /**
     * Starts the game and initializes the board.
     *
     * @param players the list of players participating in the game
     */
    public void start(List<Player> players) {
        this.status = GameStatus.IN_PROGRESS;
        board.initialize(layout, players);
    }

    /**
     * Moves a player to a new position on the board.
     *
     * @param player the player making the move
     * @param start the starting position of the pawn
     * @param end the new position to move the player to
     * @throws Exception if the move is not valid
     */
    public void move(Player player, Position start, Position end) throws Exception {
        boolean valid = board.validateMove(player, start, end);
        if (!valid) {
            throw new Exception("Move is not valid.");
        } else {
            board.move(start, end);
        }
    }

    /**
     * Returns whether the game is currently in progress.
     *
     * @return true if the game is in progress, false otherwise
     */
    public boolean isWaitingForPlayers() {
        return status != GameStatus.IN_PROGRESS;
    }

    /**
     * Returns the maximum number of players allowed in the game.
     *
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns the board array.
     *
     * @return the board array
     */
    public Node[][] getBoardArray() {
        return board.getBoardArray();
    }

    /**
     * Returns the layout of the game.
     *
     * @return the game layout
     */
    public Layout getLayout() {
        return layout;
    }

    /**
     * Returns the variant of the game.
     *
     * @return the game variant
     */
    public Variant getVariant() {
        return variant;
    }

    /**
     * Returns the status of the game.
     *
     * @return the game status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Checks if a player has won the game.
     *
     * @param players the list of players in the game
     * @return the player who won, or null if no player has won
     */
    public Player checkIfSomeoneWon(List<Player> players) {
        return board.checkIfSomeoneWon(players);
    }

    /**
     * Sets the status of the game.
     *
     * @param gameStatus the new game status
     */
    public void setStatus(GameStatus gameStatus) {
        this.status = gameStatus;
    }
}