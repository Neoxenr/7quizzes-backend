package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.GameScore;

public class GameRepositoryStatic implements GameRepository {
    private final int questionsCount = 5;

    private int questionsAnsweredCount = 0;

    private final GameScore gameScore;

    /**
     * GameRepositoryStatic constructor
     */
    public GameRepositoryStatic() {
        gameScore = new GameScore();
    }

    /**
     * Returns questions count
     *
     * @return int - questions count in the game
     */
    @Override
    public int getQuestionsCount() {
        return questionsCount;
    }

    /**
     * Return answered questions count in the game
     *
     * @return int - answered questions count in the game
     */
    @Override
    public int getQuestionsAnsweredCount() {
        return questionsAnsweredCount;
    }

    /**
     * Returns game score model
     *
     * @return GameScore - game score model
     */
    @Override
    public GameScore getGameScore() {
        return gameScore;
    }

    /**
     * Updates correct answers
     */
    @Override
    public void updateQuestionsAnsweredCount() {
        questionsAnsweredCount++;
    }

    /**
     * Updates game score
     *
     * @param questionMark - score for right answer on question
     */
    @Override
    public void updateGameScore(final int questionMark) {
        gameScore.updateTotalScore(questionMark);
        gameScore.updateQuestionScore();
    }
}
