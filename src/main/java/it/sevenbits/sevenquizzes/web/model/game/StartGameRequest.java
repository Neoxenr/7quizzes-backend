package it.sevenbits.sevenquizzes.web.model.game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartGameRequest {
    private final String playerId;

    /**
     * StartGameRequest constructor
     *
     * @param playerId - player id
     */
    public StartGameRequest(@JsonProperty("playerId") final String playerId) {
        this.playerId = playerId;
    }

    /**
     * Returns player id
     *
     * @return player id
     */
    public String getPlayerId() {
        return playerId;
    }
}
