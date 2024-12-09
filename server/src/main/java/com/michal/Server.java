package com.michal;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.michal.Game.Game;
import com.michal.Game.Position;

public class Server implements Mediator {
    private ServerSocket serverSocket;
    private static final int PORT = 8085;

    private static List<Game> games = new ArrayList<>();
    private List<ClientHandler> clients = new ArrayList<>();

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server started on port " + PORT);

            while (true) {
                var clientSocket = serverSocket.accept();
                var clientHandler = new ClientHandler(clientSocket, this);
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
    public void handleListGames(ClientHandler clientHandler) {
        for (Game game : games) {
            clientHandler.send("Game " + game.getId() + ": " + game.getPlayersCount() + "/" + game.getMaxPlayers()
                    + " players.");
        }
    }

    @Override
    public void handleJoinGame(ClientHandler clientHandler, UUID gameId) {
        Optional<Game> gameOptional = games.stream().filter(game -> game.getId() == gameId).findFirst();

        if (gameOptional.isPresent()) {
            Game game = gameOptional.get();
            try {
                game.addPlayerToGame(clientHandler);
                clientHandler.send("Joined game " + gameId);
            } catch (IllegalArgumentException e) {
                clientHandler.send("Could not join game ID " + gameId);

            }
        }
    }

    @Override
    public void handleCreateGame(ClientHandler clientHandler, int players) {
        Game game = new Game(players);
        games.add(game);
        game.addPlayerToGame(clientHandler);
        clientHandler.send("Created a new game and joined");
    }

    @Override
    public void handleMove(ClientHandler clientHandler, int x, int y) {
        for (Game game : games) {
            if (game.checkIfPlayerIsInGame(clientHandler)) {

                Position new_position = new Position(x, y);
                game.move(clientHandler, new_position);
            }
        }
    }

    // @Override
    // public void handleMessage(String msg, ClientHandler sender) {
    // Gson gson = new Gson();
    // System.out.println("Mediator processing: " + msg);

    // try {
    // // Deserializacja jsona do obiektu ClientRequest
    // JsonObject request = gson.fromJson(msg, JsonObject.class);
    // String command = request.get("command").getAsString();
    // List<Game> games;

    // // Obsługa poleceń
    // switch (command.toLowerCase()) {
    // case "list":
    // games = getAvalibleGames();
    // for (Game game : games) {
    // sender.send("Game " + game.getId() + ": " + game.getPlayers().size() + "/"
    // + game.getMaxPlayers() + " players.");
    // }
    // break;

    // case "join":
    // joinGame(sender, request.get("gameID").getAsInt());
    // sender.send("Joined game " + request.get("gameID").getAsInt());
    // break;

    // case "create":
    // startGameSession(request.get("players").getAsInt());
    // sender.send("Created a new game");
    // break;

    // case "move":
    // games = getAvalibleGames();
    // int x = request.get("x").getAsInt();
    // int y = request.get("y").getAsInt();
    // // Można to zrobić lepiej
    // for (Game game : games) {
    // if (game.getPlayers().contains(sender)) {
    // game.move(sender, x, y);
    // }
    // }
    // break;

    // default:
    // sender.send("Polecenie nie obsługiwane");
    // break;
    // }

    // } catch (JsonSyntaxException e) {
    // sender.send("Invalid JSON format");
    // } catch (Exception e) {
    // sender.send("An error occured: " + e.getMessage());
    // }
    // }

    // private void startGameSession(int players) {
    // Game game = new Game(players);
    // games.add(game);
    // System.out.println("Created new game session for " + players + " players.");
    // }

    // public List<Game> getAvalibleGames() {
    // return games;
    // }

    // public boolean joinGame(ClientHandler clientHandler, int gameId) {
    // Optional<Game> gameOptional = games.stream()
    // .filter(game -> game.getId() == gameId)
    // .findFirst();

    // if (gameOptional.isPresent()) {
    // Game game = gameOptional.get();
    // if (game.addPlayer(clientHandler)) {
    // System.out.println("Player added to game ID " + gameId);
    // return true;
    // }
    // }
    // System.out.println("Could not join game ID " + gameId);
    // return false;
    // }
}
