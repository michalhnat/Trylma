package com.michal.Game.Bots;

import com.michal.Game.*;
import com.michal.Game.Board.Move;
import com.michal.Game.Board.Node;
import com.michal.Game.MoveValidation.MoveValidatorStandard;
import com.michal.Game.MoveValidation.MoveValidatorSuper;

import java.util.UUID;

public class BotPlayer extends Player {
    BotAlgorithm botAlgorithm;

    public BotPlayer(UUID id, BotAlgorithm botAlgorithm) {
        super(id, null);
        this.botAlgorithm = botAlgorithm;
    }

    public Move makeMove(Node[][] board) {
        return botAlgorithm.makeMove(board, this);
    }

    @Override
    public void sendMessage(String message) {
        // Do nothing
    }

    @Override
    public void sendError(String message) {
        // Do nothing
    }

    @Override
    public void sendGameInfo(GameInfo gameInfo) {
        // Do nothing
    }

    @Override
    public void sendBoard(String board) {
        // Do nothing
    }

    public void setVariant(Variant variant) {
        if (variant == Variant.SUPER) {
            botAlgorithm.setMoveValidator(new MoveValidatorSuper());
        }
        else {
            botAlgorithm.setMoveValidator(new MoveValidatorStandard());
        }
    }
}
