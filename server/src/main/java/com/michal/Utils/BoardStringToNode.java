package com.michal.Utils;

import com.michal.Game.Board.Node;
import com.michal.Game.Board.Pawn;
import com.michal.Game.Player;

import java.util.List;

public class BoardStringToNode {
    public static Node[][] stringToNode(String boardString, List<Player> players) {
        // convert string to 2D array of characters
        String[] rows = boardString.split("\n");
        String[][] stringBoard = new String[rows.length][rows[0].length()];

        for (int i = 0; i < rows.length; i++) {
            stringBoard[i] = rows[i].split("");
        }

        // Insert pawns into the Node[][] board
        int length = rows.length;
        int size = (length - 1) / 4 + 1;
        Node[][] board = StarBuilder.buildStar(size);

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                if (stringBoard[length - y - 1][x].equals("W") || stringBoard[length - y - 1][x].equals("X")) {
                    continue;
                } else {
                    for (Player player : players) {
                        if (stringBoard[length - y - 1][x].equals(player.getColor().substring(0, 1))) {
                            board[x][y].setPawn(new Pawn(player));
                        }
                    }
                }
            }
        }

        return board;
    }
}
