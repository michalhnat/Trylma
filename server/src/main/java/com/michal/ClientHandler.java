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
import com.michal.Game.*;
import com.michal.Game.Board.Layout;
import com.michal.Game.Board.Position;
import com.michal.Models.GameModel;
import com.michal.Utils.JsonDeserializer;
import com.michal.Utils.MyLogger;

/**
 * Handles communication with a client and processes client requests.
 */
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

    /**
     * Constructs a ClientHandler with the specified socket and mediator.
     *
     * @param socket the socket for client communication
     * @param mediator the mediator for handling game actions
     */
    public ClientHandler(Socket socket, Mediator mediator) {
        this.socket = socket;
        this.mediator = mediator;
        this.communication = new SocketCommunication();
        this.inGame = false;
        this.id = UUID.randomUUID();
    }

    /**
     * Returns the unique identifier of the client.
     *
     * @return the UUID of the client
     */
    public synchronized UUID getId() {
        return id;
    }

    /**
     * Sends a message to the client.
     *
     * @param msg the message to send
     */
    @Override
    public void sendMessage(String msg) {
        communication.sendMessage(msg, out);
    }

    /**
     * Sends a list of game information to the client.
     *
     * @param list the list of game information to send
     */
    public void sendListMessage(List<GameInfo> list) {
        communication.sendListMessage(list, out);
    }

    public void sendSaveListMessage(List<GameSave> list) {
        communication.sendSaveListMessage(list, out);
    }

    /**
     * Sends the current state of the game board to the client.
     *
     * @param board the board state to send
     */
    public void sendBoard(String board) {
        communication.sendBoard(board, out);
    }

    /**
     * Sends game information to the client.
     *
     * @param gameInfo the game information to send
     */
    public void sendGameInfo(GameInfo gameInfo) {
        communication.sendGameInfo(gameInfo, out);
    }

    /**
     * Sends an error message to the client.
     *
     * @param msg the error message to send
     */
    @Override
    public void sendError(String msg) {
        communication.sendError(msg, out);
    }

    /**
     * Returns the player associated with the client.
     *
     * @return the player associated with the client
     */
    public synchronized Player getPlayer() {
        return player;
    }

    /**
     * Sets the player associated with the client.
     *
     * @param player the player to associate with the client
     */
    public synchronized void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Checks if the client is currently in a game.
     *
     * @return true if the client is in a game, false otherwise
     */
    public synchronized boolean isInGame() {
        return inGame;
    }

    /**
     * Sets the in-game status of the client.
     *
     * @param inGame the in-game status to set
     */
    public synchronized void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    /**
     * Handles a message received from the client.
     *
     * @param msg the message received from the client
     */
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
                    Layout layout = Layout.valueOf(payload.get("layout").getAsString());
                    Variant variant = Variant.valueOf(payload.get("variant").getAsString());
                    int boardSize = payload.get("boardSize").getAsInt();
                    mediator.handleCreateGame(this, boardSize, layout, variant);
                    break;
                case "move":
                    int start_x = payload.get("start_x").getAsInt();
                    int start_y = payload.get("start_y").getAsInt();
                    Position start = new Position(start_x, start_y);

                    int end_x = payload.get("end_x").getAsInt();
                    int end_y = payload.get("end_y").getAsInt();
                    Position end = new Position(end_x, end_y);

                    if (isInGame()) {
                        mediator.handleMove(this, start, end);
                    } else {
                        sendError("You are not part of any game session.");
                    }
                    break;
                case "pass":
                    if (isInGame()) {
                        mediator.handlePass(this);
                    } else {
                        sendError("You are not part of any game session.");
                    }
                    break;
                case "add_bot":
                    if (isInGame()) {
                        mediator.handleAddBot(this);
                    } else {
                        sendError("You are not part of any game session.");
                    }
                    break;
                case "save_game":
                    if (isInGame()) {
                        mediator.saveGame(this);
                    } else {
                        sendError("You are not part of any game session.");
                    }
                    break;
                case "list_saves":
                    mediator.handleListSaves(this);
                    break;
                default:
                    sendError("Unsupported command: " + command);
                    break;
            }
        } catch (JsonSyntaxException e) {
            sendError("Invalid JSON format: " + e.getMessage());
        } catch (Exception e) {
            sendError("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Runs the client handler, processing incoming messages from the client.
     */
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
