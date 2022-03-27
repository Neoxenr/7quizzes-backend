package it.sevenbits.sevenquizzes.core.model.game;

public class Game {
    private final GameScore gameScore;
    private final GameStatus gameStatus;

    /**
     * Game constructor
     *
     * @param questionsCount - questions count
     */
    public Game(final int questionsCount) {
        gameScore = new GameScore();
        gameStatus = new GameStatus("not started", null, 0, questionsCount);
    }

    /**
     * Returns game score
     *
     * @return GameScore - game score
     */
    public GameScore getGameScore() {
        return gameScore;
    }

    /**
     * Returns game status
     *
     * @return GameStatus - game status
     */
    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
