package it.sevenbits.sevenquizzes.core.repository.game;

import it.sevenbits.sevenquizzes.core.model.game.Game;

import java.util.List;

public interface GameRepository {
    /**
     * Returns all games in repository
     *
     * @return List<Game> - all games in repository
     */
    List<Game> getAll();

    /**
     * Gets game by room id
     *
     * @param roomId - room id
     * @return Game - game
     */
    Game getById(String roomId);

    /**
     * Adds new game to repository
     *
     * @param roomId - room id
     * @param questionsCount - questions count
     * @return Game - new game
     */
    Game create(String roomId, int questionsCount);

    /**
     * Checks that key with game id exists
     *
     * @param roomId - room id
     * @return boolean - true if game with room id exists
     */
    boolean contains(String roomId);
}
