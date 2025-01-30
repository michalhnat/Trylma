package com.michal.Game.Board;

import com.michal.Game.Player;
import com.michal.Models.GameMoves;

import java.util.List;

/**
 * Abstract class representing a game board.
 */
public abstract class Board {

    /** The 2D array representing the board. */
    public Node[][] board;

    /**
     * Moves a piece from the start position to the end position.
     *
     * @param start the starting position of the piece
     * @param end the ending position of the piece
     */
    public abstract void move(Position start, Position end);

    /**
     * Checks if any player has won the game.
     *
     * @param players the list of players in the game
     * @return the player who won, or null if no player has won
     */
    public abstract Player checkIfSomeoneWon(List<Player> players);

    /**
     * Initializes the board with the given layout and players.
     *
     * @param layout the layout of the game
     * @param players the list of players in the game
     */
    public abstract void initialize(Layout layout, List<Player> players);

    /**
     * Validates if a move is valid.
     *
     * @param player the player making the move
     * @param start the starting position of the piece
     * @param end the ending position of the piece
     * @return true if the move is valid, false otherwise
     */
    public abstract boolean validateMove(Player player, Position start, Position end);

    /**
     * Returns the 2D array representing the board.
     *
     * @return the 2D array of nodes representing the board
     */
    public abstract Node[][] getBoardArray();

    public void loadPawns(GameMoves loadedGameMoves, List<Player> players) {}
}