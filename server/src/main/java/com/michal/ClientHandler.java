package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.michal.Game.GameSession;
import com.michal.Game.Player;
import com.michal.Utils.JsonDeserializer;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Mediator mediator;
    private final ICommunication communication;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Player player;
    private boolean inGame;

    public ClientHandler(Socket socket, Mediator mediator) {
        this.socket = socket;
        this.mediator = mediator;
        this.communication = new SocketCommunication(socket);
        this.inGame = false;
    }

    public void send(String msg) {
        communication.sendMessage(msg, out);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

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

            switch (command.toLowerCase()) {
                case "list":
                    mediator.handleListGames(this);
                    break;
                case "join":
                    int gameId = payload.get("gameID").getAsInt();
                    mediator.handleJoinGame(this, gameId);
                    break;
                case "create":
                    int players = payload.get("players").getAsInt();
                    mediator.handleCreateGame(this, players);
                    break;
                case "move":
                    int x = payload.get("x").getAsInt();
                    int y = payload.get("y").getAsInt();
                    if (isInGame()) {
                        mediator.handleMove(this, x, y);
                    } else {
                        send("You are not part of any game session.");
                    }
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
                    System.err.println(
                            "Received unsupported message type: " + message.getClass().getName());
                }
            }
        } catch (IOException e) {
            System.err.println("Client disconnected");
        } catch (ClassNotFoundException e) {
            System.err.println("Error reading object from client: " + e.getMessage());
        } finally {
            mediator.removeClient(this);
            if (player != null && inGame) {
                // Remove player from their game session
                synchronized (mediator) {
                    List<GameSession> sessions = ((Server) mediator).getGameSessions();
                    for (GameSession session : sessions) {
                        if (session.getPlayers().contains(player)) {
                            session.removePlayer(player);
                            setInGame(false);
                            break;
                        }
                    }
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
    }
}
