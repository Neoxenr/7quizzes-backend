package it.sevenbits.sevenquizzes.core.model.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private final Map<String, GameScore> gameScores;
    private final GameStatus gameStatus;

    private List<String> answeredPlayers;

    /**
     * Game constructor
     *
     * @param questionsCount - questions count
     */
    public Game(final int questionsCount) {
        gameScores = new HashMap<>();
        gameStatus = new GameStatus("not started", null, 0, questionsCount);

        answeredPlayers = new ArrayList<>();
    }

    /**
     * Adds game score to player
     *
     * @param playerId - player id
     */
    public void addGameScore(final String playerId) {
        gameScores.put(playerId, new GameScore());
    }

    /**
     * Adds new answered player
     *
     * @param playerId - player id
     */
    public void addAnsweredPlayer(final String playerId) {
        answeredPlayers.add(playerId);
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
     * Gets game score by player id
     *
     * @param playerId - player id
     * @return GameScore - player's game score
     */
    public GameScore getGameScore(final String playerId) {
        return gameScores.get(playerId);
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public List<String> getAnsweredPlayers() {
        return answeredPlayers;
    }
}
