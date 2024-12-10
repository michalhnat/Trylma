package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.Utils.JsonBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

/**
 * Command to list all games.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "list", description = "List all games")
public class ListGamesCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    /**
     * Executes the command to list all games.
     * Sends a JSON message to the server to request the list of games.
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
            String jsonMessage = JsonBuilder.setBuilder("list").build();
            communication.sendMessage(jsonMessage);
        } catch (Exception e) {
            display.displayError("Failed to list games");
        }
    }
}