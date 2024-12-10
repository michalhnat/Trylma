package com.michal;

import java.util.Scanner;

import com.michal.Commands.MainCommand;

import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * CLI input handler that processes user commands.
 */
@Command(name = "cli", description = "CLI input handler")
public class CLIInputHandler implements Runnable {
    private CommandLine commandLine;
    private Scanner scanner;
    private ICommunication communication;
    private Display display;

    /**
     * Constructs a new CLIInputHandler instance with the specified communication and display interfaces.
     *
     * @param communication the communication interface to use
     * @param display the display interface to use
     */
    public CLIInputHandler(ICommunication communication, Display display) {
        MainCommand mainCommand = new MainCommand(communication, display);
        this.commandLine = new CommandLine(mainCommand);
        this.scanner = new Scanner(System.in);
        this.communication = communication;
        this.display = display;
    }

    /**
     * Runs the CLI input handler, continuously reading and executing user commands.
     */
    @Override
    public void run() {
        while (true) {
            display.displayMessage(">", false);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            try {
                commandLine.execute(input.split(" "));
            } catch (Exception e) {
                display.displayError("Invalid command");
            }
        }
    }
}