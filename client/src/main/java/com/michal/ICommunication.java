package com.michal;

import java.io.ObjectInputStream;
import java.net.InetAddress;

import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Exceptions.FailedSendingMessageToServer;

/**
 * Interface for communication with the server.
 */
public interface ICommunication {

    /**
     * Connects to the server using the specified address and port.
     *
     * @param address the server's InetAddress
     * @param port the server's port
     * @throws FailedConnectingToServerException if the connection to the server fails
     */
    public void connectToServer(InetAddress address, int port)
            throws FailedConnectingToServerException;

    /**
     * Sends a message to the server.
     *
     * @param message the message to send
     * @throws FailedSendingMessageToServer if sending the message fails
     */
    public void sendMessage(String message) throws FailedSendingMessageToServer;

    /**
     * Gets the input stream for receiving messages from the server.
     *
     * @return the ObjectInputStream for receiving messages
     */
    public ObjectInputStream getInputStream();

    /**
     * Checks if the client is connected to the server.
     *
     * @return true if connected, false otherwise
     */
    public boolean isConnected();
}