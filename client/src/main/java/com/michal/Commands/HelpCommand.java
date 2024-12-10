package com.michal.Commands;

import com.michal.Display;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "help", description = "Displays help message")
public class HelpCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Override
    public void run() {
        Display display = parent.getDisplay();
        display.displayMessage("Available commands:");
        display.displayMessage("help - displays this message");
        display.displayMessage("connect <address> <port> - connects to the server");
        display.displayMessage("disconnect - disconnects from the server");
        display.displayMessage("exit - exits the application");
    }

}
