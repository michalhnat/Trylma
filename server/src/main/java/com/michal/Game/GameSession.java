package com.michal.Game;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import com.michal.GameSessionMediator;
import com.michal.Utils.BoardStringBuilder;
import com.michal.Utils.MyLogger;

public class GameSession {
    private static final AtomicInteger sessionCounter = new AtomicInteger(1);
    private final int sessionId;
    private final Game game;
    private final List<Player> players;
    private final GameQueue gameQueue;
    private final GameSessionMediator server;
    private final Queue<String> availableColors;
    Logger logger = MyLogger.logger;
    static {
        MyLogger.loggerConfig();
    }

    private static final List<String> COLORS =
            List.of("Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Cyan", "Magenta");

    public GameSession(Board board, Layout layout, Variant variant, GameSessionMediator server) {
        this.sessionId = sessionCounter.getAndIncrement();
        this.game = new Game(board, layout, variant);
        this.players = new ArrayList<>();
        this.gameQueue = new GameQueue();
        this.server = server;
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
        gameQueue.addPlayer(player);
        player.setGameSession(this);
        // player.getClientHandler().setInGame(true);

        broadcastMessage("Player " + player.getName() + " has joined the game with color "
                + assignedColor + ".");

        for (Player p : players) {
            p.sendGameInfo(new GameInfo(sessionId, players.size(), game.getLayout(),
                    game.getVariant(), game.getStatus(), p.getColor()));
        }

        if (players.size() == game.getMaxPlayers()) {
            startGame();
        }
    }

    public synchronized void removePlayer(Player player) {
        if (players.remove(player)) {
            gameQueue.removePlayer(player);
            player.setGameSession(null);
            // player.getClientHandler().setInGame(false);

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
        game.start(players);
        // if (game.getBoard() == null) {
        // System.out.println("Board is null");
        // }
        broadcastMessage("Game started!");
        broadcastBoard(BoardStringBuilder.buildBoardString(game.getBoardArray()));
        promptNextPlayer();
    }

    public synchronized void handleMove(Player player, Position start, Position end) {
        if (!game.isInProgress()) {
            player.sendError("Game is not in progress.");
            return;
        }

        try {
            game.move(player, start, end);
            broadcastMessage("Player " + player.getColor() + " moved");
            broadcastBoard(BoardStringBuilder.buildBoardString(game.getBoardArray()));

            Player winner = game.checkIfSomeoneWon(gameQueue.getPlayers());
            if (winner != null) {
                broadcastMessage("Player " + winner.getColor() + " has won!");
                gameQueue.removePlayer(winner);
                if (gameQueue.getSize() == 1) {
                    broadcastMessage("The game has ended");
                    server.removeSession(this);
                }
            }

            promptNextPlayer();
        } catch (Exception e) {
            player.sendError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private synchronized void promptNextPlayer() {
        Player nextPlayer = gameQueue.takePlayer();
        if (nextPlayer != null) {
            nextPlayer.sendMessage("Your turn to move.");
        }
    }

    private synchronized void broadcastMessage(String message) {
        for (Player p : players) {
            p.sendMessage(message);
        }
    }

    private synchronized void broadcastBoard(String board) {
        for (Player p : players) {
            p.sendGameInfo(new GameInfo(sessionId, players.size(), game.getLayout(),
                    game.getVariant(), game.getStatus(), p.getColor()));
            p.sendBoard(board);
        }
    }

    public synchronized List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public Game getGame() {
        return game;
    }

    public void handlePass(Player player) {
        if (!game.isInProgress()) {
            player.sendError("Game is not in progress.");
            return;
        }

        broadcastMessage("Player " + player.getColor() + " passed.");
        promptNextPlayer();
    }
}
