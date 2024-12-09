package com.michal.Game;

import java.util.Queue;

import com.michal.ClientHandler;

public class GameQueue {
    private Queue<ClientHandler> players;

    public GameQueue() {
        players = new java.util.LinkedList<>();
    }

    public ClientHandler takePlayer() {
        ClientHandler player = players.poll();
        if (player != null) {
            players.offer(player);
        }
        return player;
    }

    public void addPlayer(ClientHandler player) {
        players.add(player);
    }

    public int getSize() {
        return players.size();
    }

    public boolean checkIfPlayerInQueue(ClientHandler player) {
        return players.contains(player);
    }

    public void removePlayer(ClientHandler player) {
        players.remove(player);
    }
}
