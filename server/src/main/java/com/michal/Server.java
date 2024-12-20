package com.michal;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.michal.Game.Board;
import com.michal.Game.GameInfo;
import com.michal.Game.GameSession;
import com.michal.Game.MockBoard;
import com.michal.Game.Player;
import com.michal.Game.Position;

public class Server implements Mediator, GameSessionMediator {
    private ServerSocket serverSocket;
    private static final int PORT = 8085;

    private List<GameSession> gameSessions = Collections.synchronizedList(new ArrayList<>());
    private List<ClientHandler> clients = Collections.synchronizedList(new ArrayList<>());

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
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

    public List<GameSession> getGameSessions() {
        return gameSessions;
    }

    @Override
    public void handleCreateGame(ClientHandler clientHandler, int players) {
        synchronized (gameSessions) {
            if (clientHandler.isInGame()) {
                clientHandler.sendError("Error: You are already in a game session.");
                return;
            }

            Board board = new MockBoard();

            try {
                GameSession session = new GameSession(board, players, this);
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

    @Override
    public void handleMove(ClientHandler clientHandler, int x, int y) {
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
                session.handleMove(player, new Position(x, y));
            } else {
                clientHandler.sendError("You are not part of any game session.");
            }
        }

    }

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
                        session.getGame().getMaxPlayers()));
            }

            clientHandler.sendListMessage(activeGames);
        }
    }

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

    @Override
    public void removeSession(GameSession session) {
        synchronized (gameSessions) {
            gameSessions.remove(session);
        }
    }
}
