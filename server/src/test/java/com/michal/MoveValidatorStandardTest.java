package com.michal;

import com.michal.Game.Board.Node;
import com.michal.Game.Board.Pawn;
import com.michal.Game.Board.Position;
import com.michal.Game.MoveValidation.MoveValidator;
import com.michal.Game.MoveValidation.MoveValidatorStandard;
import com.michal.Utils.StarBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

public class MoveValidatorStandardTest {
    Node[][] board = StarBuilder.buildStar(5);
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    @Test
    public void main() {
        Node[][] board = StarBuilder.buildStar(5);

        board[5][5].setPawn(new Pawn(null));
        board[6][5].setPawn(new Pawn(null));
        board[7][6].setPawn(new Pawn(null));
        board[8][6].setPawn(new Pawn(null));
        board[10][8].setPawn(new Pawn(null));
        board[12][10].setPawn(new Pawn(null));
        board[11][10].setPawn(new Pawn(null));
        board[4][4].setPawn(new Pawn(null));

        MoveValidator moveValidator = new MoveValidatorStandard();

        HashSet<Position> validMoves = moveValidator.getValidMoves(board, new Position(5, 5));


        // Display the board with valid moves
        for (int y = 16; y >= 0; y--) {
            for (int x = 0; x <= 16; x++) {
                if (board[x][y] == null) {
                    System.out.print("BB");
                } else if (x == 5 && y == 5) {
                    System.out.print(ANSI_RED + "()" + ANSI_RESET);
                } else if (board[x][y].getPawn() != null) {
                    System.out.print(ANSI_GREEN + "[]" + ANSI_RESET);
                } else if (validMoves.contains(new Position(x, y))) {
                    System.out.print(ANSI_WHITE + "()" + ANSI_RESET);
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }

        for (Position move : validMoves) {
            System.out.println(move.x() + " " + move.y());
        }

        Position expectedValidMove = new Position(0, 0);
        if (validMoves.contains(new Position(13, 11))) {
            expectedValidMove = new Position(13, 11);
        }
        Assertions.assertEquals(expectedValidMove, new Position(13, 11));

    }
}
