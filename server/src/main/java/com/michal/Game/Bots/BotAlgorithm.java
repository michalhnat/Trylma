package com.michal.Game.Bots;

import com.michal.Game.Board.Move;
import com.michal.Game.MoveValidation.MoveValidator;
import com.michal.Game.Board.Node;
import com.michal.Game.Player;

/**
 * Interface for bot algorithms.
 */
public interface BotAlgorithm {

    /**
     * Makes a move on the board using the bot algorithm.
     *
     * @param board the current state of the game board
     * @param botPlayer the bot player making the move
     * @return the move made by the bot
     */
    public Move makeMove(Node[][] board, Player botPlayer);

    /**
     * Sets the move validator for the bot algorithm.
     *
     * @param moveValidator the move validator to set
     */
    public void setMoveValidator(MoveValidator moveValidator);
}