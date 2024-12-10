package com.michal.Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.michal.ClientHandler;
import com.michal.Server;

public class GameSession {
    private static int sessionCounter = 1;
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

    public synchronized void addPlayer(Player player) {
        if (players.size() >= game.getMaxPlayers()) {
            throw new IllegalArgumentException("Game session is full.");
        }

        if (availableColors.isEmpty()) {
            throw new IllegalArgumentException("No available colors for new players.");
        }

        String assignedColor = availableColors.poll();
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

    public synchronized void removePlayer(Player player) {
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

    private synchronized void startGame() {
        game.start();
        broadcastMessage("Game started!");
        promptNextPlayer();
    }

    public synchronized void handleMove(Player player, Position newPosition) {
        if (!game.isInProgress()) {
            player.sendError("Game is not in progress.");
            return;
        }

        try {
            game.move(player, newPosition);
            broadcastMessage("Player " + player.getName() + " moved to position ("
                    + newPosition.getX() + ", " + newPosition.getY() + ").");
            promptNextPlayer();
        } catch (Exception e) {
            player.sendError("Invalid move: " + e.getMessage());
        }
    }

    private synchronized void promptNextPlayer() {
        ClientHandler nextHandler = gameQueue.takePlayer();
        if (nextHandler != null) {
            Player nextPlayer = findPlayerByHandler(nextHandler);
            if (nextPlayer != null) {
                nextPlayer.sendMessage("Your turn to move.");
            }
        }
    }

    private synchronized Player findPlayerByHandler(ClientHandler handler) {
        for (Player p : players) {
            if (p.getClientHandler().equals(handler)) {
                return p;
            }
        }
        return null;
    }

    private synchronized void broadcastMessage(String message) {
        for (Player p : players) {
            p.sendMessage(message);
        }
    }

    public synchronized List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Game getGame() {
        return game;
    }
}
