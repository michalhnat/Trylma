package com.michal;

import com.michal.Game.Variant;
import com.michal.Game.Board.Layout;
import com.michal.Game.Board.Position;
import com.michal.Models.GameMoves;

import java.util.List;

/**
 * An interface for handling various client actions in the game.
 */
public interface Mediator {

    /**
     * Handles the request to list available games for the specified client.
     *
     * @param clientHandler the client handler requesting the list of games
     */
    void handleListGames(ClientHandler clientHandler);

    /**
     * Handles the request to join a game for the specified client.
     *
     * @param clientHandler the client handler requesting to join a game
     * @param gameId the ID of the game to join
     */
    void handleJoinGame(ClientHandler clientHandler, int gameId);

    /**
     * Handles the request to create a new game for the specified client.
     *
     * @param clientHandler the client handler requesting to create a game
     * @param boardSize the size of the game board
     * @param layout the layout of the game board
     * @param variant the variant of the game
     * @param gameMoves the initial game moves
     * @param loadedMoveHistory the move history loaded from a saved game
     */
    void handleCreateGame(ClientHandler clientHandler, int boardSize, Layout layout,
                          Variant variant, GameMoves gameMoves, List<GameMoves> loadedMoveHistory);

    /**
     * Handles the request to make a move in the game for the specified client.
     *
     * @param clientHandler the client handler making the move
     * @param start the starting position of the move
     * @param end the ending position of the move
     */
    void handleMove(ClientHandler clientHandler, Position start, Position end);

    /**
     * Removes the specified client from the game.
     *
     * @param clientHandler the client handler to remove
     */
    void removeClient(ClientHandler clientHandler);

    /**
     * Handles the request to pass the turn in the game for the specified client.
     *
     * @param clientHandler the client handler passing their turn
     */
    void handlePass(ClientHandler clientHandler);

    /**
     * Handles the request to add a bot to the game for the specified client.
     *
     * @param clientHandler the client handler requesting to add a bot
     */
    void handleAddBot(ClientHandler clientHandler);

    /**
     * Saves the current game session for the specified client.
     *
     * @param clientHandler the client handler requesting to save the game
     */
    void saveGame(ClientHandler clientHandler);

    /**
     * Handles the request to list saved games for the specified client.
     *
     * @param clientHandler the client handler requesting the list of saved games
     */
    void handleListSaves(ClientHandler clientHandler);

    /**
     * Loads a saved game session for the specified client.
     *
     * @param clientHandler the client handler requesting to load a game
     * @param saveId the ID of the saved game to load
     */
    void loadGame(ClientHandler clientHandler, int saveId);
}