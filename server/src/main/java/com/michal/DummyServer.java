package com.michal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class DummyServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8085)) {
            System.out.println("Server started on port 8080");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());

                // Handle client in a new thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                String message = (String) in.readObject();
                System.out.println("Received: " + message);

                // Process commands
                if (message.startsWith("list")) {
                    out.writeObject("Available games: \n1. Game#1 (2/6 players)\n2. Game#2 (4/6 players)");
                    System.out.println("Sent list of games");
                } else if (message.startsWith("join")) {
                    out.writeObject("Joined game successfully!");
                } else if (message.startsWith("move")) {
                    out.writeObject("Move executed successfully!");
                } else {
                    out.writeObject("Unknown command: " + message);
                }
                out.flush();
            }
        } catch (Exception e) {
            System.out.println("Client disconnected");
        }
    }
}