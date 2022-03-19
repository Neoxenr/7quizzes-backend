package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;

import java.util.HashMap;
import java.util.Map;

public class GameRepositoryStatic implements GameRepository {
    private final Map<String, Game> games;

    public GameRepositoryStatic() {
        this.games = new HashMap<>();
    }

    public GameScore getGameScore(final String id) {
        return games.get(id).getGameScore();
    }

    public GameStatus getGameStatus(final String id) {
        return games.get(id).getGameStatus();
    }
}
