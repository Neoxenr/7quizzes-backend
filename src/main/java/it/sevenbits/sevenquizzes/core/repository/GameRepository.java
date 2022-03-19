package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;

public interface GameRepository {
    GameScore getGameScore(final String id);
    GameStatus getGameStatus(final String id);
}
