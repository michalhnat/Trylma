package com.michal.Game.Bots;

import com.michal.Game.Board.Move;
import com.michal.Game.MoveValidation.MoveValidator;
import com.michal.Game.Board.Node;
import com.michal.Game.Player;
import com.michal.Game.Board.Position;

import java.util.ArrayList;

public class BotAlgorithmRandom implements BotAlgorithm {
    private MoveValidator moveValidator;

    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }

    @Override
    public Move makeMove(Node[][] board, Player botPlayer) {
        ArrayList<Move> possibleMoves = new ArrayList<>();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Node node = board[i][j];
                Position nodePosition = new Position(i, j);

                if (node == null) {
                    continue;
                }
                if (node.getPawn() == null) {
                    continue;
                }
                if (!(node.getPawn().getPlayer() == botPlayer)) {
                    continue;
                }

                moveValidator.getValidMoves(board, nodePosition).forEach(move -> {
                    possibleMoves.add(new Move(nodePosition, move));
                });
            }
        }

        return possibleMoves.get((int) (Math.random() * possibleMoves.size()));
    }
}