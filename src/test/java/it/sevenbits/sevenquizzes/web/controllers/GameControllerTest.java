package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import it.sevenbits.sevenquizzes.core.model.question.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.IncorrectAnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepositoryStatic;
import it.sevenbits.sevenquizzes.core.repository.question.QuestionRepository;
import it.sevenbits.sevenquizzes.core.repository.question.QuestionRepositoryStatic;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepositoryStatic;
import it.sevenbits.sevenquizzes.web.model.game.StartGameRequest;
import it.sevenbits.sevenquizzes.web.model.question.AnswerQuestionRequest;
import it.sevenbits.sevenquizzes.web.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GameControllerTest {
    private GameController gameController;

    private GameService gameService;

    private GameRepository gameRepository;
    private QuestionRepository questionRepository;
    private RoomRepository roomRepository;

    @Before
    public void setUp() {
        gameRepository = new GameRepositoryStatic(new HashMap<>());
        questionRepository = new QuestionRepositoryStatic(new HashMap<>(), new HashMap<>());
        roomRepository = new RoomRepositoryStatic(new HashMap<>(), new HashMap<>());

        gameService = new GameService(questionRepository, gameRepository, roomRepository);

        gameController = new GameController(gameService);
    }

    @Test
    public void postNewGameTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        final StartGameRequest startGameRequest = new StartGameRequest(playerId);

        roomRepository.create(roomId, playerId, "Test room");

        final ResponseEntity<QuestionLocation> response = gameController.postNewGame(roomId, startGameRequest);

        Assert.assertEquals(response.getBody().getQuestionId(), UUID.fromString(response.getBody().getQuestionId()).toString());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postNewGameNotFoundTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        final StartGameRequest startGameRequest = new StartGameRequest(playerId);

        final ResponseEntity<QuestionLocation> response = gameController.postNewGame(roomId, startGameRequest);

        Assert.assertNull(response.getBody());
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void postNewGameAlreadyStartedGameTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        final StartGameRequest startGameRequest = new StartGameRequest(playerId);

        roomRepository.create(roomId, playerId, "Test room");
        gameRepository.create(roomId, 10);

        final ResponseEntity<QuestionLocation> response = gameController.postNewGame(roomId, startGameRequest);

        Assert.assertNull(response.getBody());
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void getQuestionDataTest() throws SQLException {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        roomRepository.create(roomId, playerId, "Test room");
        gameRepository.create(roomId, 1);
        questionRepository.addRoomQuestions(roomId, 1);

        final List<String> roomQuestionsIds = questionRepository.getRoomQuestionsId(roomId);
        final QuestionWithOptions question = questionRepository.getById(roomQuestionsIds.get(0));

        final ResponseEntity<QuestionWithOptions> response = gameController.getQuestionData(roomId, roomQuestionsIds.get(0));

        Assert.assertEquals(roomQuestionsIds.get(0), response.getBody().getQuestionId());
        Assert.assertEquals(question.getQuestionText(), response.getBody().getQuestionText());
        Assert.assertEquals(question.getAnswersList(), response.getBody().getAnswersList());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getQuestionDataNotFoundTest() {
        final String roomId = UUID.randomUUID().toString();
        final String questionId = UUID.randomUUID().toString();

        final ResponseEntity<QuestionWithOptions> response = gameController.getQuestionData(roomId, questionId);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void postAnswerTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.create(roomId, playerId, "Test room");
        final Game game = gameRepository.create(roomId, 2);
        questionRepository.addRoomQuestions(roomId, 2);

        game.addGameScore(playerId);

        final List<String> roomQuestionsIds = questionRepository.getRoomQuestionsId(roomId);
        final QuestionWithOptions question = questionRepository.getById(roomQuestionsIds.get(0));
        final String answerId = question.getAnswersList().get(0).getAnswerId();

        final GameStatus gameStatus = gameRepository.getById(roomId).getGameStatus();
        gameStatus.setQuestionId(question.getQuestionId());
        gameStatus.setStatus("started");

        final AnswerQuestionRequest request = new AnswerQuestionRequest(playerId, answerId);

        final ResponseEntity<AnswerQuestionResponse> response = (ResponseEntity<AnswerQuestionResponse>) gameController.postAnswer(roomId, question.getQuestionId(), request);

        Assert.assertEquals(answerId, response.getBody().getCorrectAnswerId());
        Assert.assertEquals(roomQuestionsIds.get(1), response.getBody().getQuestionId());
        Assert.assertEquals(5, response.getBody().getTotalScore());
        Assert.assertEquals(1, response.getBody().getQuestionScore());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postAnswerOutOfOrderTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.create(roomId, playerId, "Test room");
        final Game game = gameRepository.create(roomId, 1);
        questionRepository.addRoomQuestions(roomId, 1);

        game.addGameScore(playerId);

        final List<String> roomQuestionsIds = questionRepository.getRoomQuestionsId(roomId);
        final QuestionWithOptions question = questionRepository.getById(roomQuestionsIds.get(0));
        final String answerId = question.getAnswersList().get(0).getAnswerId();

        final GameStatus gameStatus = gameRepository.getById(roomId).getGameStatus();
        gameStatus.setQuestionId(UUID.randomUUID().toString());
        gameStatus.setStatus("started");

        final AnswerQuestionRequest request = new AnswerQuestionRequest(playerId, answerId);

        final ResponseEntity<IncorrectAnswerQuestionResponse> response = (ResponseEntity<IncorrectAnswerQuestionResponse>) gameController.postAnswer(roomId, question.getQuestionId(), request);

        Assert.assertEquals(question.getQuestionId(), response.getBody().getQuestionId());
        Assert.assertEquals(0, response.getBody().getTotalScore());
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void postAnswerNotFoundTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.create(roomId, playerId, "Test room");
        gameRepository.create(roomId,  1);
        questionRepository.addRoomQuestions(roomId, 1);

        final AnswerQuestionRequest request = new AnswerQuestionRequest(playerId, UUID.randomUUID().toString());

        final ResponseEntity<?> response = gameController.postAnswer(roomId, UUID.randomUUID().toString(), request);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getGameStatusTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.create(roomId, playerId, "Test room");
        gameRepository.create(roomId, 1);

        final ResponseEntity<GameStatus> response = gameController.getGameStatus(roomId);

        Assert.assertEquals("not started", Objects.requireNonNull(response.getBody()).getStatus());
        Assert.assertEquals(1, response.getBody().getQuestionsCount());
        Assert.assertEquals(0, response.getBody().getQuestionNumber());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getGameStatusNotFoundTest() throws SQLException {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.create(roomId, playerId, "Test room");
        gameRepository.create(roomId, 1);

        final ResponseEntity<GameStatus> response = gameController.getGameStatus(UUID.randomUUID().toString());

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
