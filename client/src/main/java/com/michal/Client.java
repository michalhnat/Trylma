package com.michal;

public class Client {
    private ICommunication communication;
    private ServerMessageHandler messageHandler;
    private Display display;
    private CLIInputHandler inputHandler;

    public Client(ICommunication communication, Display display,
            CLIInputHandler inputHandler) {
        this.communication = communication;
        this.display = display;
        this.inputHandler = inputHandler;
    }

    public void run() {
        Thread inputHandlerThread = new Thread(inputHandler);
        display.displayMessage("Please connect to server using 'connect' command");

        Thread connectionCheckThread = new Thread(() -> {
            while (!communication.isConnected()) {
                try {
                    Thread.sleep(1000); // Check every second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }

            // Once connected, start message handler
            Thread messageHandlerThread = new Thread(new MessageHandler(display, communication.getInputStream()));
            messageHandlerThread.start();
        });

        connectionCheckThread.start();
        inputHandlerThread.start();

        // try {
        // messageHandlerThread.join();
        // inputHandlerThread.join();
        // } catch (InterruptedException e) {
        // // display.showMessage("Thread interrupted: " + e.getMessage());
        // Thread.currentThread().interrupt();
        // }
    }
}