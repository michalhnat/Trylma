package com.michal;

public interface Display {
    void displayBoard();

    void displayMessage(String message, boolean newLine);

    void displayMessage(String message);

    void displayError(String message);

}