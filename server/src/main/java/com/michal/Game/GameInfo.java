package com.michal.Game;

/**
 * Represents information about a game, including its ID, current number of players, and maximum
 * number of players.
 */
public class GameInfo {
    private final int id;
    private final int currentPlayers;
    private final int maxPlayers;
    private final Layout layout;
    private final Variant variant;
    private final GameStatus status;
    private final String players_color;

    /**
     * Constructs a GameInfo object with the specified ID, current number of players, layout, variant, status, and players' color.
     *
     * @param id the ID of the game
     * @param currentPlayers the current number of players in the game
     * @param layout the layout of the game
     * @param variant the variant of the game
     * @param status the status of the game
     * @param players_color the color of the players
     */
    public GameInfo(int id, int currentPlayers, Layout layout, Variant variant, GameStatus status,
                    String players_color) {
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = layout.getPlayers();
        this.layout = layout;
        this.variant = variant;
        this.status = status;
        this.players_color = players_color;
    }

    /**
     * Returns the status of the game.
     *
     * @return the game status
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Returns the color of the players.
     *
     * @return the players' color
     */
    public String getPlayers_color() {
        return players_color;
    }

    /**
     * Returns the ID of the game.
     *
     * @return the game ID
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the current number of players in the game.
     *
     * @return the current number of players
     */
    public int getCurrentPlayers() {
        return currentPlayers;
    }

    /**
     * Returns the maximum number of players allowed in the game.
     *
     * @return the maximum number of players
     */
    public int getMaxPlayers() {
        return maxPlayers;
    }

    /**
     * Returns the layout of the game as a string.
     *
     * @return the game layout
     */
    public String getLayout() {
        return layout.toString();
    }

    /**
     * Returns the variant of the game as a string.
     *
     * @return the game variant
     */
    public String getVariant() {
        return variant.toString();
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