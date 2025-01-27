package com.michal.Utils;

import com.michal.Game.Board.Node;
import com.michal.Game.Board.Pawn;
import com.michal.Game.Player;

import java.util.List;
import java.util.UUID;

public class BoardStringToNodeTest {
    public static void main(String[] args) {
        System.out.println("Test for BoardStringToNode class");

        Node[][] board = StarBuilder.buildStar(5);
        Player p1 = new Player(UUID.randomUUID(), null);
        Player p2 = new Player(UUID.randomUUID(), null);
        p1.setColor("Red");
        p2.setColor("Blue");
        board[4][3].setPawn(new Pawn(p1));
        board[4][4].setPawn(new Pawn(p1));
        board[4][5].setPawn(new Pawn(p2));
        board[4][6].setPawn(new Pawn(p2));

        System.out.println("Before conversion:");
        String stringBoard = BoardStringBuilder.buildBoardString(board);
        System.out.println(stringBoard);
        System.out.println("After conversion:");
        Node[][] newBoard = BoardStringToNode.stringToNode(stringBoard, List.of(p1, p2));
        String newStringBoard = BoardStringBuilder.buildBoardString(newBoard);
        System.out.println(newStringBoard);
    }
}
