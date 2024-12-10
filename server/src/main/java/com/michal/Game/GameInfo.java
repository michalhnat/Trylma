package com.michal.Game;

public class GameInfo {
    private int id;
    private int currentPlayers;
    private int maxPlayers;

    public GameInfo(int id, int currentPlayers, int maxPlayers) {
        this.id = id;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @Override
    public String toString() {
        return "GameInfo{" + "id=" + id + ", currentPlayers=" + currentPlayers + ", maxPlayers="
                + maxPlayers + '}';
    }

}
