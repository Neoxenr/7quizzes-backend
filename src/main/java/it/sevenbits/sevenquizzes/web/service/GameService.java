package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;
import it.sevenbits.sevenquizzes.core.model.question.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.question.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.question.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.repository.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.QuestionRepository;
import it.sevenbits.sevenquizzes.web.model.game.StartGameRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class GameService {
    private final QuestionRepository questionRepository;
    private final GameRepository gameRepository;

    /**
     * GameService constructor
     *
     * @param questionRepository - question repository
     * @param gameRepository - game repository
     */
    public GameService(final QuestionRepository questionRepository, final GameRepository gameRepository) {
        this.questionRepository = questionRepository;
        this.gameRepository = gameRepository;
    }

    /**
     * Returns model with question id
     *
     * @return QuestionLocation - model with question id
     */
    public QuestionLocation startGame(final String roomId, final String playerId) {
        GameScore gameScore = gameRepository.getGameScore(roomId);

        // не знаю, будут ли вопросы закреплены за конкретной игрой
        // или можно брать рандомный вопрос из банка вопросов?

        System.out.println(gameScore);

        final List<String> questionsIds = questionRepository.getQuestionsIds();
        final String questionId = selectQuestionId(questionsIds);

        return new QuestionLocation(questionId);
    }

    /**
     * Returns model with question data
     *
     * @param questionId - question id
     * @return QuestionWithOptions - model with question data
     */
    public QuestionWithOptions getQuestion(final String roomId, final String questionId) {
        return questionRepository.getQuestionById(questionId);
    }

    /**
     * Returns model with game statistics, right answer id, next question id
     *
     * @param questionId - question id
     * @param answerId - answer id
     * @return AnswerQuestionResponse - model with game statistics, right answer id, next question id
     * @throws Exception - if game is ended
     */
    public AnswerQuestionResponse answerQuestion(final String roomId, final String playerId,
                                                 final String questionId, final String answerId) throws Exception {
        final GameStatus gameStatus = gameRepository.getGameStatus(roomId);

        final int questionsCount = gameStatus.getQuestionsCount();
        int questionNumber = gameStatus.getQuestionNumber();

        if (questionNumber < questionsCount) {
            GameScore gameScore = gameRepository.getGameScore(roomId);

            final String correctAnswerId = questionRepository.getCorrectAnswerId(questionId);

            if (answerId.equals(correctAnswerId)) {
                final int questionMark = 5;
                gameScore.updateScore(questionMark);
            }

            // связать список вопросов с игрой?? убрть удаление
            questionRepository.removeQuestion(questionId);

            gameStatus.updateQuestionNumber();

            questionNumber = gameStatus.getQuestionNumber();

            if (questionNumber == questionsCount) {
                return new AnswerQuestionResponse(correctAnswerId, null,
                        gameScore.getTotalScore(), gameScore.getQuestionScore());
            }

            List<String> questionsIds = questionRepository.getQuestionsIds();
            final String nextQuestionId = selectQuestionId(questionsIds);

            return new AnswerQuestionResponse(correctAnswerId, nextQuestionId,
                    gameScore.getTotalScore(), gameScore.getQuestionScore());
        } else {
            throw new Exception("The game is ended");
        }
    }

    public GameStatus getGameStatus(final String roomId) {
        return gameRepository.getGameStatus(roomId);
    }

    /**
     * Return next question id
     *
     * @param questionsIds - questions ids
     * @return String - next question id
     */
    public String selectQuestionId(final List<String> questionsIds) {
        return questionsIds.get(new Random().nextInt(questionsIds.size()));
    }
}
