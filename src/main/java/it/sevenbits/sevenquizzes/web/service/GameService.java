package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import it.sevenbits.sevenquizzes.core.model.player.Player;
import it.sevenbits.sevenquizzes.core.model.question.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.question.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.repository.game.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.question.QuestionRepository;
import it.sevenbits.sevenquizzes.core.repository.room.RoomRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class GameService {
    private final QuestionRepository questionRepository;
    private final GameRepository gameRepository;
    private final RoomRepository roomRepository;

    /**
     * GameService constructor
     *
     * @param questionRepository - question repository
     * @param gameRepository     - game repository
     */
    public GameService(final QuestionRepository questionRepository, final GameRepository gameRepository,
            final RoomRepository roomRepository) {
        this.questionRepository = questionRepository;
        this.gameRepository = gameRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Returns model with question id
     *
     * @return QuestionLocation - model with question id
     */
    public QuestionLocation startGame(final String roomId, final String playerId) throws SQLException {
        if (!roomRepository.contains(roomId)) {
            throw new NullPointerException("Room with id = " + roomId + "does not exist");
        }

        if (gameRepository.contains(roomId)) {
            throw new RuntimeException("Game for room with id = " + roomId + "has been already started");
        }

        final int questionsCount = 2;
        final Game game = gameRepository.create(roomId, questionsCount);

        for (final Player player : roomRepository.getPlayers(roomId)) {
            game.addGameScore(player.getPlayerId());
        }

        final GameStatus gameStatus = game.getGameStatus();
        gameStatus.setStatus("started");

        questionRepository.addRoomQuestions(roomId, questionsCount);

        final List<String> roomQuestionsId = questionRepository.getRoomQuestionsId(roomId);

        String questionId = selectQuestionId(roomQuestionsId, game.getPreviousQuestionsId());
        game.addPreviousQuestionId(questionId);

        gameStatus.setQuestionId(questionId);

        return new QuestionLocation(questionId);
    }

    /**
     * Returns model with question data
     *
     * @param questionId - question id
     * @return QuestionWithOptions - model with question data
     */
    public QuestionWithOptions getQuestionData(final String roomId, final String questionId) {
        if (!roomRepository.contains(roomId)) {
            throw new NullPointerException("Room with id = " + roomId + " does not exist");
        }
        return questionRepository.getById(questionId);
    }

    /**
     * Returns model with game statistics, right answer id, next question id
     *
     * @param questionId - question id
     * @param answerId   - answer id
     * @return AnswerQuestionResponse - model with game statistics, right answer id, next question id
     * @throws Exception - if game is ended
     */
    public AnswerQuestionResponse answerQuestion(final String roomId, final String playerId,
            final String questionId, final String answerId) throws Exception {
        final Game game = gameRepository.getById(roomId);

        final GameStatus gameStatus = game.getGameStatus();
        final GameScore gameScore = game.getGameScore(playerId);

        final List<String> answeredPlayers = game.getAnsweredPlayers();

        if (!gameStatus.getQuestionId().equals(questionId) || answeredPlayers.contains(playerId)) {
            return new AnswerQuestionResponse(null, null,
                    gameScore.getTotalScore(), gameScore.getQuestionScore());
        }

        if (!gameStatus.getStatus().equals("ended")) {
            final String correctAnswerId = questionRepository.getCorrectAnswerId(questionId);

            game.addAnsweredPlayer(playerId);

            if (answerId.equals(correctAnswerId) || answeredPlayers.size() ==
                    roomRepository.getPlayers(roomId).size()) {
                if (answerId.equals(correctAnswerId)) {
                    final int questionMark = 5;

                    gameScore.updateScore(questionMark);
                    gameStatus.updateQuestionNumber();
                }

                final int questionsCount = gameStatus.getQuestionsCount();
                final int questionNumber = gameStatus.getQuestionNumber();

                if (questionNumber == questionsCount) {
                    gameStatus.setStatus("ended");

                    return new AnswerQuestionResponse(correctAnswerId, null,
                            gameScore.getTotalScore(), gameScore.getQuestionScore());
                }

                game.setAnsweredPlayers(new ArrayList<>());

                List<String> questionsIds = questionRepository.getRoomQuestionsId(roomId);
                final String newQuestionId = selectQuestionId(questionsIds, game.getPreviousQuestionsId());

                game.addPreviousQuestionId(questionId);

                gameStatus.setQuestionId(newQuestionId);
            }

            return new AnswerQuestionResponse(correctAnswerId, gameStatus.getQuestionId(),
                    gameScore.getTotalScore(), gameScore.getQuestionScore());
        }

        throw new Exception("The game is ended");
    }

    /**
     * Returns game status
     *
     * @param roomId - room id
     * @return GameStatus - game status
     */
    public GameStatus getGameStatus(final String roomId) {
        return gameRepository.getById(roomId).getGameStatus();
    }

    /**
     * Return next question id
     *
     * @param questionsId - questions ids
     * @return String - next question id
     */
    private String selectQuestionId(final List<String> questionsId, final List<String> previousQuestionsId) {
        final Random random = new Random();


        String questionId = questionsId.get(random.nextInt(questionsId.size()));
        while (previousQuestionsId.contains(questionId)) {
            questionId = questionsId.get(random.nextInt(questionsId.size()));
        }

        return questionId;
    }
}
