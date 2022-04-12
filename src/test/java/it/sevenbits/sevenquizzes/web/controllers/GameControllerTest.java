package it.sevenbits.sevenquizzes.web.controllers;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import it.sevenbits.sevenquizzes.core.model.question.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.IncorrectAnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.repository.*;
import it.sevenbits.sevenquizzes.web.model.game.StartGameRequest;
import it.sevenbits.sevenquizzes.web.model.question.AnswerQuestionRequest;
import it.sevenbits.sevenquizzes.web.service.GameService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
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
    public void postNewGameTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        final StartGameRequest startGameRequest = new StartGameRequest(playerId);

        roomRepository.addRoom(roomId, playerId, "Test room");

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
    public void postNewGameAlreadyStartedGameTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        final StartGameRequest startGameRequest = new StartGameRequest(playerId);

        roomRepository.addRoom(roomId, playerId, "Test room");
        gameRepository.addGame(roomId, 10);

        final ResponseEntity<QuestionLocation> response = gameController.postNewGame(roomId, startGameRequest);

        Assert.assertNull(response.getBody());
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void getQuestionDataTest() {
        final String roomId = UUID.randomUUID().toString();
        final String playerId = UUID.randomUUID().toString();

        roomRepository.addRoom(roomId, playerId, "Test room");
        gameRepository.addGame(roomId, 1);
        questionRepository.createRoomQuestions(roomId, 1);

        final List<String> roomQuestionsIds = questionRepository.getRoomQuestionsIds(roomId);
        final QuestionWithOptions question = questionRepository.getRoomQuestionById(roomId, roomQuestionsIds.get(0));

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
    public void postAnswerTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.addRoom(roomId, playerId, "Test room");
        final Game game = gameRepository.addGame(roomId, 2);
        questionRepository.createRoomQuestions(roomId, 2);

        game.addGameScore(playerId);

        final List<String> roomQuestionsIds = questionRepository.getRoomQuestionsIds(roomId);
        final QuestionWithOptions question = questionRepository.getRoomQuestionById(roomId, roomQuestionsIds.get(0));
        final String answerId = question.getAnswersList().get(0).getAnswerId();

        final GameStatus gameStatus = gameRepository.getGame(roomId).getGameStatus();
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
    public void postAnswerOutOfOrderTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.addRoom(roomId, playerId, "Test room");
        final Game game = gameRepository.addGame(roomId, 1);
        questionRepository.createRoomQuestions(roomId, 1);

        game.addGameScore(playerId);

        final List<String> roomQuestionsIds = questionRepository.getRoomQuestionsIds(roomId);
        final QuestionWithOptions question = questionRepository.getRoomQuestionById(roomId, roomQuestionsIds.get(0));
        final String answerId = question.getAnswersList().get(0).getAnswerId();

        final GameStatus gameStatus = gameRepository.getGame(roomId).getGameStatus();
        gameStatus.setQuestionId(UUID.randomUUID().toString());
        gameStatus.setStatus("started");

        final AnswerQuestionRequest request = new AnswerQuestionRequest(playerId, answerId);

        final ResponseEntity<IncorrectAnswerQuestionResponse> response = (ResponseEntity<IncorrectAnswerQuestionResponse>) gameController.postAnswer(roomId, question.getQuestionId(), request);

        Assert.assertEquals(question.getQuestionId(), response.getBody().getQuestionId());
        Assert.assertEquals(0, response.getBody().getTotalScore());
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    public void postAnswerNotFoundTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.addRoom(roomId, playerId, "Test room");
        gameRepository.addGame(roomId,  1);
        questionRepository.createRoomQuestions(roomId, 1);

        final AnswerQuestionRequest request = new AnswerQuestionRequest(playerId, UUID.randomUUID().toString());

        final ResponseEntity<?> response = gameController.postAnswer(roomId, UUID.randomUUID().toString(), request);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getGameStatusTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.addRoom(roomId, playerId, "Test room");
        gameRepository.addGame(roomId, 1);

        final ResponseEntity<GameStatus> response = gameController.getGameStatus(roomId);

        Assert.assertEquals("not started", response.getBody().getStatus());
        Assert.assertEquals(1, response.getBody().getQuestionsCount());
        Assert.assertEquals(0, response.getBody().getQuestionNumber());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getGameStatusNotFoundTest() {
        final String playerId = UUID.randomUUID().toString();
        final String roomId = UUID.randomUUID().toString();

        roomRepository.addRoom(roomId, playerId, "Test room");
        gameRepository.addGame(roomId, 1);

        final ResponseEntity<GameStatus> response = gameController.getGameStatus(UUID.randomUUID().toString());

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
