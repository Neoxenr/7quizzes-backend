package it.sevenbits.sevenquizzes.web.model.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JoinRoomRequest {
    private final String playerId;

    /**
     * JoinRoomRequest constructor
     *
     * @param playerId - player id
     */
    public JoinRoomRequest(@JsonProperty("playerId") final String playerId) {
        this.playerId = playerId;
    }

    /**
     * Return player id
     *
     * @return String - player id
     */
    public String getPlayerId() {
        return playerId;
    }
}
