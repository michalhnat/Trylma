package com.michal;

import java.io.ObjectOutputStream;
import java.util.List;
import com.michal.Game.GameInfo;

public interface ICommunication {
    void sendMessage(String msg, ObjectOutputStream out);

    void sendListMessage(List<GameInfo> message, ObjectOutputStream out);

    void sendError(String msg, ObjectOutputStream out);

    void receive(String msg);
}
