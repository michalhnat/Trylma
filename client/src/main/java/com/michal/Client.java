package com.michal;

public class Client {
    private ICommunication communication;
    private ServerMessageHandler messageHandler;
    private Display display;
    private CLIInputHandler inputHandler;

    public Client(ICommunication communication, Display display,
            CLIInputHandler inputHandler, ServerMessageHandler messageHandler) {
        this.communication = communication;
        this.display = display;
        this.inputHandler = inputHandler;
        this.messageHandler = messageHandler;
    }

    public void run() {
        try {
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

                display.displayMessage("Connected to server");

                messageHandler.setDisplay(display);
                messageHandler.setObjectInputStream(communication.getInputStream());

                Thread messageHandlerThread = new Thread(messageHandler);
                messageHandlerThread.start();
            });

            connectionCheckThread.start();
            inputHandlerThread.start();

        } catch (Exception e) {
            display.displayError("An unexpected error occurred");
        }
    }
}