package com.michal;

import java.util.UUID;

/**
 * An interface for handling various client actions in the game.
 */
public interface Mediator {

    /**
     * Handles the request to list available games for the specified client.
     *
     * @param clientHandler the client handler requesting the list of games
     */
    public void handleListGames(ClientHandler clientHandler);

    /**
     * Handles the request to join a game for the specified client.
     *
     * @param clientHandler the client handler requesting to join a game
     * @param gameId the ID of the game to join
     */
    public void handleJoinGame(ClientHandler clientHandler, int gameId);

    /**
     * Handles the request to create a new game for the specified client.
     *
     * @param clientHandler the client handler requesting to create a game
     * @param players the number of players for the new game
     */
    public void handleCreateGame(ClientHandler clientHandler, int players);

    /**
     * Handles the request to make a move in the game for the specified client.
     *
     * @param clientHandler the client handler making the move
     * @param x the x-coordinate of the move
     * @param y the y-coordinate of the move
     */
    public void handleMove(ClientHandler clientHandler, int x, int y);

    /**
     * Removes the specified client from the game.
     *
     * @param clientHandler the client handler to remove
     */
    public void removeClient(ClientHandler clientHandler);
}