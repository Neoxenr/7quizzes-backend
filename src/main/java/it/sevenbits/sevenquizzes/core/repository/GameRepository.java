package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.GameScore;

public interface GameRepository {
    /**
     * Returns questions count
     *
     * @return int - questions count in the game
     */
    int getQuestionsCount();

    /**
     * Return answered questions count in the game
     *
     * @return int - answered questions count in the game
     */
    int getQuestionsAnsweredCount();

    /**
     * Updates correct answers
     */
    void updateQuestionsAnsweredCount();

    /**
     * Updates game score
     *
     * @param questionMark - score for right answer on question
     */
    void updateGameScore(int questionMark);

    /**
     * Returns game score model
     *
     * @return GameScore - game score model
     */
    GameScore getGameScore();
}
