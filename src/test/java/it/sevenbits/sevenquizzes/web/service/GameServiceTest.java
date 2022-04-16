package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import it.sevenbits.sevenquizzes.core.model.question.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.question.QuestionRepository;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class GameServiceTest {
    private GameService gameService;

    private QuestionRepository mockQuestionRepository;
    private GameRepository mockGameRepository;
    private RoomRepository mockRoomRepository;

    @Before
    public void setUp() {
        mockQuestionRepository = mock(QuestionRepository.class);
        mockGameRepository = mock(GameRepository.class);
        mockRoomRepository = mock(RoomRepository.class);

        gameService = new GameService(mockQuestionRepository, mockGameRepository, mockRoomRepository);
    }

    @Test
    public void startGameTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        final int questionsCount = 2;

        final Game mockGame = mock(Game.class);
        final GameStatus gameStatus = new GameStatus("not started", null, 0, questionsCount);

        final List<String> questionIds = new ArrayList<>();
        questionIds.add(UUID.randomUUID().toString());

        when(mockGameRepository.create(roomId, questionsCount)).thenReturn(mockGame);
        when(mockGame.getGameStatus()).thenReturn(gameStatus);
        when(mockRoomRepository.contains(roomId)).thenReturn(true);
        when(mockQuestionRepository.getRoomQuestionsId(roomId)).thenReturn(questionIds);

        final QuestionLocation resultQuestionLocation = gameService.startGame(roomId, playerId);

        verify(mockGameRepository, times(1)).create(roomId, questionsCount);
        verify(mockGame, times(1)).getGameStatus();
        verify(mockRoomRepository, times(1)).contains(roomId);
        verify(mockQuestionRepository, times(1)).getRoomQuestionsId(roomId);

        Assert.assertEquals("started", gameStatus.getStatus());
        Assert.assertEquals(questionsCount, gameStatus.getQuestionsCount());
        Assert.assertEquals(questionIds.get(0), resultQuestionLocation.getQuestionId());
    }

    @Test
    public void getQuestionDataTest() {
        final String roomId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();

        final QuestionWithOptions mockQuestion = mock(QuestionWithOptions.class);

        when(mockQuestionRepository.getById(questionId)).thenReturn(mockQuestion);

        final QuestionWithOptions resultQuestion = gameService.getQuestionData(roomId, questionId);

        verify(mockQuestionRepository, times(1)).getById(questionId);

        Assert.assertEquals(mockQuestion, resultQuestion);
    }

    @Test
    public void answerNotCurrentQuestionTest() throws Exception {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();
        final String answerId = UUID.randomUUID().toString();

        final GameStatus mockGameStatus = mock(GameStatus.class);
        final GameScore mockGameScore = mock(GameScore.class);

        final List<String> mockAnsweredPlayers = mock(List.class);

        final int totalScore = 10;
        final int questionScore = 5;

        final Game mockGame = mock(Game.class);

        when(mockRoomRepository.contains(roomId)).thenReturn(true);
        when(mockGameRepository.getById(roomId)).thenReturn(mockGame);
        when(mockGame.getAnsweredPlayers()).thenReturn(mockAnsweredPlayers);
        when(mockGame.getGameStatus()).thenReturn(mockGameStatus);
        when(mockGame.getGameScore(playerId)).thenReturn(mockGameScore);
        when(mockGameStatus.getQuestionId()).thenReturn(UUID.randomUUID().toString());
        when(mockGameScore.getTotalScore()).thenReturn(totalScore);
        when(mockGameScore.getQuestionScore()).thenReturn(questionScore);

        final AnswerQuestionResponse resultResponse = gameService.answerQuestion(roomId, playerId,
                questionId, answerId);

        verify(mockRoomRepository, times(1)).contains(roomId);
        verify(mockGameRepository, times(1)).getById(roomId);
        verify(mockGame, times(1)).getAnsweredPlayers();
        verify(mockGame, times(1)).getGameStatus();
        verify(mockGame, times(1)).getGameScore(playerId);
        verify(mockGameStatus, times(1)).getQuestionId();
        verify(mockGameScore, times(1)).getTotalScore();
        verify(mockGameScore, times(1)).getQuestionScore();

        Assert.assertNull(resultResponse.getCorrectAnswerId());
        Assert.assertNull(resultResponse.getQuestionId());
        Assert.assertEquals(totalScore, resultResponse.getTotalScore());
        Assert.assertEquals(questionScore, resultResponse.getQuestionScore());
    }

    @Test
    public void answerLastQuestionTest() throws Exception {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();
        final String answerId = UUID.randomUUID().toString();

        final String status = "started";
        final int questionNumber = 9;
        final int questionsCount = 10;

        final GameStatus gameStatus = new GameStatus(status, questionId, questionNumber, questionsCount);
        final GameScore gameScore = new GameScore();

        final Game mockGame = mock(Game.class);

        when(mockRoomRepository.contains(roomId)).thenReturn(true);
        when(mockGameRepository.getById(roomId)).thenReturn(mockGame);
        when(mockGame.getGameStatus()).thenReturn(gameStatus);
        when(mockGame.getGameScore(playerId)).thenReturn(gameScore);
        when(mockQuestionRepository.getCorrectAnswerId(questionId)).thenReturn(answerId);

        final AnswerQuestionResponse resultResponse = gameService.answerQuestion(roomId, playerId, questionId, answerId);

        verify(mockRoomRepository, times(1)).contains(roomId);
        verify(mockGameRepository, times(1)).getById(roomId);
        verify(mockGame, times(1)).getGameStatus();
        verify(mockGame, times(1)).getGameScore(playerId);
        verify(mockQuestionRepository, times(1)).getCorrectAnswerId(questionId);

        Assert.assertEquals(answerId, resultResponse.getCorrectAnswerId());
        Assert.assertNull(resultResponse.getQuestionId());
        Assert.assertEquals(gameScore.getTotalScore(), resultResponse.getTotalScore());
        Assert.assertEquals(gameScore.getQuestionScore(), resultResponse.getQuestionScore());
        Assert.assertEquals("ended", gameStatus.getStatus());
    }

    @Test
    public void answerQuestionTest() throws Exception {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();
        final String answerId = UUID.randomUUID().toString();

        final String status = "started";
        final int questionNumber = 3;
        final int questionsCount = 10;

        final GameStatus gameStatus = new GameStatus(status, questionId, questionNumber, questionsCount);
        final GameScore gameScore = new GameScore();

        final Game mockGame = mock(Game.class);

        final List<String> questionIds = new ArrayList<>();
        questionIds.add(UUID.randomUUID().toString());

        when(mockRoomRepository.contains(roomId)).thenReturn(true);
        when(mockGameRepository.getById(roomId)).thenReturn(mockGame);
        when(mockGame.getGameStatus()).thenReturn(gameStatus);
        when(mockGame.getGameScore(playerId)).thenReturn(gameScore);
        when(mockQuestionRepository.getCorrectAnswerId(questionId)).thenReturn(answerId);
        when(mockQuestionRepository.getRoomQuestionsId(roomId)).thenReturn(questionIds);

        final AnswerQuestionResponse resultResponse = gameService.answerQuestion(roomId, playerId, questionId, answerId);

        verify(mockRoomRepository, times(1)).contains(roomId);
        verify(mockGameRepository, times(1)).getById(roomId);
        verify(mockGame, times(1)).getGameStatus();
        verify(mockGame, times(1)).getGameScore(playerId);
        verify(mockQuestionRepository, times(1)).getCorrectAnswerId(questionId);
        verify(mockQuestionRepository, times(1)).getRoomQuestionsId(roomId);

        Assert.assertEquals(answerId, resultResponse.getCorrectAnswerId());
        Assert.assertEquals(gameStatus.getQuestionId(), resultResponse.getQuestionId());
        Assert.assertEquals(gameScore.getTotalScore(), resultResponse.getTotalScore());
        Assert.assertEquals(gameScore.getQuestionScore(), resultResponse.getQuestionScore());
    }

    @Test
    public void getGameStatusTest() {
        final String roomId = UUID.randomUUID().toString();

        final Game mockGame = mock(Game.class);
        final GameStatus mockGameStatus = mock(GameStatus.class);

        when(mockGameRepository.getById(roomId)).thenReturn(mockGame);
        when(mockGame.getGameStatus()).thenReturn(mockGameStatus);

        final GameStatus resultGameStatus = gameService.getGameStatus(roomId);

        verify(mockGameRepository, times(1)).getById(roomId);
        verify(mockGame, times(1)).getGameStatus();

        Assert.assertEquals(mockGameStatus, resultGameStatus);
    }
}