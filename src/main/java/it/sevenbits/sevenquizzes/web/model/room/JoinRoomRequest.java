package it.sevenbits.sevenquizzes.web.model.room;

public class JoinRoomRequest {
    private final String playerId;

    public JoinRoomRequest(final String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerId() {
        return playerId;
    }
}
