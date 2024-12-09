package com.michal.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.michal.ClientHandler;

public class Game {
    private UUID gameId = UUID.randomUUID();
    private int maxPlayers;
    private GameQueue gameQueue = new GameQueue();
    private GameStatus gameStatus;
    private Board board;
    // private static int gameCounter = 0;
    // private int id;
    // private int maxPlayers;
    // private List<ClientHandler> players;
    // private int activePlayerIndex;

    // private Random rand = new Random();
    // private boolean inProgress;
    // TODO Ustalenie kolejności graczy

    public Game(int maxPlayers) {
        this.maxPlayers = maxPlayers;
        this.gameStatus = GameStatus.WAITING;
        // this.id = ++gameCounter;
        // this.maxPlayers = maxPlayers;
        // this.players = new ArrayList<>();
        // this.board = new Board();
        // this.inProgress = false;
    }

    public void addPlayerToGame(ClientHandler player) {
        if (gameQueue.checkIfPlayerInQueue(player)) {
            throw new IllegalArgumentException("Player is already in queue");
        }

        gameQueue.addPlayer(player);

        if (gameQueue.getSize() == maxPlayers) {
            this.gameStatus = GameStatus.IN_PROGRESS;
        }
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public int getPlayersCount() {
        return gameQueue.getSize();
    }

    public boolean checkIfPlayerIsInGame(ClientHandler player) {
        return gameQueue.checkIfPlayerInQueue(player);
    }

    // public List<ClientHandler> getPlayers() {
    // return players;
    // }

    public void move(ClientHandler player, Position new_position) {
        if (gameStatus == GameStatus.IN_PROGRESS) {
            // TODO Wykonanie ruchu w tablicy (board.move(start, end))
            // broadcastMessage("A player has moved from " + start + " to " + end + ".");
            // activePlayerIndex = (activePlayerIndex + 1) % maxPlayers; // Pora na ruch
            // kolejnego gracza
            // players.get(activePlayerIndex).send("Your turn to move");
        } else {

        }
    }

    // public void move(ClientHandler player, int start, int end) {
    // if (player == players.get(activePlayerIndex)) {
    // // TODO Wykonanie ruchu w tablicy (board.move(start, end))
    // broadcastMessage("A player has moved from " + start + " to " + end + ".");
    // activePlayerIndex = (activePlayerIndex + 1) % maxPlayers; // Pora na ruch
    // kolejnego gracza
    // players.get(activePlayerIndex).send("Your turn to move");
    // } else {
    // player.send("Please wait for your turn.");
    // }
    // }

    // public void broadcastMessage(String msg) {
    // for (ClientHandler player : players) {
    // player.send(msg);
    // }
    // }

    // public boolean addPlayer(ClientHandler player) {
    // if (players.size() < maxPlayers) {
    // players.add(player);

    // // Jeśli dodaliśmy ostatniego gracza, zaczynamy gre
    // if (players.size() == maxPlayers) {
    // startGame();
    // }

    // return true;
    // } else {
    // System.out.println("Game ID " + id + " is full.");
    // return false;
    // }
    // }

    // public void startGame() {
    // broadcastMessage("All players have joined, starting the game...");
    // activePlayerIndex = rand.nextInt(maxPlayers); // Losujemy indeks
    // zaczynającego gracza
    // inProgress = true;
    // players.get(activePlayerIndex).send("Your turn to move");
    // }

    public UUID getId() {
        return gameId;
    }
}
