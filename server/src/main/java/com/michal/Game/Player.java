// Player.java
package com.michal.Game;

import com.michal.ClientHandler;
import java.util.UUID;

public class Player {
    private final UUID id;
    private final ClientHandler clientHandler;
    private final String name;

    public Player(ClientHandler clientHandler) {
        this.id = UUID.randomUUID();
        this.clientHandler = clientHandler;
        this.name = "Player-" + id.toString().substring(0, 8);
    }

    public void sendMessage(String message) {
        clientHandler.send(message);
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
}