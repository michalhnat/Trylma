package com.michal;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public interface Communication_1 {
    void sendMessage(String msg, ObjectOutputStream out);
    void receive(String msg);
}
