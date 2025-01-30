package com.michal;

import com.michal.Game.GameSession;

/**
 * An interface for mediating game sessions.
 */
public interface GameSessionMediator {

    /**
     * Removes a game session from the list of active sessions.
     *
     * @param session the game session to remove
     */
    void removeSession(GameSession session);
}
