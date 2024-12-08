package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

@Command(name = "exit", description = "Exit the application")
public class ExitCommand implements Runnable {

    @ParentCommand
    private MainCommand parent;

    @Override
    public void run() {
        Display display = parent.getDisplay();
        display.displayMessage("Exiting the application");
        System.exit(0);
    }
}