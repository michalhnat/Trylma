package com.michal;

import com.michal.Utils.JsonBuilder;
import com.michal.Utils.MyLogger;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class SocketCommunication implements ICommunication {
    private final Socket socket;
    Logger logger = MyLogger.logger;
    static {MyLogger.loggerConfig();}

    public SocketCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void sendMessage(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(JsonBuilder
                    .setBuilder("message")
                    .setPayloadArgument("content", msg)
                    .build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public void sendError(String msg, ObjectOutputStream out) {
        try {
            out.writeObject(JsonBuilder
                    .setBuilder("error")
                    .setPayloadArgument("content", msg)
                    .build());
        } catch (IOException e) {
            logger.warning("Error sending message: " + e.getMessage());
        }
    }

    @Override
    public void receive(String msg) {
        System.out.println("Received from client: " + msg);
    }
}
