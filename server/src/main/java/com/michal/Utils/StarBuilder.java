package com.michal.Utils;

import com.michal.Game.CornerNode;
import com.michal.Game.Direction;
import com.michal.Game.Node;
import com.michal.Game.Player;

import java.util.List;

public class StarBuilder {

    private static StarBuilder instance;

    public static StarBuilder getInstance() {
        if (instance == null) {
            synchronized (StarBuilder.class) {
                if (instance == null) {
                    instance = new StarBuilder();
                }
            }
        }
        return instance;
    }

    public static Node[][] buildStar(int size) {
        int maxSize = 4 * size - 4;
        int midpoint = maxSize / 2;
        int blockSize = size - 1;

        Node[][] board = new Node[maxSize+1][maxSize+1];

        // Fill the entire board with nulls
        for (int i = 0; i < maxSize; i++) {
            for (int j = 0; j < maxSize; j++) {
                board[i][j] = null;
            }
        }

        // Filling the board with star shaped nodes

        // First the big square in the middle
        for (int x = blockSize; x <= maxSize - blockSize; x++) {
            for (int y = blockSize; y <= maxSize - blockSize; y++) {
                if (y >= x +size) { // NorthWest corner
                    board[x][y] = new CornerNode(x, y, Direction.NORTHWEST);
                } else if (y <= x - size) { // SouthEast corner
                    board[x][y] = new CornerNode(x, y, Direction.SOUTHEAST);
                } else {
                    board[x][y] = new Node(x, y);
                }
            }
        }

        // Then the four triangles

        // SouthWest triangle
        for (int x=0; x < blockSize;x++) {
            for (int y = blockSize; y < 2*blockSize; y++) {
                if (y <= x + blockSize) {
                    board[x][y] = new CornerNode(x, y, Direction.SOUTHWEST);
                }
            }
        }

        // North triangle
        for (int x = midpoint + 1; x <= maxSize - blockSize; x++) {
            for (int y = midpoint + size; y <= maxSize; y++) {
                if (y <= x + blockSize) {
                    board[x][y] = new CornerNode(x, y, Direction.NORTH);
                }
            }
        }

        // South triangle
        for (int x = blockSize; x < midpoint; x++) {
            for (int y = 0; y < blockSize; y++) {
                if (y >= x-blockSize) {
                    board[x][y] = new CornerNode(x, y, Direction.SOUTH);
                }
            }
        }

        // NorthEast triangle
        for (int x = midpoint + size; x <= maxSize; x++) {
            for (int y = midpoint+1; y < midpoint+size; y++) {
                if (y >= x-blockSize) {
                    board[x][y] = new CornerNode(x, y, Direction.NORTHEAST);
                }
            }
        }

        return board;
    }
}
