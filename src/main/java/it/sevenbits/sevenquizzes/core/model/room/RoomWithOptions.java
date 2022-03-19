package it.sevenbits.sevenquizzes.core.model.room;

public class RoomWithOptions {
    private final String roomId;
    private final String roomName;

    public RoomWithOptions(final String roomId, final String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }
}
