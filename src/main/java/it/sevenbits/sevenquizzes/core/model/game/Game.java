package it.sevenbits.sevenquizzes.core.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<String, GameScore> gameScores;
    private final GameStatus gameStatus;

    private List<String> answeredPlayers;
    private List<String> previousQuestionsId;

    /**
     * Game constructor
     *
     * @param questionsCount - questions count
     */
    public Game(final int questionsCount) {
        gameScores = new HashMap<>();
        gameStatus = new GameStatus("not started", null, 0, questionsCount);

        answeredPlayers = new ArrayList<>();
        previousQuestionsId = new ArrayList<>();
    }

    /**
     * Add game score to player
     *
     * @param playerId - player id
     */
    public void addGameScore(final String playerId) {
        gameScores.put(playerId, new GameScore());
    }

    /**
     * Add new answered player
     *
     * @param playerId - player id
     */
    public void addAnsweredPlayer(final String playerId) {
        answeredPlayers.add(playerId);
    }

    /**
     * Add question id to previous questions id in the game
     *
     * @param questionId - previous question id
     */
    public void addPreviousQuestionId(final String questionId) {
        previousQuestionsId.add(questionId);
    }

    /**
     * Set new answered players
     *
     * @param answeredPlayers - answered players
     */
    public void setAnsweredPlayers(final List<String> answeredPlayers) {
        this.answeredPlayers = answeredPlayers;
    }

    /**
     * Get game score by player id
     *
     * @param playerId - player id
     * @return GameScore - player's game score
     */
    public GameScore getGameScore(final String playerId) {
        return gameScores.get(playerId);
    }

    /**
     * Get current game status
     *
     * @return GameStatus - current game status
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }

    /**
     * Return answered players
     *
     * @return List<String> - answered players
     */
    public List<String> getAnsweredPlayers() {
        return answeredPlayers;
    }

    /**
     * Return previous questions id in the game
     *
     * @return List<String> - previous questions id
     */
    public List<String> getPreviousQuestionsId() {
        return previousQuestionsId;
    }
}
