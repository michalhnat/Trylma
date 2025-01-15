package com.michal;

/**
 * Interface defining methods for handling UI messages and updates. Implements basic functionality
 * for displaying information and error messages, as well as general message handling in the
 * application.
 */
public interface IController {

    /**
     * Displays an informational message to the user. This should be used for non-critical
     * notifications and updates.
     *
     * @param message The informational message to be displayed
     */
    public void showInfo(String message);

    /**
     * Displays an error message to the user. This should be used for critical errors and warnings
     * that require user attention.
     *
     * @param message The error message to be displayed
     */
    public void showError(String message);

    /**
     * Handles incoming messages from the system. This method should process and route messages to
     * appropriate handlers based on their content.
     *
     * @param message The message to be processed
     */
    public void handleMessage(String message);
}
