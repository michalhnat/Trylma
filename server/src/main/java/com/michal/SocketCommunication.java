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

/**
 * A class for handling socket communication.
 */
public class SocketCommunication implements ICommunication {
    private final Socket socket;
    Logger logger = MyLogger.logger;

    /**
     * Constructs a new SocketCommunication instance with the specified socket.
     *
     * @param socket the socket to use for communication
     */
    public SocketCommunication(Socket socket) {
        this.socket = socket;
    }

    /**
     * Sends a message to the specified output stream.
     *
     * @param msg the message to send
     * @param out the output stream to send the message to
     */
    @Override
    public synchronized void sendMessage(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(
                    JsonBuilder.setBuilder("message").setPayloadArgument("content", msg).build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    /**
     * Sends a list of GameInfo objects as a message to the specified output stream.
     *
     * @param list the list of GameInfo objects to send
     * @param out the output stream to send the message to
     */
    @Override
    public synchronized void sendListMessage(List<GameInfo> list, ObjectOutputStream out) {
        try {
            JsonBuilder builder = JsonBuilder.setBuilder("list");
            List<JsonObject> gameObjects = list.stream().map(game -> {
                JsonObject gameObj = new JsonObject();
                gameObj.addProperty("gameId", game.getId());
                gameObj.addProperty("currentPlayers", game.getCurrentPlayers());
                gameObj.addProperty("maxPlayers", game.getMaxPlayers());
                gameObj.addProperty("layout", game.getLayout());
                gameObj.addProperty("variant", game.getVariant());
                gameObj.addProperty("status", game.getStatus().toString());
                gameObj.addProperty("players_color", game.getPlayers_color());
                return gameObj;
            }).collect(Collectors.toList());

            builder.setPayloadArray(gameObjects);
            out.writeObject(builder.build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public synchronized void sendGameInfo(GameInfo gameInfo, ObjectOutputStream out) {
        try {
            out.writeObject(JsonBuilder.setBuilder("gameInfo")
                    .setPayloadArgument("gameId", gameInfo.getId())
                    .setPayloadArgument("players", gameInfo.getCurrentPlayers())
                    .setPayloadArgument("layout", gameInfo.getLayout())
                    .setPayloadArgument("variant", gameInfo.getVariant())
                    .setPayloadArgument("status", gameInfo.getStatus().toString())
                    .setPayloadArgument("color", gameInfo.getPlayers_color()).build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public synchronized void sendBoard(String board, ObjectOutputStream out) {
        try {
            out.writeObject(
                    JsonBuilder.setBuilder("board").setPayloadArgument("content", board).build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    /**
     * Sends an error message to the specified output stream.
     *
     * @param msg the error message to send
     * @param out the output stream to send the error message to
     */
    @Override
    public synchronized void sendError(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(
                    JsonBuilder.setBuilder("error").setPayloadArgument("content", msg).build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    /**
     * Receives a message.
     *
     * @param msg the message received
     */
    @Override
    public synchronized void receive(String msg) {
        System.out.println("Received from client: " + msg);
    }
}
