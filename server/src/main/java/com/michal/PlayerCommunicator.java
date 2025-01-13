package com.michal;

import com.michal.Game.GameInfo;

public interface PlayerCommunicator {
    void sendMessage(String message);

    void sendError(String message);

    void sendBoard(String board);

    void sendGameInfo(GameInfo gameInfo);
}
