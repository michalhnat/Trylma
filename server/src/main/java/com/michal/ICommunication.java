package com.michal;

import java.io.ObjectOutputStream;
import java.util.List;
import com.michal.Game.GameInfo;
import com.michal.Models.GameModel;
import com.michal.Models.GameMoves;

/**
 * An interface for communication operations.
 */
public interface ICommunication {
    /**
     * Sends a message to the specified output stream.
     *
     * @param msg the message to send
     * @param out the output stream to send the message to
     */
    void sendMessage(String msg, ObjectOutputStream out);

    /**
     * Sends a list of GameInfo objects as a message to the specified output stream.
     *
     * @param message the list of GameInfo objects to send
     * @param out the output stream to send the message to
     */
    void sendListMessage(List<GameInfo> message, ObjectOutputStream out);

    /**
     * Sends a GameInfo object as a message to the specified output stream.
     *
     * @param gameInfo the GameInfo object to send
     * @param out the output stream to send the message to
     */
    void sendGameInfo(GameInfo gameInfo, ObjectOutputStream out);

    /**
     * Sends the current state of the board to the specified output stream.
     *
     * @param board the board state to send
     * @param out the output stream to send the board state to
     */
    void sendBoard(String board, ObjectOutputStream out);

    /**
     * Sends an error message to the specified output stream.
     *
     * @param msg the error message to send
     * @param out the output stream to send the error message to
     */
    void sendError(String msg, ObjectOutputStream out);

    /**
     * Sends a list of GameSave objects as a message to the specified output stream.
     *
     * @param list the list of GameSave objects to send
     * @param out the output stream to send the message to
     */
    void sendSaveListMessage(List<GameSave> list, ObjectOutputStream out);

    /**
     * Sends a list of GameMoves objects representing the move history as a message to the specified output stream.
     *
     * @param moves the list of GameMoves objects to send
     * @param out the output stream to send the message to
     */
    void sendMoveHistory(List<GameMoves> moves, ObjectOutputStream out);
}