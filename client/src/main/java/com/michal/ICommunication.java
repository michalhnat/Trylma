package com.michal;

import java.io.ObjectInputStream;
import java.net.InetAddress;

import com.michal.Exceptions.FailedConnectingToServerException;
import com.michal.Exceptions.FailedSendingMessageToServer;

public interface ICommunication {
    public void connectToServer(InetAddress address, int port) throws FailedConnectingToServerException;

    public void sendMessage(String message) throws FailedSendingMessageToServer;

    public ObjectInputStream getInputStream();

    public boolean isConnected();

}
