package com.michal;

import com.michal.Game.GameInfo;

/**
 * Interface for communicating with a player.
 */
public interface PlayerCommunicator {

    /**
     * Sends a message to the player.
     *
     * @param message the message to send
     */
    void sendMessage(String message);

    /**
     * Sends an error message to the player.
     *
     * @param message the error message to send
     */
    void sendError(String message);

    /**
     * Sends the current state of the board to the player.
     *
     * @param board the board state to send
     */
    void sendBoard(String board);

    /**
     * Sends game information to the player.
     *
     * @param gameInfo the game information to send
     */
    void sendGameInfo(GameInfo gameInfo);
}