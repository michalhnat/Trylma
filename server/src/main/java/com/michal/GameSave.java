package com.michal;

/**
 * Represents a saved game state.
 */
public class GameSave {
    private String id;
    private String board;

    /**
     * Constructs a new GameSave instance with the specified id and board state.
     *
     * @param id the unique identifier for the saved game
     * @param board the board state of the saved game
     */
    public GameSave(String id, String board) {
        this.id = id;
        this.board = board;
    }

    /**
     * Returns the unique identifier for the saved game.
     *
     * @return the unique identifier for the saved game
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the saved game.
     *
     * @param id the unique identifier for the saved game
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the board state of the saved game.
     *
     * @return the board state of the saved game
     */
    public String getBoard() {
        return board;
    }

    /**
     * Sets the board state of the saved game.
     *
     * @param board the board state of the saved game
     */
    public void setBoard(String board) {
        this.board = board;
    }
}