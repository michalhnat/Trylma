package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

/**
 * Abstract class representing a command that can be executed.
 * Implements the Runnable interface.
 */
public abstract class AbstractCommand implements Runnable {
    protected ICommunication communication;
    protected Display display;

    /**
     * Constructs a new AbstractCommand instance with the specified communication and display.
     *
     * @param communication the communication interface to use
     * @param display the display interface to use
     */
    public AbstractCommand(ICommunication communication, Display display) {
        this.communication = communication;
        this.display = display;
    }

    /**
     * Executes the command.
     * This method must be implemented by subclasses.
     */
    public abstract void run();
}