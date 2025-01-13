package com.michal.Game;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import com.michal.ClientHandler;
import com.michal.PlayerCommunicator;

public class Player {
    private final UUID id;
    private final PlayerCommunicator communicator;
    private final String name;
    private final Set<Pawn> pawns;
    private GameSession gameSession;
    private String color;

    public Player(UUID id, PlayerCommunicator communicator) {
        this.id = id;
        this.communicator = communicator;
        this.name = "Player-" + id.toString().substring(0, 8);
        this.pawns = new HashSet<>();
    }

    public synchronized void sendMessage(String message) {
        communicator.sendMessage(message);
    }

    public synchronized void sendError(String message) {
        communicator.sendError(message);
    }

    public synchronized void sendBoard(String board) {
        communicator.sendBoard(board);
    }

    public void sendGameInfo(GameInfo gameInfo) {
        communicator.sendGameInfo(gameInfo);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PlayerCommunicator getcommunicator() {
        return communicator;
    }

    public Set<Pawn> getPawns() {
        return pawns;
    }

    public void addPawn(Pawn pawn) {
        pawns.add(pawn);
    }

    public GameSession getGameSession() {
        return gameSession;
    }

    public void setGameSession(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
