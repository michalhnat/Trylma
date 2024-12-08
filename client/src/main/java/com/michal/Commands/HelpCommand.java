package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

import picocli.CommandLine.Command;

@Command(name = "help", description = "Displays help message")
public class HelpCommand extends AbstractCommand {
    public HelpCommand(ICommunication communication, Display display) {
        super(communication, display);
    }

    @Override
    public void run() {
        display.displayMessage("Available commands:");
        display.displayMessage("help - displays this message");
        display.displayMessage("connect <address> <port> - connects to the server");
        display.displayMessage("disconnect - disconnects from the server");
        display.displayMessage("exit - exits the application");
    }

}
