package com.michal;

/**
 * The Client class that handles the main client operations.
 */
public class Client {
    private ICommunication communication;
    private ServerMessageHandler messageHandler;
    private Display display;
    private CLIInputHandler inputHandler;

    /**
     * Constructs a new Client instance with the specified communication, display, input handler, and message handler.
     *
     * @param communication the communication interface to use
     * @param display the display interface to use
     * @param inputHandler the input handler to use
     * @param messageHandler the message handler to use
     */
    public Client(ICommunication communication, Display display, CLIInputHandler inputHandler,
                  ServerMessageHandler messageHandler) {
        this.communication = communication;
        this.display = display;
        this.inputHandler = inputHandler;
        this.messageHandler = messageHandler;
    }

    /**
     * Runs the client, starting the input handler and connection check threads.
     */
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