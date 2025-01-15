package com.michal;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.michal.Game.*;

/**
 * The Server class implements the Mediator and GameSessionMediator interfaces
 * to manage game sessions and client connections.
 */
public class Server implements Mediator, GameSessionMediator {
    private static final int PORT = 8085;

    private final List<GameSession> gameSessions = Collections.synchronizedList(new ArrayList<>());
    private final List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    /**
     * Starts the server and listens for client connections.
     */
    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                var clientSocket = serverSocket.accept();
                var clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    /**
     * Returns the list of active game sessions.
     *
     * @return the list of game sessions
     */
    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    /**
     * Handles the creation of a new game session.
     *
     * @param clientHandler the client handler requesting the game creation
     * @param boardSize the size of the game board
     * @param layout the layout of the game board
     * @param variant the variant of the game
     */
    @Override
    public void handleCreateGame(ClientHandler clientHandler, int boardSize, Layout layout,
                                 Variant variant) {
        synchronized (gameSessions) {
            if (clientHandler.isInGame()) {
                clientHandler.sendError("Error: You are already in a game session.");
                return;
            }

            MoveValidator moveValidator;
            if (variant == Variant.SUPER) {
                moveValidator = new MoveValidatorSuper();
            }
            else {
                moveValidator = new MoveValidatorStandard();
            }
            Board board = new StarBoard(boardSize, moveValidator);

            try {
                GameSession session = new GameSession(board, layout, variant, this);
                synchronized (gameSessions) {
                    gameSessions.add(session);
                }
                clientHandler.sendMessage(
                        "Created a new game session with ID: " + session.getSessionId());
            } catch (IllegalArgumentException e) {
                clientHandler.sendError("Failed to create game session: " + e.getMessage());
            }
        }
    }

    /**
     * Handles a client joining an existing game session.
     *
     * @param clientHandler the client handler requesting to join a game
     * @param gameId the ID of the game session to join
     */
    @Override
    public void handleJoinGame(ClientHandler clientHandler, int gameId) {
        if (clientHandler.isInGame()) {
            clientHandler.sendError("Error: You are already in a game session.");
            return;
        }
        synchronized (gameSessions) {
            Optional<GameSession> sessionOptional = gameSessions.stream()
                    .filter(session -> session.getSessionId() == gameId).findFirst();

            if (sessionOptional.isPresent()) {
                GameSession session = sessionOptional.get();
                Player player = new Player(clientHandler.getId(), clientHandler);
                try {
                    session.addPlayer(player);
                    clientHandler.setPlayer(player);
                    clientHandler.setInGame(true);
                } catch (IllegalArgumentException e) {
                    clientHandler.sendError(
                            "Could not join game session " + gameId + ": " + e.getMessage());
                }
            } else {
                clientHandler.sendError("Game session " + gameId + " not found.");
            }
        }
    }

    /**
     * Handles a move made by a client in a game session.
     *
     * @param clientHandler the client handler making the move
     * @param start the starting position of the move
     * @param end the ending position of the move
     */
    @Override
    public void handleMove(ClientHandler clientHandler, Position start, Position end) {
        if (!clientHandler.isInGame()) {
            clientHandler.sendError("You are not part of any game session.");
            return;
        }

        synchronized (gameSessions) {
            Player player = clientHandler.getPlayer();
            Optional<GameSession> sessionOptional = gameSessions.stream()
                    .filter(session -> session.getPlayers().contains(player)).findFirst();

            if (sessionOptional.isPresent()) {
                GameSession session = sessionOptional.get();
                session.handleMove(player, start, end);
            } else {
                clientHandler.sendError("You are not part of any game session.");
            }
        }

    }

    /**
     * Handles a request from a client to list all active game sessions.
     *
     * @param clientHandler the client handler requesting the list of games
     */
    @Override
    public void handleListGames(ClientHandler clientHandler) {
        synchronized (gameSessions) {
            if (gameSessions.isEmpty()) {
                clientHandler.sendError("No active game sessions.");
                return;
            }

            List<GameInfo> activeGames = new ArrayList<>();
            for (GameSession session : gameSessions) {
                activeGames.add(new GameInfo(session.getSessionId(), session.getPlayers().size(),
                        session.getGame().getLayout(), session.getGame().getVariant(),
                        session.getGame().getStatus(), "None"));
            }

            clientHandler.sendListMessage(activeGames);
        }
    }

    /**
     * Removes a client from the server and any game session they are part of.
     *
     * @param client the client handler to remove
     */
    public void removeClient(ClientHandler client) {
        synchronized (clients) {
            clients.remove(client);
            if (client.isInGame()) {
                Player player = client.getPlayer();
                if (player != null) {
                    synchronized (gameSessions) {
                        for (GameSession session : new ArrayList<>(gameSessions)) {
                            if (session.getPlayers().contains(player)) {
                                session.removePlayer(player);
                                client.setInGame(false);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Removes a game session from the list of active sessions.
     *
     * @param session the game session to remove
     */
    @Override
    public void removeSession(GameSession session) {
        synchronized (gameSessions) {
            gameSessions.remove(session);
        }
    }

    /**
     * Handles a pass action from a client in a game session.
     *
     * @param clientHandler the client handler passing their turn
     */
    @Override
    public void handlePass(ClientHandler clientHandler) {
        if (!clientHandler.isInGame()) {
            clientHandler.sendError("You are not part of any game session.");
            return;
        }

        synchronized (gameSessions) {
            Player player = clientHandler.getPlayer();
            Optional<GameSession> sessionOptional = gameSessions.stream()
                    .filter(session -> session.getPlayers().contains(player)).findFirst();

            if (sessionOptional.isPresent()) {
                GameSession session = sessionOptional.get();
                session.handlePass(player);
            } else {
                clientHandler.sendError("You are not part of any game session.");
            }
        }
    }
}