package com.michal.Utils;

import com.michal.Game.Node;

public class BoardStringBuilderTest {

    public static void main(String[] args) {

        Node[][] board = StarBuilder.buildStar(5);
        String string = BoardStringBuilder.buildBoardString(board);

        System.out.println(string);
    }
}
