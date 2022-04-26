package it.sevenbits.sevenquizzes.web.model.room;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateRoomRequest {
    private final String roomName;

    /**
     * CreateRoomRequest constructor
     *
     * @param roomName - room name
     */
    @JsonCreator
    public CreateRoomRequest(@JsonProperty("roomName") final String roomName) {
        this.roomName = roomName;
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
