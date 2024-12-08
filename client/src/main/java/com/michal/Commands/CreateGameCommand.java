package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;
import com.michal.Exceptions.FailedSendingMessageToServer;
import com.michal.Utils.JsonBuilder;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "create", description = "Create a game")
public class CreateGameCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Override
    public void run() {
        Display display = parent.getDisplay();
        ICommunication communication = parent.getCommunication();
        if (!communication.isConnected()) {
            display.displayError("Not connected to a server");
            return;
        }
        try {
            String jsonMessage = JsonBuilder.setBuilder("create").build();
            communication.sendMessage(jsonMessage);
        } catch (FailedSendingMessageToServer e) {
            display.displayError(e.getMessage());
        } catch (Exception e) {
            display.displayError("Failed to join game");
        }
    }

}
