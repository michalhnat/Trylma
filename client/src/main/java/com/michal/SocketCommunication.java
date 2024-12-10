package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Exceptions.FailedSendingMessageToServer;

/**
 * Class that handles socket communication with the server.
 */
public class SocketCommunication implements ICommunication {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean connected;

    /**
     * Default constructor for SocketCommunication.
     * Initializes the connected state to false.
     */
    public SocketCommunication() {
        this.connected = false;
    }

    /**
     * Constructs a new SocketCommunication instance and connects to the server.
     *
     * @param address the server's InetAddress
     * @param port the server's port
     * @throws FailedConnectingToServerException if the connection to the server fails
     */
    public SocketCommunication(InetAddress address, int port)
            throws FailedConnectingToServerException {
        connectToServer(address, port);
    }

    /**
     * Connects to the server using the specified address and port.
     *
     * @param address the server's InetAddress
     * @param port the server's port
     * @throws FailedConnectingToServerException if the connection to the server fails
     */
    public void connectToServer(InetAddress address, int port)
            throws FailedConnectingToServerException {
        try {
            this.socket = new Socket(address, port);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.connected = true;
            startConnectionMonitor();
        } catch (IOException e) {
            throw new FailedConnectingToServerException(
                    "Failed to connect to server: " + e.getMessage());
        }
    }

    /**
     * Sends a message to the server.
     *
     * @param message the message to send
     * @throws FailedSendingMessageToServer if sending the message fails
     */
    public void sendMessage(String message) throws FailedSendingMessageToServer {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            throw new FailedSendingMessageToServer(
                    "Failed to send message to server: " + e.getMessage());
        }
    }

    /**
     * Gets the input stream for receiving messages from the server.
     *
     * @return the ObjectInputStream for receiving messages
     */
    public ObjectInputStream getInputStream() {
        return in;
    }

    /**
     * Checks if the client is connected to the server.
     *
     * @return true if connected, false otherwise
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Starts a thread to monitor the connection status.
     * Sets the connected state to false if the socket is closed or not connected.
     */
    private void startConnectionMonitor() {
        new Thread(() -> {
            try {
                while (connected) {
                    if (socket.isClosed() || !socket.isConnected()) {
                        connected = false;
                    }
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }).start();
    }
}