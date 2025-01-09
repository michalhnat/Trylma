package com.michal;

/**
 * Interface for displaying messages and game board.
 */
public interface Display {

    /**
     * Displays the game board.
     */
    void displayBoard();

    /**
     * Displays a message to the console.
     *
     * @param message the message to display
     * @param newLine whether to print a new line after the message
     */
    void displayMessage(String message, boolean newLine);

    /**
     * Displays a message to the console with a new line.
     *
     * @param message the message to display
     */
    void displayMessage(String message);

    /**
     * Displays an error message to the console.
     *
     * @param message the error message to display
     */
    void displayError(String message);
}