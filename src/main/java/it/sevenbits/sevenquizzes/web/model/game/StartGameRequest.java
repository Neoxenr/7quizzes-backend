package it.sevenbits.sevenquizzes.web.model.game;

public class StartGameRequest {
    private final String playerId;

    public StartGameRequest(final String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
