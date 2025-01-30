package com.michal.Game.Bots;

import com.michal.Game.*;
import com.michal.Game.Board.Move;
import com.michal.Game.Board.Node;
import com.michal.Game.MoveValidation.MoveValidatorStandard;
import com.michal.Game.MoveValidation.MoveValidatorSuper;
import com.michal.Models.GameMoves;

import java.util.List;
import java.util.UUID;

/**
 * Represents a bot player in the game.
 */
public class BotPlayer extends Player {
    private BotAlgorithm botAlgorithm;
    private String color;

    /**
     * Constructs a BotPlayer with the specified ID and bot algorithm.
     *
     * @param id the unique identifier of the bot player
     * @param botAlgorithm the algorithm used by the bot to make moves
     */
    public BotPlayer(UUID id, BotAlgorithm botAlgorithm) {
        super(id, null);
        this.botAlgorithm = botAlgorithm;
    }

    /**
     * Makes a move on the board using the bot algorithm.
     *
     * @param board the current state of the game board
     * @return the move made by the bot
     */
    public Move makeMove(Node[][] board) {
        return botAlgorithm.makeMove(board, this);
    }

    /**
     * Does nothing as bot players do not receive messages.
     *
     * @param message the message to send
     */
    @Override
    public void sendMessage(String message) {
        // Do nothing
    }

    /**
     * Does nothing as bot players do not receive error messages.
     *
     * @param message the error message to send
     */
    @Override
    public void sendError(String message) {
        // Do nothing
    }

    /**
     * Does nothing as bot players do not receive game information.
     *
     * @param gameInfo the game information to send
     */
    @Override
    public void sendGameInfo(GameInfo gameInfo) {
        // Do nothing
    }

    /**
     * Does nothing as bot players do not receive board states.
     *
     * @param board the board state to send
     */
    @Override
    public void sendBoard(String board) {
        // Do nothing
    }

    /**
     * Does nothing as bot players do not receive move history.
     *
     * @param loadedMoveHistory the list of game moves to send
     */
    @Override
    public void sendMoveHistory(List<GameMoves> loadedMoveHistory) {
        // Do nothing
    }

    /**
     * Sets the move validator for the bot based on the game variant.
     *
     * @param variant the game variant
     */
    public void setVariant(Variant variant) {
        if (variant == Variant.SUPER) {
            botAlgorithm.setMoveValidator(new MoveValidatorSuper());
        }
        else {
            botAlgorithm.setMoveValidator(new MoveValidatorStandard());
        }
    }

    /**
     * Returns the color assigned to the bot player.
     *
     * @return the color assigned to the bot player
     */
    @Override
    public String getColor() {
        return color;
    }

    /**
     * Sets the color for the bot player.
     *
     * @param color the color to set
     */
    @Override
    public void setColor(String color) {
        this.color = color;
    }
}