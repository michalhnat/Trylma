package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

import picocli.CommandLine.Command;

@Command(name = "exit", description = "Exit the application")
public class ExitCommand extends AbstractCommand {

    public ExitCommand(ICommunication communication, Display display) {
        super(communication, display);
    }

    @Override
    public void run() {
        display.displayMessage("Exiting the application");
        System.exit(0);
    }
}