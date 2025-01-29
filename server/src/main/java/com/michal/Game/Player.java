package com.michal.Game;

import java.util.List;
import java.util.UUID;

import com.michal.Models.GameMoves;
import com.michal.PlayerCommunicator;

/**
 * Represents a player in the game.
 */
public class Player {
    private final UUID id;
    private final PlayerCommunicator communicator;
    private final String name;
    private GameSession gameSession;
    private String color;

    /**
     * Constructs a Player with the specified ID and communicator.
     *
     * @param id the unique identifier of the player
     * @param communicator the communicator for the player
     */
    public Player(UUID id, PlayerCommunicator communicator) {
        this.id = id;
        this.communicator = communicator;
        this.name = "Player-" + id.toString().substring(0, 8);
    }

    /**
     * Copy constructor for creating a new Player based on an existing one.
     *
     * @param player the player to copy
     */
    public Player(Player player) {
        this.id = player.id;
        this.communicator = player.communicator;
        this.name = player.name;
        this.gameSession = player.gameSession;
        this.color = player.color;
    }

    /**
     * Sends a message to the player.
     *
     * @param message the message to send
     */
    public synchronized void sendMessage(String message) {
        communicator.sendMessage(message);
    }

    /**
     * Sends an error message to the player.
     *
     * @param message the error message to send
     */
    public synchronized void sendError(String message) {
        communicator.sendError(message);
    }

    /**
     * Sends the current state of the board to the player.
     *
     * @param board the board state to send
     */
    public synchronized void sendBoard(String board) {
        communicator.sendBoard(board);
    }

    /**
     * Sends game information to the player.
     *
     * @param gameInfo the game information to send
     */
    public void sendGameInfo(GameInfo gameInfo) {
        communicator.sendGameInfo(gameInfo);
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the game session for the player.
     *
     * @param gameSession the game session to set
     */
    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    /**
     * Returns the color assigned to the player.
     *
     * @return the color assigned to the player
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color for the player.
     *
     * @param color the color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    public void sendMoveHistory(List<GameMoves> loadedMoveHistory) {
        communicator.sendMoveHistory(loadedMoveHistory);
    }
}