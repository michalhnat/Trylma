package com.michal.Game.Bots;

import com.michal.Game.Board.Move;
import com.michal.Game.MoveValidation.MoveValidator;
import com.michal.Game.Board.Node;
import com.michal.Game.Player;

public interface BotAlgorithm {
    public Move makeMove(Node[][] board, Player botPlayer);

    public void setMoveValidator(MoveValidator moveValidator);
}
