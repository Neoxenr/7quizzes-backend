package it.sevenbits.sevenquizzes.core.model.game;

public class Game {
    private final GameScore gameScore;
    private final GameStatus gameStatus;

    public Game() {
        final int questionsCount = 2;
        gameScore = new GameScore();
        gameStatus = new GameStatus("not started", null, 0, questionsCount);
    }

    public GameScore getGameScore() {
        return gameScore;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
