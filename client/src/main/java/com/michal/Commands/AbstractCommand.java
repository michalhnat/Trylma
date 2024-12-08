package com.michal.Commands;

import com.michal.Display;
import com.michal.ICommunication;

public abstract class AbstractCommand implements Runnable {
    protected ICommunication communication;
    protected Display display;

    public AbstractCommand(ICommunication communication, Display display) {
        this.communication = communication;
        this.display = display;
    }

    public abstract void run();
}
