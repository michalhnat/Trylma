package com.michal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketCommunication implements Communication_1 {
    private final Socket socket;

    public SocketCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void sendMessage(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(msg);
        } catch (IOException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public void receive(String msg) {
        System.out.println("Received from client: " + msg);
    }
}
