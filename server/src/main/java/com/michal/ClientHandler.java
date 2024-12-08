package com.michal;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Mediator mediator;
    private final Communication_1 communication;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.mediator = Server.getInstance();
        this.communication = new SocketCommunication(socket);
    }

    public void send(String msg) {
        communication.sendMessage(msg, out);
    }

    @Override
    public void run() {
        try {
            // Inicjalizacja strumieni
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            System.out.println("Client connected: " + socket.getInetAddress());

            Object message;
            while ((message = in.readObject()) != null) {
                if (message instanceof String) {
                    String textMessage = (String) message;
                    System.out.println("Received: " + textMessage);
                    mediator.handleMessage(textMessage, this);
                } else {
                    System.err.println("Received unsupported message type: " + message.getClass().getName());
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error reading object from client: " + e.getMessage());
        } finally {
            Server.getInstance().removeClient(this);
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
