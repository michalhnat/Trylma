package com.michal;

import java.io.*;
import java.net.Socket;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.michal.Utils.JsonDeserializer;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Mediator mediator;
    private final ICommunication communication;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket, Mediator mediator) {
        this.socket = socket;
        this.mediator = mediator;
        this.communication = new SocketCommunication(socket);
    }

    public void send(String msg) {
        communication.sendMessage(msg, out);
    }

    // TODO moze da sie to jakos ladniej zrobic
    private void handleMessage(String msg) {
        try {
            JsonDeserializer deserializer = JsonDeserializer.getInstance();

            String command = deserializer.getCommand(msg);
            JsonObject payload = deserializer.getPayload(msg);
            boolean isError = deserializer.isError(msg);
            String errorMessage = deserializer.getErrorMessage(msg);

            if (isError) {
                System.err.println("Error from client: " + errorMessage);
                return;
            }

            // Process commands
            switch (command.toLowerCase()) {
                case "list":
                    mediator.handleListGames(this);
                    break;
                case "join":
                    String gameId = payload.get("gameID").getAsString();
                    UUID gameUUID = UUID.fromString(gameId);
                    mediator.handleJoinGame(this, gameUUID);
                    break;
                case "create":
                    int players = payload.get("players").getAsInt();
                    mediator.handleCreateGame(this, players);
                    break;
                case "move":
                    int x = payload.get("x").getAsInt();
                    int y = payload.get("y").getAsInt();
                    mediator.handleMove(this, x, y);
                    break;
                default:
                    send("Unsupported command: " + command);
                    break;
            }
        } catch (JsonSyntaxException e) {
            send("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            send("Error processing message: " + e.getMessage());
        }
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

                    handleMessage(textMessage);

                } else {
                    System.err.println("Received unsupported message type: " + message.getClass().getName());
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error reading object from client: " + e.getMessage());
        } finally {
            mediator.removeClient(this);
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
