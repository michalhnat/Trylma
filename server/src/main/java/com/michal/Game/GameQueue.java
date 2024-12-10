package com.michal.Game;

import java.util.Queue;

import com.michal.ClientHandler;

/**
 * Represents a queue of players in the game.
 */
public class GameQueue {
    private Queue<ClientHandler> players;

    /**
     * Constructs a GameQueue with an empty queue of players.
     */
    public GameQueue() {
        players = new java.util.LinkedList<>();
    }

    /**
     * Takes a player from the queue and re-adds them to the end of the queue.
     *
     * @return the player taken from the queue, or null if the queue is empty
     */
    public ClientHandler takePlayer() {
        ClientHandler player = players.poll();
        if (player != null) {
            players.offer(player);
        }
        return player;
    }

    /**
     * Adds a player to the queue.
     *
     * @param player the player to add to the queue
     */
    public void addPlayer(ClientHandler player) {
        players.add(player);
    }

    /**
     * Returns the number of players in the queue.
     *
     * @return the size of the queue
     */
    public int getSize() {
        return players.size();
    }

    /**
     * Checks if a player is in the queue.
     *
     * @param player the player to check
     * @return true if the player is in the queue, false otherwise
     */
    public boolean checkIfPlayerInQueue(ClientHandler player) {
        return players.contains(player);
    }

    /**
     * Removes a player from the queue.
     *
     * @param player the player to remove from the queue
     */
    public void removePlayer(ClientHandler player) {
        players.remove(player);
    }
}