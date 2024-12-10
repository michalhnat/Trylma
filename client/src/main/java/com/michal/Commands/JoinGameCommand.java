package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.Exceptions.FailedSendingMessageToServer;
import com.michal.Utils.JsonBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.ParentCommand;

/**
 * Command to join a game.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "join", description = "Join a game")
public class JoinGameCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Parameters(index = "0", description = "Game ID")
    private int gameID;

    /**
     * Executes the command to join a game.
     * Sends a JSON message to the server with the game ID.
     */
    @Override
    public void run() {
        Display display = parent.getDisplay();
        ICommunication communication = parent.getCommunication();
        if (!communication.isConnected()) {
            display.displayError("Not connected to a server");
            return;
        }
        try {
            String jsonMessage = JsonBuilder.setBuilder("join")
                    .setPayloadArgument("gameID", gameID)
                    .build();

            communication.sendMessage(jsonMessage);
        } catch (FailedSendingMessageToServer e) {
            display.displayError(e.getMessage());
        } catch (Exception e) {
            display.displayError("Failed to join game");
        }
    }
}