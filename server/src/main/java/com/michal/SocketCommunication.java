package com.michal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import com.google.gson.JsonObject;
import com.michal.Game.GameInfo;
import com.michal.Utils.JsonBuilder;
import com.michal.Utils.MyLogger;

public class SocketCommunication implements ICommunication {
    private final Socket socket;
    Logger logger = MyLogger.logger;

    public SocketCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public synchronized void sendMessage(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(
                    JsonBuilder.setBuilder("message").setPayloadArgument("content", msg).build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public synchronized void sendListMessage(List<GameInfo> list, ObjectOutputStream out) {
        try {
            JsonBuilder builder = JsonBuilder.setBuilder("list");
            List<JsonObject> gameObjects = list.stream().map(game -> {
                JsonObject gameObj = new JsonObject();
                gameObj.addProperty("gameId", game.getId());
                gameObj.addProperty("currentPlayers", game.getCurrentPlayers());
                gameObj.addProperty("maxPlayers", game.getMaxPlayers());
                return gameObj;
            }).collect(Collectors.toList());

            builder.setPayloadArray(gameObjects);
            out.writeObject(builder.build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public synchronized void sendError(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(
                    JsonBuilder.setBuilder("error").setPayloadArgument("content", msg).build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public synchronized void receive(String msg) {
        System.out.println("Received from client: " + msg);
    }
}
