package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

/**
 * Command to exit the application.
 * Implements the Runnable interface to be executed as a command.
 */
@Command(name = "exit", description = "Exit the application")
public class ExitCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    /**
     * Executes the command to exit the application.
     * Displays a message and terminates the application.
     */
    @Override
    public void run() {
        Display display = parent.getDisplay();
        display.displayMessage("Exiting the application");
        System.exit(0);
    }
}