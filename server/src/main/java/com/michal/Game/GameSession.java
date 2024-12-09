package com.michal.Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.Queue;

import com.michal.ClientHandler;
import com.michal.Server;

public class GameSession {
    private static int sessionCounter = 1; // Static counter for unique session IDs
    private final int sessionId;
    private final Game game;
    private final List<Player> players;
    private final GameQueue gameQueue;
    private final Server server;
    private final Queue<String> availableColors;

    private static final List<String> COLORS =
            List.of("Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Cyan", "Magenta");

    public GameSession(Board board, int maxPlayers) {
        this.sessionId = sessionCounter++;
        this.game = new Game(board, maxPlayers);
        this.players = new ArrayList<>();
        this.gameQueue = new GameQueue();
        this.server = null;
        this.availableColors = new LinkedList<>(COLORS);
    }

    public int getSessionId() {
        return sessionId;
    }

    public void addPlayer(Player player) {
        synchronized (players) {
            if (players.size() >= game.getMaxPlayers()) {
                throw new IllegalArgumentException("Game session is full.");
            }

            if (availableColors.isEmpty()) {
                throw new IllegalArgumentException("No available colors for new players.");
            }

            String assignedColor = availableColors.poll(); // Assign next available color
            player.setColor(assignedColor);

            players.add(player);
            gameQueue.addPlayer(player.getClientHandler());
            player.setGameSession(this);
            player.getClientHandler().setInGame(true);

            broadcastMessage("Player " + player.getName() + " has joined the game with color "
                    + assignedColor + ".");

            if (players.size() == game.getMaxPlayers()) {
                startGame();
            }
        }
    }

    public void removePlayer(Player player) {
        synchronized (players) {
            if (players.remove(player)) {
                gameQueue.removePlayer(player.getClientHandler());
                player.setGameSession(null);
                player.getClientHandler().setInGame(false);

                // Recycle the player's color
                String color = player.getColor();
                if (color != null) {
                    availableColors.offer(color);
                }

                broadcastMessage("Player " + player.getName() + " has left the game.");

                if (players.isEmpty()) {
                    if (server != null) {
                        server.removeSession(this);
                    }
                }
            }
        }
    }

    private void startGame() {
        game.start();
        broadcastMessage("Game started!");
        promptNextPlayer();
    }

    public void handleMove(Player player, Position newPosition) {
        if (!game.isInProgress()) {
            player.sendMessage("Game is not in progress.");
            return;
        }

        try {
            game.move(player, newPosition);
            broadcastMessage("Player " + player.getName() + " moved to position ("
                    + newPosition.getX() + ", " + newPosition.getY() + ").");
            promptNextPlayer();
        } catch (Exception e) {
            player.sendMessage("Invalid move: " + e.getMessage());
        }
    }

    private void promptNextPlayer() {
        ClientHandler nextHandler = gameQueue.takePlayer();
        if (nextHandler != null) {
            Player nextPlayer = findPlayerByHandler(nextHandler);
            if (nextPlayer != null) {
                nextPlayer.sendMessage("Your turn to move.");
            }
        }
    }

    private Player findPlayerByHandler(ClientHandler handler) {
        for (Player p : players) {
            if (p.getClientHandler().equals(handler)) {
                return p;
            }
        }
        return null;
    }

    private void broadcastMessage(String message) {
        for (Player p : players) {
            p.sendMessage(message);
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Game getGame() {
        return game;
    }
}
