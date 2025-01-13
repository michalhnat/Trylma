package com.michal;

import java.io.ObjectOutputStream;
import java.util.List;
import com.michal.Game.GameInfo;

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

    void sendGameInfo(GameInfo gameInfo, ObjectOutputStream out);

    void sendBoard(String board, ObjectOutputStream out);

    /**
     * Sends an error message to the specified output stream.
     *
     * @param msg the error message to send
     * @param out the output stream to send the error message to
     */
    void sendError(String msg, ObjectOutputStream out);

    /**
     * Receives a message.
     *
     * @param msg the message received
     */
    void receive(String msg);
}
