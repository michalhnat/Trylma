package com.michal;

import java.io.ObjectInputStream;
import java.net.InetAddress;

public interface ICommunication {
    public void connectToServer(InetAddress address, int port);

    public void sendMessage(String message);

    public ObjectInputStream getInputStream();

    public boolean isConnected();
}
