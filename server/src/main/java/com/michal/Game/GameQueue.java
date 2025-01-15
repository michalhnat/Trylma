package com.michal.Game;

import java.util.List;
import java.util.Queue;

/**
 * Represents a queue of players in the game.
 */
public class GameQueue {
    private final Queue<Player> players;

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
    public synchronized Player takePlayer() {
        Player player = players.poll();
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
    public synchronized void addPlayer(Player player) {
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
     * Removes a player from the queue.
     *
     * @param player the player to remove from the queue
     */
    public synchronized void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * Returns an unmodifiable list of players in the queue.
     *
     * @return a list of players
     */
    public List<Player> getPlayers() {
        return List.copyOf(players);
    }
}