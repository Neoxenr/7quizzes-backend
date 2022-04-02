package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.game.Game;

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
     * Gets game by room id
     *
     * @param roomId - room id
     * @return Game - game
     */
    Game getGame(String roomId);

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

    /**
     * Removes game by room id
     *
     * @param roomId - room id
     */
    void remove(String roomId);
}
