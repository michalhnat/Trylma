package com.michal.Game;

/**
 * Represents information about a game, including its ID, current number of players, and maximum number of players.
 */
public class GameInfo {
    private int id;
    private int currentPlayers;
    private int maxPlayers;

    /**
     * Constructs a GameInfo object with the specified ID, current number of players, and maximum number of players.
     *
     * @param id the ID of the game
     * @param currentPlayers the current number of players in the game
     * @param maxPlayers the maximum number of players allowed in the game
     */
    public GameInfo(int id, int currentPlayers, int maxPlayers) {
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns the ID of the game.
     *
     * @return the ID of the game
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the game.
     *
     * @param id the new ID of the game
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the current number of players in the game.
     *
     * @return the current number of players in the game
     */
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * Sets the current number of players in the game.
     *
     * @param currentPlayers the new current number of players in the game
     */
    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    /**
     * Returns the maximum number of players allowed in the game.
     *
     * @return the maximum number of players allowed in the game
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Sets the maximum number of players allowed in the game.
     *
     * @param maxPlayers the new maximum number of players allowed in the game
     */
    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    /**
     * Returns a string representation of the GameInfo object.
     *
     * @return a string representation of the GameInfo object
     */
    @Override
    public String toString() {
        return "GameInfo{" + "id=" + id + ", currentPlayers=" + currentPlayers + ", maxPlayers="
                + maxPlayers + '}';
    }
}