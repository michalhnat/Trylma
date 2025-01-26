package com.michal.Game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import com.michal.GameSessionMediator;
import com.michal.Database.DatabaseConnector;
import com.michal.Models.GameModel;
import com.michal.Models.GameMoves;
import com.michal.Utils.BoardStringBuilder;
import jakarta.persistence.OptimisticLockException;

/**
 * Manages a game session, including players, game state, and communication.
 */
public class GameSession {
    private static final AtomicInteger sessionCounter = new AtomicInteger(1);
    private final int sessionId;
    private final Game game;
    private final List<Player> players;
    private final GameQueue gameQueue;
    private final GameSessionMediator server;
    private final Queue<String> availableColors;
    private final DatabaseConnector databaseConnector;
    private Player currentPlayer;
    private int currentMoveNumber = 0;
    private GameModel gameModel;

    private static final List<String> COLORS =
            List.of("Red", "Blue", "Green", "Yellow", "Purple", "Orange", "Cyan", "Magenta");

    /**
     * Constructs a GameSession with the specified board, layout, variant, and server.
     *
     * @param board the game board
     * @param layout the game layout
     * @param variant the game variant
     * @param server the server mediator
     * @param databaseConnector databaseConnector the database connector
     */
    public GameSession(Board board, Layout layout, Variant variant, GameSessionMediator server,
            DatabaseConnector databaseConnector) {
        this.sessionId = sessionCounter.getAndIncrement();
        this.game = new Game(board, layout, variant);
        this.players = new ArrayList<>();
        this.gameQueue = new GameQueue();
        this.server = server;
        this.availableColors = new LinkedList<>(COLORS);
        this.databaseConnector = databaseConnector;
    }

    /**
     * Returns the session ID.
     *
     * @return the session ID
     */
    public int getSessionId() {
        return sessionId;
    }

    /**
     * Adds a player to the game session.
     *
     * @param player the player to add
     * @throws IllegalArgumentException if the session is full or no colors are available
     */
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

    /**
     * Removes a player from the game session.
     *
     * @param player the player to remove
     */
    public synchronized void removePlayer(Player player) {
        if (players.remove(player)) {
            gameQueue.removePlayer(player);
            player.setGameSession(null);

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

    /**
     * Starts the game if the maximum number of players has been reached.
     */
    private synchronized void startGame() {
        game.start(players);

        GameModel gameModel = new GameModel();
        gameModel.setLayout(game.getLayout().toString());
        gameModel.setVariant(game.getVariant().toString());
        gameModel.setPlayer_count(players.size());
        gameModel.setStartTime(LocalDateTime.now());

        this.gameModel = gameModel;
        databaseConnector.saveGame(gameModel);

        broadcastMessage("Game started!");
        broadcastBoard(BoardStringBuilder.buildBoardString(game.getBoardArray()));

        promptNextPlayer();
    }

    /**
     * Handles a move made by a player.
     *
     * @param player the player making the move
     * @param start the starting position of the pawn
     * @param end the ending position of the pawn
     */
    public synchronized void handleMove(Player player, Position start, Position end) {
        if (game.isWaitingForPlayers()) {
            player.sendError("Game is not in progress.");
            return;
        }

        if (currentPlayer != player) {
            player.sendError("It's not your turn to move.");
            return;
        }

        try {
            game.move(player, start, end);
            broadcastMessage("Player " + player.getColor() + " moved");
            broadcastBoard(BoardStringBuilder.buildBoardString(game.getBoardArray()));

            currentMoveNumber++;
            GameMoves move = new GameMoves();
            move.setGame(gameModel);
            move.setMoveNumber(currentMoveNumber);
            move.setPlayerColor(player.getColor());
            move.setBoardAfterMove(BoardStringBuilder.buildBoardString(game.getBoardArray()));
            System.out.println(BoardStringBuilder.buildBoardString(game.getBoardArray()));
            move.setMoveTime(LocalDateTime.now());

            databaseConnector.saveGameMove(move);

            Player winner = game.checkIfSomeoneWon(gameQueue.getPlayers());
            if (winner != null) {
                broadcastMessage("Player " + winner.getColor() + " has won!");

                game.setStatus(GameStatus.FINISHED);

                for (Player p : players) {
                    p.sendGameInfo(new GameInfo(sessionId, players.size(), game.getLayout(),
                            game.getVariant(), game.getStatus(), winner.getColor())); // cheated
                                                                                      // gameinfo to
                                                                                      // send winner
                                                                                      // color,
                                                                                      // should be
                                                                                      // dedicated
                                                                                      // method for
                                                                                      // that
                    gameModel.setEndTime(LocalDateTime.now());
                    databaseConnector.saveGame(gameModel);
                }

                server.removeSession(this);

                return;
            }

            promptNextPlayer();

        } catch (OptimisticLockException e) {
            player.sendError("conflict at saving to db, retrying");

        } catch (Exception e) {
            player.sendError("Error: " + e.getMessage());
        }
    }

    /**
     * Prompts the next player to make a move.
     */
    private synchronized void promptNextPlayer() {
        Player nextPlayer = gameQueue.takePlayer();
        currentPlayer = nextPlayer;
        if (nextPlayer != null) {
            nextPlayer.sendMessage("Your turn to move.");

        }
    }

    /**
     * Broadcasts a message to all players in the session.
     *
     * @param message the message to broadcast
     */
    private synchronized void broadcastMessage(String message) {
        for (Player p : players) {
            p.sendMessage(message);
        }
    }

    /**
     * Broadcasts the current state of the game board to all players.
     *
     * @param board the board state to broadcast
     */
    private synchronized void broadcastBoard(String board) {
        for (Player p : players) {
            p.sendGameInfo(new GameInfo(sessionId, players.size(), game.getLayout(),
                    game.getVariant(), game.getStatus(), p.getColor()));
            p.sendBoard(board);
        }
    }

    /**
     * Returns a list of players in the session.
     *
     * @return a list of players
     */
    public synchronized List<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    /**
     * Returns the game associated with this session.
     *
     * @return the game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Handles a pass action by a player.
     *
     * @param player the player passing their turn
     */
    public void handlePass(Player player) {
        if (game.isWaitingForPlayers()) {
            player.sendError("Game is not in progress.");
            return;
        }

        broadcastMessage("Player " + player.getColor() + " passed.");
        promptNextPlayer();
    }
}
