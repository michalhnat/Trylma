
package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Exceptions.FailedSendingMessageToServer;

public class SocketCommunication implements ICommunication {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean connected;

    public SocketCommunication() {
        this.connected = false;
    }

    public SocketCommunication(InetAddress address, int port)
            throws FailedConnectingToServerException {
        connectToServer(address, port);
    }

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

    public void sendMessage(String message) throws FailedSendingMessageToServer {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            throw new FailedSendingMessageToServer(
                    "Failed to send message to server: " + e.getMessage());
        }
    }

    public ObjectInputStream getInputStream() {
        return in;
    }

    public boolean isConnected() {
        return connected;
    }

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
                e.printStackTrace();
            }
        }).start();
    }
}
