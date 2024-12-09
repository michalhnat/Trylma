package com.michal.Game;

import com.michal.ClientHandler;
import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

public class Player {
    private final UUID id;
    private final ClientHandler clientHandler;
    private final String name;
    private final Set<Pone> pones;
    private GameSession gameSession;
    private String color;

    public Player(ClientHandler clientHandler) {
        this.id = UUID.randomUUID();
        this.clientHandler = clientHandler;
        this.name = "Player-" + id.toString().substring(0, 8);
        this.pones = new HashSet<>();
    }

    public void sendMessage(String message) {
        clientHandler.sendMessage(message);
    }

    public void sendError(String message) {
        clientHandler.sendError(message);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public Set<Pone> getPones() {
        return pones;
    }

    public void addPone(Pone pone) {
        pones.add(pone);
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    // New methods for color
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
