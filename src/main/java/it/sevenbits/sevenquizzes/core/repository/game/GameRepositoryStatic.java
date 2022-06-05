package it.sevenbits.sevenquizzes.core.repository.game;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameRepositoryStatic implements GameRepository {
    private final Logger logger = LoggerFactory.getLogger("it.sevenbits.sevenquizzes.core.repository.game.logger");

    private final Map<String, Game> games;

    /**
     * GameRepositoryStatic constuctor
     *
     * @param games - games
     */
    public GameRepositoryStatic(final Map<String, Game> games) {
        this.games = games;
    }

    /**
     * Returns all games in repository
     *
     * @return List<Game>
     */
    public List<Game> getAll() {
        logger.info("Getting all games");
        return new ArrayList<>(games.values());
    }

    /**
     * Gets game by room id
     *
     * @param roomId - room id
     * @return Game - game entity
     */
    public Game getById(final String roomId) {
        logger.info("Getting game with room id = {}", roomId);
        return games.get(roomId);
    }

    /**
     * Create new game
     *
     * @param roomId - room id
     * @param questionsCount - questions count
     * @return Game - new game
     */
    public Game create(final String roomId, final int questionsCount) {
        logger.info("Creating new game with room id = {} and questions count = {}", roomId, questionsCount);

        final Game game = new Game(questionsCount);

        games.put(roomId, game);

        return game;
    }

    /**
     *
     * @param roomId - room id
     * @return boolean - true if game with room id exists
     */
    @Override
    public boolean contains(final String roomId) {
        logger.info("Checking that game with room id = {} is exist", roomId);
        return games.containsKey(roomId);
    }
}
