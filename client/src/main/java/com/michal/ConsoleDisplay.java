package com.michal;

public class ConsoleDisplay implements Display {
    @Override
    public void displayMessage(String message, boolean newLine) {
        if (newLine) {
            System.out.println(message);
        } else {
            System.out.print(message);
        }
    }

    @Override
    public void displayMessage(String message) {
        displayMessage(message, true);
    }

    @Override
    public void displayError(String error) {
        System.err.println("\u001B[31m" + error + "\u001B[0m");
    }

    @Override
    public void displayBoard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayBoard'");
    }

}
