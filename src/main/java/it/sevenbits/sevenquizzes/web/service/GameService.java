package it.sevenbits.sevenquizzes.web.service;

import it.sevenbits.sevenquizzes.core.model.AnswerQuestionResponse;
import it.sevenbits.sevenquizzes.core.model.GameScore;
import it.sevenbits.sevenquizzes.core.model.QuestionLocation;
import it.sevenbits.sevenquizzes.core.model.QuestionWithOptions;
import it.sevenbits.sevenquizzes.core.repository.GameRepository;
import it.sevenbits.sevenquizzes.core.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

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
     * Returns model with questino id
     *
     * @return QuestionLocation - model with question id
     */
    public QuestionLocation startGame() {
        GameScore gameScore = gameRepository.getGameScore();

        System.out.println(gameScore);

        final Set<String> questionsIds = questionRepository.getQuestionsIds();
        final String questionId = selectQuestionId(questionsIds);

        return new QuestionLocation(questionId);
    }

    /**
     * Return next question id
     *
     * @param questionsIds - questions ids
     * @return String - next question id
     */
    public String selectQuestionId(final Set<String> questionsIds) {
        return new ArrayList<>(questionsIds).get(new Random().nextInt(questionsIds.size()));
    }

    /**
     * Returns model with question data
     *
     * @param questionId - question id
     * @return QuestionWithOptions - model with question data
     */
    public QuestionWithOptions getQuestion(final String questionId) {
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
    public AnswerQuestionResponse answerQuestion(final String questionId, final String answerId) throws Exception {
        final int questionsCount = gameRepository.getQuestionsCount();
        final int questionsAnsweredCount = gameRepository.getQuestionsAnsweredCount();

        if (isGameOver(questionsAnsweredCount, questionsCount)) {
            throw new Exception("The game is ended");
        }

        final String correctAnswerId = questionRepository.getCorrectAnswerId(questionId);

        if (isRightAnswer(answerId, correctAnswerId)) {
            final int questionMark = 5;
            gameRepository.updateGameScore(questionMark);
        }

        questionRepository.removeQuestion(questionId);

        gameRepository.updateQuestionsAnsweredCount();

        final GameScore gameScore = gameRepository.getGameScore();
        System.out.println(gameScore);

        if (isGameOver(questionsAnsweredCount, questionsCount)) {
            return new AnswerQuestionResponse(correctAnswerId, null,
                    gameScore.getTotalScore(), gameScore.getQuestionScore());
        }

        Set<String> questionsIds = questionRepository.getQuestionsIds();

        final String nextQuestionId = selectQuestionId(questionsIds);

        return new AnswerQuestionResponse(correctAnswerId, nextQuestionId,
                gameScore.getTotalScore(), gameScore.getQuestionScore());
    }

    /**
     * Returns result of comparison answer id and correct answer id
     *
     * @param answerId - answer id
     * @param correctAnswerId - correct answer id
     * @return boolean - result of comparison answer id and correct answer id
     */
    public boolean isRightAnswer(final String answerId, final String correctAnswerId) {
        return answerId.equals(correctAnswerId);
    }

    /**
     * Return result of comparison questions answered count and questions count
     *
     * @param questionsAnsweredCount - questions answered count
     * @param questionsCount - question count in the game
     * @return boolean - result of comparison questions answered count and questions count
     */
    public boolean isGameOver(final int questionsAnsweredCount, final int questionsCount) {
        return questionsAnsweredCount >= questionsCount;
    }
}
