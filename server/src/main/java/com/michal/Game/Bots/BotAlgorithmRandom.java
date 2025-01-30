package com.michal.Game.Bots;

import com.michal.Game.Board.Move;
import com.michal.Game.MoveValidation.MoveValidator;
import com.michal.Game.Board.Node;
import com.michal.Game.Player;
import com.michal.Game.Board.Position;

import java.util.ArrayList;

/**
 * Implements a random move algorithm for a bot player.
 */
public class BotAlgorithmRandom implements BotAlgorithm {
    private MoveValidator moveValidator;

    /**
     * Sets the move validator for the bot algorithm.
     *
     * @param moveValidator the move validator to set
     */
    public void setMoveValidator(MoveValidator moveValidator) {
        this.moveValidator = moveValidator;
    }

    /**
     * Makes a random move on the board using the bot algorithm.
     *
     * @param board the current state of the game board
     * @param botPlayer the bot player making the move
     * @return the move made by the bot
     */
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