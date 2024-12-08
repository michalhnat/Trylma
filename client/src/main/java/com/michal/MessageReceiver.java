package com.michal;

import java.io.IOException;
import java.io.ObjectInputStream;

public class MessageReceiver extends ServerMessageHandler {
    public MessageReceiver(Display display, ObjectInputStream in) {
        super(display, in);
    }

    public MessageReceiver() {
    }

    @Override
    public void hanldeMessage(String message) {
        System.out.println(message);

    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) in.readObject();
                hanldeMessage(message);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
