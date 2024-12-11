package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.michal.Game.GameInfo;
import com.michal.Game.GameSession;
import com.michal.Game.Player;
import com.michal.Utils.JsonDeserializer;
import com.michal.Utils.MyLogger;

public class ClientHandler implements Runnable, PlayerCommunicator {
    private final Socket socket;
    private final Mediator mediator;
    private final ICommunication communication;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private volatile Player player;
    private volatile boolean inGame;
    private final UUID id;
    Logger logger = MyLogger.logger;
    static {
        MyLogger.loggerConfig();
    }

    public ClientHandler(Socket socket, Mediator mediator) {
        this.socket = socket;
        this.mediator = mediator;
        this.communication = new SocketCommunication(socket);
        this.inGame = false;
        this.id = UUID.randomUUID();
    }

    public synchronized UUID getId() {
        return id;
    }

    @Override
    public void sendMessage(String msg) {
        communication.sendMessage(msg, out);
    }

    public void sendListMessage(List<GameInfo> list) {
        communication.sendListMessage(list, out);
    }

    @Override
    public void sendError(String msg) {
        communication.sendError(msg, out);
    }

    public synchronized Player getPlayer() {
        return player;
    }

    public synchronized void setPlayer(Player player) {
        this.player = player;
    }

    public synchronized boolean isInGame() {
        return inGame;
    }

    public synchronized void setInGame(boolean inGame) {
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
                logger.warning("Error from client: " + errorMessage);
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
                        sendError("You are not part of any game session.");
                    }
                    break;
                default:
                    sendError("Unsupported command: " + command);
                    break;
            }
        } catch (JsonSyntaxException e) {
            sendError("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            sendError("Error processing message: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());

            logger.info("Client connected: " + socket.getInetAddress());

            Object message;
            while ((message = in.readObject()) != null) {
                if (message instanceof String) {
                    String textMessage = (String) message;
                    logger.info("Received: " + textMessage);

                    handleMessage(textMessage);
                } else {
                    logger.warning(
                            "Received unsupported message type: " + message.getClass().getName());
                }
            }
        } catch (IOException e) {
            logger.warning("Client disconnected");
        } catch (ClassNotFoundException e) {
            logger.warning("Error reading object from client: " + e.getMessage());
        } finally {
            mediator.removeClient(this);
            if (player != null && inGame) {
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
                logger.warning("Error closing socket: " + e.getMessage());
            }
        }
    }
}
