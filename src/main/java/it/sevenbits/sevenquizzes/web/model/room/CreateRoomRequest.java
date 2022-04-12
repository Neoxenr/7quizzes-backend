package it.sevenbits.sevenquizzes.web.model.room;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRoomRequest {
    private final String playerId;
    private final String roomName;

    /**
     * CreateRoomRequest constructor
     *
     * @param playerId - player id
     * @param roomName - room name
     */
    public CreateRoomRequest(@JsonProperty("playerId") final String playerId, @JsonProperty("roomName") final String roomName) {
        this.playerId = playerId;
        this.roomName = roomName;
    }

    /**
     * Returns player id
     *
     * @return String - player id
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Returns room name
     *
     * @return String - room name
     */
    public String getRoomName() {
        return roomName;
    }
}
