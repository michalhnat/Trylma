package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.JsonBuilder;

import picocli.CommandLine.Command;

@Command(name = "list", description = "List all games")

public class ListGamesCommand extends AbstractCommand {

    public ListGamesCommand(ICommunication communication, Display display) {
        super(communication, display);
    }

    @Override
    public void run() {
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
