package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;

import java.util.List;

public interface GameRepository {
    /**
     * Adds new game to repository
     *
     * @param roomId - room id
     * @param questionsCount - questions count
     * @return Game - new game
     */
    Game addGame(String roomId, int questionsCount);

    /**
     * Returns game score
     *
     * @param roomId - room id
     * @return GameScore - model for game score
     */
    GameScore getGameScore(String roomId);

    /**
     * Returns game status
     *
     * @param roomId - room id
     * @return GameStatus - model for game status
     */
    GameStatus getGameStatus(String roomId);

    /**
     * Returns all games in repository
     *
     * @return List<Game> - all games in repository
     */
    List<Game> getGames();

    /**
     * Checks that key with game id exists
     *
     * @param roomId - room id
     * @return boolean - true if game with room id exists
     */
    boolean contains(String roomId);
}
