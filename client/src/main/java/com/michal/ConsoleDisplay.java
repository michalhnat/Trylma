package com.michal;

public class ConsoleDisplay implements Display {
    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void displayError(String error) {
        System.err.println(error);
    }

    @Override
    public void displayBoard() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayBoard'");
    }

    @Override
    public void displayInfo(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'displayInfo'");
    }

}
