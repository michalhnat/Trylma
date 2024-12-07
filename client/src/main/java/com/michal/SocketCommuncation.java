package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
// import java.io.ObjectInputStream;
// import java.io.ObjectOutputStream;

public class SocketCommuncation implements ICommunication {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean connected = false;

    public SocketCommuncation() {
    }

    public SocketCommuncation(InetAddress address, int port) {
        try {
            socket = new Socket(address, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectToServer(InetAddress address, int port) {
        try {
            socket = new Socket(address, port);
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }

    @Override
    public void sendMessage(String message) {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObjectInputStream getInputStream() {
        return in;
    }

    public boolean isConnected() {
        return connected;
    }

}
