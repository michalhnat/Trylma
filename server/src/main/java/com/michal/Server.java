package com.michal;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

public class Server implements Mediator {
    private static Server instance;
    private static List<ClientHandler> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    private static final int PORT = 8085;
    private static List<Game> games = new ArrayList<>();

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                var clientSocket = serverSocket.accept();
                var clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    @Override
    public void handleMessage(String msg, ClientHandler sender) {
        Gson gson = new Gson();
        System.out.println("Mediator processing: " + msg);

        try {
            // Deserializacja jsona do obiektu ClientRequest
            JsonObject request = gson.fromJson(msg, JsonObject.class);
            String command = request.get("command").getAsString();
            List<Game> games;

            // Obsługa poleceń
            switch (command.toLowerCase()) {
                case "list":
                    games = getAvalibleGames();
                    for (Game game : games) {
                        sender.send("Game " + game.getId() + ": " + game.getPlayers().size() + "/" + game.getMaxPlayers() + " players.");
                    }
                    break;

                case "join":
                    joinGame(sender, request.get("gameID").getAsInt());
                    sender.send("Joined game " + request.get("gameID").getAsInt());
                    break;

                case "create":
                    startGameSession(request.get("players").getAsInt());
                    sender.send("Created a new game");
                    break;

                case "move":
                    games = getAvalibleGames();
                    int x = request.get("x").getAsInt();
                    int y = request.get("y").getAsInt();
                    // Można to zrobić lepiej
                    for (Game game : games) {
                        if (game.getPlayers().contains(sender)) {
                            game.move(sender, x, y);
                        }
                    }
                    break;

                default:
                    sender.send("Polecenie nie obsługiwane");
                    break;
            }

        } catch (JsonSyntaxException e) {
            sender.send("Invalid JSON format");
        } catch (Exception e) {
            sender.send("An error occured: " + e.getMessage());
        }
    }

    private void startGameSession(int players) {
        Game game = new Game(players);
        games.add(game);
        System.out.println("Created new game session for " + players + " players.");
    }

    public List<Game> getAvalibleGames() {
        return games;
    }

    public boolean joinGame(ClientHandler clientHandler, int gameId) {
        Optional<Game> gameOptional = games.stream()
                .filter(game -> game.getId() == gameId)
                .findFirst();

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            if (game.addPlayer(clientHandler)) {
                System.out.println("Player added to game ID " + gameId);
                return true;
            }
        }
        System.out.println("Could not join game ID " + gameId);
        return false;
    }

    public static void main(String[] args) {
        Server.getInstance().startServer();
    }

    private static class Game {
        private static int gameCounter = 0;
        private final int id;
        private final int maxPlayers;
        private final List<ClientHandler> players;
        private int activePlayerIndex;
        private final Board board;
        private final Random rand = new Random();
        private boolean inProgress;
        // TODO Ustalenie kolejności graczy

        public Game(int maxPlayers) {
            this.id = ++gameCounter;
            this.maxPlayers = maxPlayers;
            this.players = new ArrayList<>();
            this.board = new Board();
            this.inProgress = false;
        }

        public int getId() {
            return id;
        }

        public int getMaxPlayers() {
            return maxPlayers;
        }

        public List<ClientHandler> getPlayers() {
            return players;
        }

        public void move(ClientHandler player, int start, int end) {
            if (player == players.get(activePlayerIndex)) {
                // TODO Wykonanie ruchu w tablicy (board.move(start, end))
                broadcastMessage("A player has moved from " + start + " to " + end + ".");
                activePlayerIndex = (activePlayerIndex + 1) % maxPlayers; // Pora na ruch kolejnego gracza
                players.get(activePlayerIndex).send("Your turn to move");
            }
            else {
                player.send("Please wait for your turn.");
            }
        }

        public void broadcastMessage(String msg) {
            for (ClientHandler player : players) {
                player.send(msg);
            }
        }

        public boolean addPlayer(ClientHandler player) {
            if (players.size() < maxPlayers) {
                players.add(player);

                // Jeśli dodaliśmy ostatniego gracza, zaczynamy gre
                if (players.size() == maxPlayers) {
                    startGame();
                }


                return true;
            } else {
                System.out.println("Game ID " + id + " is full.");
                return false;
            }
        }

        public void startGame() {
            broadcastMessage("All players have joined, starting the game...");
            activePlayerIndex = rand.nextInt(maxPlayers); // Losujemy indeks zaczynającego gracza
            inProgress = true;
            players.get(activePlayerIndex).send("Your turn to move");
        }
    }

    private static class Board {

    }
}
