package com.michal;

/**
 * A class that implements the Display interface to handle console output.
 */
public class ConsoleDisplay implements Display {

    /**
     * Displays a message to the console.
     *
     * @param message the message to display
     * @param newLine whether to print a new line after the message
     */
    @Override
    public void displayMessage(String message, boolean newLine) {
        if (newLine) {
            System.out.println(message);
        } else {
            System.out.print(message);
        }
    }

    /**
     * Displays a message to the console with a new line.
     *
     * @param message the message to display
     */
    @Override
    public void displayMessage(String message) {
        displayMessage(message, true);
    }

    /**
     * Displays an error message to the console in red.
     *
     * @param error the error message to display
     */
    @Override
    public void displayError(String error) {
        System.err.println("\u001B[31m" + error + "\u001B[0m");
    }

    /**
     * Displays the game board to the console.
     *
     * @throws UnsupportedOperationException if the method is not implemented
     */
    @Override
    public void displayBoard() {
        throw new UnsupportedOperationException("Unimplemented method 'displayBoard'");
    }
}