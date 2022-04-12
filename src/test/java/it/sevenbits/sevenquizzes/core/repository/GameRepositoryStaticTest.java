package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameRepositoryStaticTest {
    private GameRepository gameRepository;

    private Map<String, Game> mockGames;

    @Before
    public void setUp() {
        mockGames = mock(Map.class);
        gameRepository = new GameRepositoryStatic(mockGames);
    }

    @Test
    public void addGameTest() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        final int gameQuestionsCount = 10;

        final Game gameMock = mock(Game.class);

        when(gameRepository.addGame(roomId, gameQuestionsCount)).thenReturn(gameMock);
        when(mockGames.put(eq(roomId), any(Game.class))).thenReturn(null);

        final Game resultGame = gameRepository.addGame(roomId, gameQuestionsCount);

        verify(mockGames, times(1)).put(eq(roomId), any(Game.class));

        Assert.assertEquals(resultGame.getGameStatus().getQuestionsCount(), gameQuestionsCount);
    }

    @Test
    public void getGameScoreTest() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        final Game gameMock = mock(Game.class);
        final GameScore gameScoreMock = mock(GameScore.class);

        when(mockGames.get(roomId)).thenReturn(gameMock);
        when(gameMock.getGameScore(playerId)).thenReturn(gameScoreMock);

        final GameScore gameScore = gameRepository.getGame(roomId).getGameScore(playerId);

        verify(mockGames, times(1)).get(roomId);
        verify(gameMock, times(1)).getGameScore(playerId);

        Assert.assertEquals(gameScore, gameScoreMock);
    }

    @Test
    public void getGameStatusTest() {
        final String roomId = UUID.randomUUID().toString();

        final Game gameMock = mock(Game.class);
        final GameStatus gameStatusMock = mock(GameStatus.class);

        when(mockGames.get(roomId)).thenReturn(gameMock);
        when(gameMock.getGameStatus()).thenReturn(gameStatusMock);

        final GameStatus gameStatus = gameRepository.getGame(roomId).getGameStatus();

        verify(mockGames, times(1)).get(roomId);

        Assert.assertEquals(gameStatus, gameStatusMock);
    }

    @Test
    public void getGamesTest() {
        List<Game> games = new ArrayList<>();

        games.add(new Game(5));
        games.add(new Game(10));

        when(gameRepository.getGames()).thenReturn(games);

        List<Game> resultGames = gameRepository.getGames();

        verify(mockGames, times(1)).values();

        Assert.assertEquals(games, resultGames);
    }
}
