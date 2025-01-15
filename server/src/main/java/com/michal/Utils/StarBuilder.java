package com.michal.Utils;

import com.michal.Game.CornerNode;
import com.michal.Game.Direction;
import com.michal.Game.Node;

/**
 * Utility class for building a star-shaped game board.
 */
public class StarBuilder {

    private static StarBuilder instance;

    /**
     * Returns the singleton instance of the StarBuilder.
     *
     * @return the singleton instance of the StarBuilder
     */
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

    /**
     * Builds a star-shaped game board of the specified size.
     *
     * @param size the size of the star
     * @return a 2D array representing the star-shaped game board
     */
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
                if (y >= x + size) { // NorthWest corner
                    board[x][y] = new CornerNode(Direction.NORTHWEST);
                } else if (y <= x - size) { // SouthEast corner
                    board[x][y] = new CornerNode(Direction.SOUTHEAST);
                } else {
                    board[x][y] = new Node();
                }
            }
        }

        // Then the four triangles

        // SouthWest triangle
        for (int x = 0; x < blockSize; x++) {
            for (int y = blockSize; y < 2 * blockSize; y++) {
                if (y <= x + blockSize) {
                    board[x][y] = new CornerNode(Direction.SOUTHWEST);
                }
            }
        }

        // North triangle
        for (int x = midpoint + 1; x <= maxSize - blockSize; x++) {
            for (int y = midpoint + size; y <= maxSize; y++) {
                if (y <= x + blockSize) {
                    board[x][y] = new CornerNode(Direction.NORTH);
                }
            }
        }

        // South triangle
        for (int x = blockSize; x < midpoint; x++) {
            for (int y = 0; y < blockSize; y++) {
                if (y >= x - blockSize) {
                    board[x][y] = new CornerNode(Direction.SOUTH);
                }
            }
        }

        // NorthEast triangle
        for (int x = midpoint + size; x <= maxSize; x++) {
            for (int y = midpoint + 1; y < midpoint + size; y++) {
                if (y >= x - blockSize) {
                    board[x][y] = new CornerNode(Direction.NORTHEAST);
                }
            }
        }

        return board;
    }
}