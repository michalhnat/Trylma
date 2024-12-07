package com.michal.Commands;

import java.io.ObjectOutputStream;

import com.michal.ICommunication;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

public abstract class AbstractCommand implements Runnable {
    protected ICommunication communication;

    public AbstractCommand(ICommunication communication) {
        this.communication = communication;
    }

    public abstract void run();
}
