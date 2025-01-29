package com.michal.Game;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.michal.Game.Board.*;
import com.michal.Game.MoveValidation.MoveValidatorStandard;
import com.michal.Server;
import com.michal.SocketCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.UUID;

import com.michal.Database.DatabaseConnector;
import com.michal.Models.GameMoves;
import com.michal.Models.GameModel;
import com.michal.Game.Bots.BotPlayer;
import com.michal.Game.Bots.BotAlgorithmSmart;
import com.michal.Game.Variant;
import com.michal.GameSessionMediator;

public class GameSessionTest {
    @Mock
    private DatabaseConnector databaseConnector;

    @Mock
    private Server server;

    @Mock
    private Board board;

    @Mock
    private Layout layout;

    @Mock
    private Variant variant;

    @InjectMocks
    private GameSession gameSession;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        board = new StarBoard(5, new MoveValidatorStandard());
        layout = Layout.THREEPLAYERS_ONESET;
        variant = Variant.STANDARD;
        server = new Server();
        databaseConnector = new DatabaseConnector();
        gameSession = new GameSession(board, layout, variant, server, databaseConnector, null, null);
    }

    @Test
    void addPlayerSuccessfully() {
        Player player = new Player(UUID.randomUUID(), null);
        gameSession.addPlayer(player);
        assertEquals(1, gameSession.getPlayers().size());
        assertEquals("Player1", gameSession.getPlayers().getFirst().getName());
    }

    @Test
    void addPlayerWhenSessionIsFull() {
        for (int i = 0; i < gameSession.getGame().getMaxPlayers(); i++) {
            gameSession.addPlayer(new Player(UUID.randomUUID(), null));
        }
        assertThrows(IllegalArgumentException.class, () -> gameSession.addPlayer(new Player(UUID.randomUUID(), null)));
    }

    @Test
    void removePlayerSuccessfully() {
        Player player = new Player(UUID.randomUUID(), null);
        gameSession.addPlayer(player);
        gameSession.removePlayer(player);
        assertTrue(gameSession.getPlayers().isEmpty());
    }

    @Test
    void startGameSuccessfully() {
        for (int i = 0; i < gameSession.getGame().getMaxPlayers(); i++) {
            gameSession.addPlayer(new Player(null, null));
        }
        assertEquals(GameStatus.IN_PROGRESS, gameSession.getGame().getStatus());
    }

    @Test
    void addBotSuccessfully() {
        gameSession.addBot();
        assertEquals(1, gameSession.getPlayers().size());
        assertInstanceOf(BotPlayer.class, gameSession.getPlayers().getFirst());
    }

    @Test
    void saveGameSuccessfully() {
        Player player = new Player(UUID.randomUUID(), null);
        gameSession.addPlayer(player);
        gameSession.startGame();
        gameSession.saveGame();
        verify(databaseConnector, times(1)).saveGame(any(GameModel.class));
    }
}