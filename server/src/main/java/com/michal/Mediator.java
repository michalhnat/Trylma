package com.michal;

import java.util.UUID;

public interface Mediator {

    public void handleListGames(ClientHandler clientHandler);

    public void handleJoinGame(ClientHandler clientHandler, UUID gameId);

    public void handleCreateGame(ClientHandler clientHandler, int players);

    public void handleMove(ClientHandler clientHandler, int x, int y);

    public void removeClient(ClientHandler clientHandler);
}
