package it.sevenbits.sevenquizzes.core.repository;

import it.sevenbits.sevenquizzes.core.model.game.Game;
import it.sevenbits.sevenquizzes.core.model.game.GameScore;
import it.sevenbits.sevenquizzes.core.model.game.GameStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameRepositoryStatic implements GameRepository {
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
     * Creates new game
     *
     * @param roomId - room id
     * @param questionCount - questions count
     * @return Game - new game
     */
    public Game addGame(final String roomId, final int questionCount) {
        final Game game = new Game(questionCount);

        games.put(roomId, game);

        return game;
    }

    /**
     * Returns game score
     *
     * @param roomId - room id
     * @return GameScore - game score
     */
    public GameScore getGameScore(final String roomId) {
        return games.get(roomId).getGameScore();
    }

    /**
     * Returns game status
     *
     * @param roomId - room id
     * @return GameStatus - game status
     */
    public GameStatus getGameStatus(final String roomId) {
        return games.get(roomId).getGameStatus();
    }

    /**
     * Returns all games in repository
     *
     * @return List<Game>
     */
    public List<Game> getGames() {
        return new ArrayList<>(games.values());
    }

    /**
     *
     * @param roomId - room id
     * @return boolean - true if game with room id exists
     */
    @Override
    public boolean contains(final String roomId) {
        return games.containsKey(roomId);
    }
}
