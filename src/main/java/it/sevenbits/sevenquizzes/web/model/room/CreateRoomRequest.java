package it.sevenbits.sevenquizzes.web.model.room;

public class CreateRoomRequest {
    private final String playerId;
    private final String roomName;

    public CreateRoomRequest(final String playerId, final String roomName) {
        this.playerId = playerId;
        this.roomName = roomName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getRoomName() {
        return roomName;
    }
}
