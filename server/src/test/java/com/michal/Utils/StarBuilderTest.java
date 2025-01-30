package com.michal.Utils;

import com.michal.Game.Board.CornerNode;
import com.michal.Game.Board.Node;

public class StarBuilderTest {
    StarBuilder starBuilder = StarBuilder.getInstance();



    // main method
    public static void main(String[] args) {
        StarBuilderTest starBuilderTest = new StarBuilderTest();
        Node[][] board = StarBuilder.buildStar(10);

        // Reverse the board array so that it matches our cordinate system
        for (int i = 0; i < board.length / 2; i++) {
            Node[] temp = board[i];
            board[i] = board[board.length - i - 1];
            board[board.length - i - 1] = temp;
        }

        // Loop through rows, then nodes of board
        for (Node[] row : board) {

            for (Node node : row) {
                if (node == null) {
                    System.out.print("  ");
                } else if (node instanceof CornerNode) {
                    System.out.print("()");
                } else {
                    System.out.print("[]");
                }
            }
            System.out.println();
        }
    }

}
