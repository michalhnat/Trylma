package com.michal.Utils;

import com.michal.Game.Board.Node;

/**
 * Utility class for building a string representation of a game board.
 */
public class BoardStringBuilder {
    private static BoardStringBuilder instance;

    /**
     * Returns the singleton instance of the BoardStringBuilder.
     *
     * @return the singleton instance of the BoardStringBuilder
     */
    public static BoardStringBuilder getInstance() {
        if (instance == null) {
            synchronized (BoardStringBuilder.class) {
                if (instance == null) {
                    instance = new BoardStringBuilder();
                }
            }
        }
        return instance;
    }

    /**
     * Builds a string representation of the given game board.
     *
     * @param board the 2D array representing the game board
     * @return a string representation of the game board
     */
    public static String buildBoardString(Node[][] board) {
        StringBuilder boardString = new StringBuilder();

        assert board != null;
        for (int y = board.length - 1; y >= 0; y--) {
            for (int x = 0; x < board.length; x++) {
                if (board[x][y] == null) {
                    boardString.append("X");
                } else if (board[x][y].getPawn() == null) {
                    boardString.append("W");
                } else if (board[x][y].getPawn() != null) {
                    String letter = board[x][y].getPawn().getPlayer().getColor().substring(0, 1);
                    boardString.append(letter);
                }
            }
            boardString.append("\n");
        }

        return boardString.toString();
    }
}
