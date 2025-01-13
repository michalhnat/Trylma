package com.michal.Utils;

import com.michal.Game.Node;

public class BoardStringBuilder {
    private static BoardStringBuilder instance;

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
